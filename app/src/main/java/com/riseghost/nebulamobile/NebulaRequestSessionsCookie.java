package com.riseghost.nebulamobile;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class NebulaRequestSessionsCookie extends Thread{
    private final String NebulaURL;
    private final String SharedPreferencesFilename = "NebulaSessionCookie" ;
    private final Context AplicationContext;
    public NebulaRequestSessionsCookie(String nebulaURL, Context AplicationContext){
        this.AplicationContext = AplicationContext;
        this.NebulaURL = nebulaURL + "/scookie";
        this.start();
    }

    @Override
    public void run(){
        try{
            URL url = new URL(this.NebulaURL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            Map<String, List<String>> Header = connection.getHeaderFields();
            List<String> SessionCookie = Header.get("Set-Cookie");
            if (SessionCookie != null){
                String c = SessionCookie.get(0).split(";")[0].split("=")[1];
                SharedPreferences sharedPreferences = this.AplicationContext.getSharedPreferences(this.SharedPreferencesFilename,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cookie",c);
                editor.apply();
            }
            connection.disconnect();
        }   catch (Exception e){
            Log.e("NebulaERRO", e.getMessage());
        }
    }

    public String getSharedPreferencesFilename(){return this.SharedPreferencesFilename;}
}
