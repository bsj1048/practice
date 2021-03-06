package univ.lecture.riotapi.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import univ.lecture.riotapi.model.CalcApp;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by tchi on 2017. 4. 1..
 */
@RestController
@RequestMapping("/api/v1/calc")
@Log4j
public class RiotApiController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${riot.api.endpoint}")
    private String riotApiEndpoint;

    @Value("${riot.api.key}")
    private String riotApiKey;

    @RequestBody(value = "/app/{name}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CalcApp queryCalc(@PathVariable("name") String arguments) throws UnsupportedEncodingException {
        final String url = riotApiEndpoint + "/app/by-name/" +
                arguments +
                "?api_key=" +
                riotApiKey;

        String response = restTemplate.getForObject(url, String.class);
        Map<String, Object> parsedMap = new JacksonJsonParser().parseMap(response);

        parsedMap.forEach((key, value) -> log.info(String.format("key [%s] type [%s] value [%s]", key, value.getClass(), value)));

        Map<String, Object> summonerDetail = (Map<String, Object>) parsedMap.values().toArray()[0];
        //String queriedName = (String)summonerDetail.get("name");
        //int queriedLevel = (Integer)summonerDetail.get("summonerLevel");
	int a = 13;
	long b = 20170407;
	double c = 0.0;
        CalcApp app = new CalcApp(a, b ,c);

        return app;
    }
}
