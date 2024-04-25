package com.riseghost.nebulamobile;

import android.graphics.drawable.Drawable;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import android.util.JsonReader;
import android.util.JsonToken;


public class ReaderJSON {
    private InputStreamReader isr;
    public ReaderJSON(InputStream inputStream){
        this.isr = new InputStreamReader(inputStream);
    }

    /*
    public JSONObject read() throws IOException, JSONException {
        BufferedReader br = new BufferedReader(this.isr);
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            json.append(line);
        br.close();
        this.isr.close();
        return new JSONObject(json.toString());
    }
    */

    public JSONObject read() throws IOException, JSONException {
        JsonReader jsonReader = new JsonReader(this.isr);
        JSONObject jsonObject = new JSONObject();

        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            JsonToken token = jsonReader.peek();
            Log.d("JSONREADERNEBULA", name);
            switch (token) {
                case NULL:
                    jsonReader.nextNull();
                    jsonObject.put(name, JSONObject.NULL);
                    break;
                case BOOLEAN:
                    boolean boolValue = jsonReader.nextBoolean();
                    jsonObject.put(name, boolValue);
                    break;
                case NUMBER:
                    String numberValue = jsonReader.nextString();
                    jsonObject.put(name, numberValue);
                    break;
                case STRING:
                    String stringValue = jsonReader.nextString();
                    jsonObject.put(name, stringValue);
                    break;
                case BEGIN_OBJECT:
                    JSONObject nestedObject = read();
                    jsonObject.put(name, nestedObject);
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        //Log.d("JSONREADERNEBULA", jsonObject.toString());
        return jsonObject;
    }


    /*
    public JSONObject readDir() throws IOException, JSONException {
        BufferedReader br = new BufferedReader(this.isr);
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            json.append(line);
        br.close();
        this.isr.close();
        String j = json.toString().replace("\\","");
        j = j.replaceAll("\\{|\\}|\\\"|\\d+\":","");

        String[] DirElement = j.replaceAll("\\{|\\}|\\\"|\\d+\":","").split(",");
        for(int i = 0; i < DirElement.length; i++){
            DirElement[i] = DirElement[i].replace("name",String.valueOf(i));
        }
        JsonReader jsonReader = new JsonReader(this.isr);
        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            Log.d("JSONCREATING", jsonReader.nextName());
        }
        Log.d("JSONCREATING", jsonReader.toString());
        //Log.d("JSONCREATING", new JSONObject(j).toString());
        return new JSONObject(j);
    }
    */
}
