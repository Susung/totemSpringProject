package totem;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class DataInputController {

    @Autowired
    private DataEntryRepository datarepo;


    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
    		repo.save(new DataEntry("adsf","1900-01-01",123,123,123));
        return "asdfasdf";
    }
}
