package com.riseghost.nebulamobile.XMLElements;

import android.util.Log;

import com.riseghost.nebulamobile.ReaderJSON;
import com.riseghost.nebulamobile.User;

import org.json.JSONObject;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NebulaUserInfo extends Thread{
    private String NebulaURL;
    private String SessionCookies;
    private User user;

    public NebulaUserInfo(String NebulaURL, String SessionCookies){
        this.NebulaURL = NebulaURL + "/user_info";
        this.SessionCookies = SessionCookies;
        this.start();
    }

    @Override
    public void run(){
        try{
            URL url = new URL(this.NebulaURL);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpsURLConnection.setRequestProperty("Cookie","connect.sid" + "=" + this.SessionCookies);

            JSONObject Uinfo = new ReaderJSON(httpsURLConnection.getInputStream()).read();
            this.user = new User(Uinfo.getString("name"),Uinfo.getString("email"));
        }   catch (Exception e){
            Log.e("NEBULAuserINFo",e.getMessage());
        }
    }

    public User getUser() throws InterruptedException {
        this.join();
        return this.user;
    }
}
