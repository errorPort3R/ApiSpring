package com.ironyard;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Future;

/**
 * Created by jeffryporter on 7/25/16.
 */



@RestController
public class ApiSpringController
{
    static final String API_URL = "http://gturnquist-quoters.cfapps.io/api/random";

    @PostConstruct
    public synchronized void init() throws InterruptedException
    {
        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    System.out.println("hi mom.");
                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    @Async
    public Future<HashMap> requestQuote()
    {
        RestTemplate query = new RestTemplate();
        HashMap result = query.getForObject(API_URL, HashMap.class);
        String type = (String) result.get("type");

        if(type.equals("success"))
        {
            HashMap value = (HashMap) result.get("value");
            return new AsyncResult<>(value);
        }
        return new AsyncResult<>(null);
    }


    //https://github.com/Book-It/BookIt
    @RequestMapping (path ="/quote", method = RequestMethod.GET)
    public ArrayList getQuote() throws InterruptedException
    {
        Future<HashMap> quote1 = requestQuote();
        Future<HashMap> quote2 = requestQuote();
        Future<HashMap> quote3 = requestQuote();

        while (!quote1.isDone() || !quote2.isDone() || !quote3.isDone())
        {
            Thread.sleep(100);
        }
        ArrayList arr = new ArrayList();
        arr.add(quote1);
        arr.add(quote2);
        arr.add(quote3);
        return arr;
    }

}
