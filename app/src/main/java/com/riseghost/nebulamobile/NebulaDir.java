package com.riseghost.nebulamobile;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class NebulaDir extends Thread{
    private String NebulaURL;
    private String path;
    private String Requestbody;
    private String SessionCookie;
    private JSONObject ResponseJSON = null;

    public  NebulaDir(String NebulaURL, String path, String SessionCookie){
        this.NebulaURL = NebulaURL + "/dir";
        this.path = path;
        this.Requestbody = "path=" + path;
        this.SessionCookie = SessionCookie;
        this.start();
    }

    @Override
    public void run(){
        try {
            URL url = new URL(this.NebulaURL);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpsURLConnection.setRequestProperty("Cookie","connect.sid" + "=" + this.SessionCookie);

            OutputStream os = new BufferedOutputStream(httpsURLConnection.getOutputStream());
            os.write(this.Requestbody.getBytes(StandardCharsets.UTF_8));
            os.close();

            this.ResponseJSON = new ReaderJSON(httpsURLConnection.getInputStream()).read();
        }   catch (Exception e){
            Log.e("EROOOOOOOOOOOOOOOOOOOOO", e.getMessage());
        }
    }

    public JSONObject getResponseJSON() throws InterruptedException {
        this.join();
        return this.ResponseJSON;
    }
}
