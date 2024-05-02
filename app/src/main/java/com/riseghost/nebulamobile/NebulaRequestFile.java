package com.riseghost.nebulamobile;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.time.LocalDate;

import javax.net.ssl.HttpsURLConnection;

public class NebulaRequestFile extends Thread{
    private final String NebulaURL;
    private final String SessionCookie;
    private final String FilePath;
    private byte[] ByteArray;

    public NebulaRequestFile(String NebulaURL, String SessionCookie, String FilePath){
        this.NebulaURL = NebulaURL + "/file" + "?path=" + FilePath;
        this.SessionCookie = SessionCookie;
        this.FilePath = FilePath;
        this.start();
    }

    @Override
    public void run(){
        try{
            URL url = new URL(this.NebulaURL);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpsURLConnection.setRequestProperty("Cookie","connect.sid" + "=" + this.SessionCookie);

            InputStream inputStream = httpsURLConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            this.ByteArray = byteArrayOutputStream.toByteArray();
        }   catch (Exception e){
            Log.e("NEBULAFILEResponse",e.getMessage());
        }
    }

    public byte[] getByteArray() throws InterruptedException{
        this.join();
        return this.ByteArray;
    }

    public String FileType(){
        if (this.ByteArray == null) return "Undefined";
        else if (ByteArray[0] == (byte) 0xFF && ByteArray[1] == (byte) 0xD8 && ByteArray[2] == (byte) 0xFF) return "jpg/jpeg";
        else if (ByteArray[0] == (byte) 0x89 && ByteArray[1] == (byte) 0x50 && ByteArray[2] == (byte) 0x4E && ByteArray[3] == (byte) 0x47 && ByteArray[4] == (byte) 0x0D && ByteArray[5] == (byte) 0x0A && ByteArray[6] == (byte) 0x1A && ByteArray[7] == (byte) 0x0A) return "png";
        else return "File";
    }
}
