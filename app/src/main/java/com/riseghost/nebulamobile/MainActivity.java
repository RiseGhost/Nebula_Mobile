package com.riseghost.nebulamobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NebulaRequestSessionsCookie r = new NebulaRequestSessionsCookie("https://nebula-env.com",getApplicationContext());
        try {
            r.join();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(r.getSharedPreferencesFilename(),MODE_PRIVATE);
            String cookie = sharedPreferences.getString("cookie","");
            NebulaLogin login = new NebulaLogin("https://nebula-env.com",cookie,"miguel@ubi.pt","theo");
            Toast.makeText(getApplicationContext(),login.getResponseJSON(),Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}