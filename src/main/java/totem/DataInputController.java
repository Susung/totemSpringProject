package totem;

import java.util.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class DataInputController {

	@Autowired
	private DataEntryRepository datarepo;
	
	@Autowired
	private DataDAO datadao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public DataEntry greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new DataEntry("adsf","1900-01-01",123,123,123);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<DataEntry> enter(@RequestBody DataEntry de) {
		datarepo.save(de);
		//datadao.save(new GraphData(new Date(de.getDate()),de.getModel(),de.getAmp(),de.getVoltage()));
		GraphData gd = new GraphData(new Date(), de.getModel(), de.getAmp(), de.getVoltage());
		datadao.save(gd);
		return new ResponseEntity<DataEntry>(new HttpHeaders(), HttpStatus.CREATED);
	}
}
