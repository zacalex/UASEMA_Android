package edu.usc.cesr.ema_uas.model;

import java.util.HashMap;

/**
 * Created by cal on 11/16/17.
 */

public class LocalCookie {
    public HashMap<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(HashMap<String, String> cookies) {
        this.cookies = cookies;
    }

    private HashMap<String, String> cookies = new HashMap<>();


}
