package com.ironyard;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by jeffryporter on 7/25/16.
 */
@RestController
public class ApiSpringController
{
    static final String API_URL = "http://gturnquist-quoters.cfapps.io/api/random";

    @RequestMapping (path ="/quote", method = RequestMethod.GET)
    public HashMap getQuote()
    {
        RestTemplate query = new RestTemplate();
        HashMap result = query.getForObject(API_URL, HashMap.class);
        String type = (String) result.get("type");

        if(type.equals("success"))
        {
            HashMap value = (HashMap) result.get("value");
            return value;
        }
        return null;
    }
}
