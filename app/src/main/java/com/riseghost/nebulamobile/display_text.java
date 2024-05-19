package com.riseghost.nebulamobile;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;

public class display_text extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_display_text);
        String Filename = getIntent().getStringExtra("FileName");
        String path = getIntent().getStringExtra("path");
        String NebulaURL = getIntent().getStringExtra("NebulaURL");
        String SessionCookie = getIntent().getStringExtra("SessionCookie");

        TextView LabelFileName = findViewById(R.id.FileName);
        LabelFileName.setText(Filename);

        NebulaRequestFile nebulaRequestFile = new NebulaRequestFile(NebulaURL,SessionCookie,path);
        try{
            String data = new String(nebulaRequestFile.getByteArray(), StandardCharsets.UTF_8);
            EditText editText = findViewById(R.id.EditText);
            editText.setText(data);
        }   catch (InterruptedException e){

        }
    }
}