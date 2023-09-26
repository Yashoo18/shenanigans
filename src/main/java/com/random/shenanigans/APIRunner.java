package com.random.shenanigans;

import java.net.MalformedURLException;
import java.net.URL;

public class APIRunner {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://randomuser.me/api/");
        APICallExample a = new APICallExample();
        a.API(url);
    }
}