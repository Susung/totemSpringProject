package totem;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import totem.cassandra.DataEntryRepository;
import totem.cassandra.DataEntry;
import totem.mysql.GraphData;
import totem.mysql.DataDAO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
@RestController
public class DataInputController {

	@Autowired
	private DataEntryRepository datarepo;	
	
	@Autowired
	private DataDAO datadao;
	
	//takes parameter m, a device name and returns all the data from MySQL with the device name
	@RequestMapping("/mapping")
	public Model greeting(@RequestParam(value="m") String m, Model model) {
		List<GraphData> d = datadao.findAll();
		
		//This part needs a fix. I couldn't get @Queries working on Mysql data repository
		//manually go through all the datasets in mysql and select the rows that are speicified
		Iterator<GraphData> itr = d.iterator();
		while (itr.hasNext()) {
			if(!itr.next().getDataKey().getModel().equals(m)) {
				itr.remove();
			}
		}
		model.addAttribute("graph", d);
		model.addAttribute("m", m);
		return model;
	}	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<DataEntry> enter(@RequestBody DataEntry de) {
		System.out.print("Entry Recieved : "+de.toString()+"....Saving to Cassandra");
		
		//save to cassandra
		datarepo.save(de);
		
		System.out.print("-successful");
		System.out.println();
		
		double watt = de.getVoltage() * de.getAmp();
		GraphData gd = new GraphData(new Date(de.getTime()), de.getModel(), watt);
		
		//save to mysql
		newEntry(gd); 
		return new ResponseEntity<DataEntry>(new HttpHeaders(), HttpStatus.CREATED);
	}
	
	//when client stops sending
	//if there is data that has not been put into mysql, 
	//take the data and put it in the correct interval
	@RequestMapping(value = "/end", method = RequestMethod.POST)
	public ResponseEntity<DataEntry> end() {
		System.out.println("data transfer finished");
		flushBins();
		return new ResponseEntity<DataEntry>(new HttpHeaders(), HttpStatus.CREATED);
	}
		
	/*
	 * I tried to separate this part to another class but couldn't due to some errors when saving.
	 * This part takes each row of data and assigns it to the interval for mysql.
	 * If a data on another interval comes, calculate the average of watt in
	 * existing interval and save it to mysql and delete the interval.
	 * then, create a new interval of the new data
	 */	
	
	private HashMap<String,Bin> bins = new HashMap<String,Bin>();
	
	private class Bin {
		public Date date;//interval of every 15 minutes, rounded down to nearest 15 minute interval
		public double total;//total addition of watts in the interval
		public int entries;//how many rows of data are in the interval
		
		public Bin(Date date, double total, int entries) {
			this.date = date;
			this.total = total;
			this.entries = entries;
		}
	}
	
	public void newEntry(GraphData entry) {
		String model = entry.getDataKey().getModel();
		
		if(bins.containsKey(model)) {
		
			Bin bin = bins.get(model);
			long t1 = entry.getDataKey().getDate().getTime();
			long t2 = bin.date.getTime();
			int t3 = entry.getDataKey().getDate().getHours() * 60 + entry.getDataKey().getDate().getMinutes();
			int t4 = bin.date.getHours() * 60 + bin.date.getMinutes();
			System.out.println(t3+ " " + t4);
			
			if((t1 - t2) >= 900000) {//if device is in another interval, make new interval
				GraphData gd = new GraphData(bin.date, model, bin.total/bin.entries);
				datadao.save(gd);//save to mysql
				bins.remove(model);
				addToBin(entry);				
				
			} else {//if new device is in current interval, update the values in the interval
				double newTotal = bin.total + entry.getWatt();
				bin.total = newTotal;
				bin.entries += 1;
			}
			
		} else {
			addToBin(entry);//if its a new device, make a interval for that device
		}		
	}
	
	private void addToBin(GraphData entry) {
		long temp = entry.getDataKey().getDate().getTime();
		entry.getDataKey().getDate().setTime(temp - (temp%900000));//round down to nearest 15 minute interval
		bins.put(entry.getDataKey().getModel(), new Bin(entry.getDataKey().getDate(), entry.getWatt(), 1));
	}
	
	//save remaining data that has not been saved to mysql
	private void flushBins() {
		for (String key : bins.keySet()) {
			GraphData gd = new GraphData(bins.get(key).date, key, bins.get(key).total/bins.get(key).entries);
			datadao.save(gd);
			System.out.println(gd.getWatt());
		}
	}
}
