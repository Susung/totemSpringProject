package totem;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
	
	@RequestMapping("/mapping")
	public Model greeting(@RequestParam(value="m") String m, Model model) {
		List<GraphData> data = datadao.findAll();
		model.addAttribute("graph", data);
		model.addAttribute("m", m);
		return model;
	}	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<DataEntry> enter(@RequestBody DataEntry de) {
		System.out.print("Entry Recieved : "+de.toString()+"....Saving to Cassandra");
		datarepo.save(de);
		System.out.print("-successful");
		System.out.println();
		double watt = de.getVoltage() * de.getAmp();
		GraphData gd = new GraphData(new Date(de.getTime()), de.getModel(), watt);
		newEntry(gd); 
		return new ResponseEntity<DataEntry>(new HttpHeaders(), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/end", method = RequestMethod.POST)
	public ResponseEntity<DataEntry> end() {
		System.out.println("data transfer finished");
		flushBins();
		return new ResponseEntity<DataEntry>(new HttpHeaders(), HttpStatus.CREATED);
	}
		
	private HashMap<String,Bin> bins = new HashMap<String,Bin>();
	
	private class Bin {
		public Date date;
		public double total;
		public int entries;
		
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
			if((t1 - t2) >= 900000) {
				GraphData gd = new GraphData(bin.date, model, bin.total/bin.entries);
				datadao.save(gd);
				bins.remove(model);
				addToBin(entry);				
			} else {
				double newTotal = bin.total + entry.getWatt();
				bin.total = newTotal;
				bin.entries += 1;
			}
			
		} else {
			addToBin(entry);
		}		
	}
	
	private void addToBin(GraphData entry) {
		long temp = entry.getDataKey().getDate().getTime();
		entry.getDataKey().getDate().setTime(temp - (temp%900000));
		bins.put(entry.getDataKey().getModel(), new Bin(entry.getDataKey().getDate(), entry.getWatt(), 1));
	}
	
	private void flushBins() {
		for (String key : bins.keySet()) {
			GraphData gd = new GraphData(bins.get(key).date, key, bins.get(key).total/bins.get(key).entries);
			datadao.save(gd);
			System.out.println(gd.getWatt());
		}
	}
}
