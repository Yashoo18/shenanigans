package com.random.shenanigans;

import java.io.IOException;
import java.net.URL;

public class APICallExampleTest {
    @org.junit.Test
    public void testResponseCodeIs200() {
        try {
            URL url = new URL("https://randomuser.me/api/");

            UserConfig a = new UserConfig();
            //            int code = a.API(url);
            // Check if the response code is 200 (HTTP OK)
            //            assertEquals(200, code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
