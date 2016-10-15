package totem;

import java.util.Date;
import com.opencsv.CSVReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import java.io.InputStreamReader;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		//server address
		final String uri = "http://52.41.88.208:8080/";
		final String end = uri+"end";
		
		//data file name
		String dataFile = "";
		
		try {
		
			//open the file and parse through using OpenCSV library
			Resource resource = new ClassPathResource(dataFile);
			InputStream is = resource.getInputStream();
			CSVReader reader = new CSVReader(new InputStreamReader(is));
			String nextLine[];
			DataEntry entry;
			
			RestTemplate restTemplate = new RestTemplate();
			DataEntry result;
			int count = 0;
			
			while ((nextLine = reader.readNext()) != null) {
					System.out.println(count);
					count++;
					
					System.out.println(nextLine[0]);
					
					entry = new DataEntry(nextLine[0],nextLine[1],Long.parseLong(nextLine[3]),Double.parseDouble(nextLine[4]),Double.parseDouble(nextLine[5]));
					
					System.out.print("Sent : " + entry.toString());
					result = restTemplate.postForObject(uri, entry, DataEntry.class);
					System.out.println("success");
			 }
			 
			 result = restTemplate.postForObject(end, "end", DataEntry.class);
		} catch (Exception e) {
			System.err.println("Error : " + e.getMessage());
		}
	}
	
}
