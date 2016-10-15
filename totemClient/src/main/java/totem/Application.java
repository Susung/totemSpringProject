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
		
		final String uri = "http://52.41.88.208:8080/";
		final String end = uri+"end";
		
		try {
			Resource resource = new ClassPathResource("grid_stats.csv");
			InputStream is = resource.getInputStream();
			CSVReader reader = new CSVReader(new InputStreamReader(is));
			String nextLine[];
			DataEntry entry;
			RestTemplate restTemplate = new RestTemplate();
			DataEntry result;
			int count = 0;//10329352
			//9000 emonpi
			//1000 raspberrypi1
			while ((nextLine = reader.readNext()) != null) {
				if(count == 1000) break;
				if(nextLine[0].equals("raspberrypi1")) {
					System.out.println(count);
					count++;
					System.out.println(nextLine[0]);
					entry = new DataEntry(nextLine[0],nextLine[1],Long.parseLong(nextLine[3]),Double.parseDouble(nextLine[4]),Double.parseDouble(nextLine[5]));
					System.out.print("Sent : " + entry.toString());
					result = restTemplate.postForObject(uri, entry, DataEntry.class);
					System.out.println("success");
				}
			 }
			 
			 result = restTemplate.postForObject(end, "end", DataEntry.class);
		} catch (Exception e) {
			System.err.println("Error : " + e.getMessage());
		}
	}
	
}
