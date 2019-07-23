package pl.kmalysiak.service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.kmalysiak.Test;

import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SampleController {

    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    public Map<String, Object> home() throws ServletException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", Test.getGreet() + " test");
        model.put("test", "test");
        return model;
    }

}
