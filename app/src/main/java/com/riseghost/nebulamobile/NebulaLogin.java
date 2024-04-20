package com.riseghost.nebulamobile;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class NebulaLogin extends Thread{
    private final String NebulaURL;
    private final String SessionCookie;
    private final String email;
    private final String password;
    private String ResponseJSON = null;

    public NebulaLogin(String NebulaURL,String SessionCookie, String email, String password){
        this.NebulaURL = NebulaURL;
        this.SessionCookie = SessionCookie;
        this.email = email;
        this.password = password;
        this.start();
    }

    @Override
    public void run(){
        try {
            URL url = new URL(this.NebulaURL + "/login");
            String requestBody = "email=" + this.email + "&password=" + this.password;

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Cookie","connect.sid" + "=" + this.SessionCookie);

            // Enviando dados
            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(requestBody.getBytes(StandardCharsets.UTF_8));
            os.close();

            this.ResponseJSON = new ReaderJSON(conn.getInputStream()).read();
            conn.disconnect();
        } catch (Exception e) {
            if (e.getMessage() != null)
                Log.e("NEBULA_CONNECT", e.getMessage());
            else
                Log.e("NEBULA_CONNECT", "NULL");
            Log.e("NEBULA_CONNECT", "Tipo de exceção: " + e.getClass().getSimpleName());
            Log.e("NEBULA_CONNECT", "Stack trace:", e);
        }
    }

    public String getResponseJSON() throws InterruptedException {
        this.join();
        return this.ResponseJSON;
    }
}
