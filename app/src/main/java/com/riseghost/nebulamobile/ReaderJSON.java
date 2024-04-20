package com.riseghost.nebulamobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReaderJSON {
    private InputStreamReader isr;
    public ReaderJSON(InputStream inputStream){
        this.isr = new InputStreamReader(inputStream);
    }

    public String read() throws IOException {
        BufferedReader br = new BufferedReader(this.isr);
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            json.append(line);
        br.close();
        this.isr.close();
        return json.toString();
    }
}
