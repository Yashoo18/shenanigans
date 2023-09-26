package com.random.shenanigans;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;


public class APICallExampleTest {
    @Test
    public void testResponseCodeIs200() {
        try {
            URL url = new URL("https://randomuser.me/api/");

            APICallExample a = new APICallExample();
            int code = a.API(url);
            // Check if the response code is 200 (HTTP OK)
            assertEquals(200, code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
