package com.riseghost.nebulamobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import com.riseghost.nebulamobile.XMLElements.Explorer;

public class HomePage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        String SessionCookie = new ReadSessionCookie("NebulaSessionCookie",getApplicationContext()).read();
        String NebulaURL = getIntent().getStringExtra("NebulaURL");
        Explorer explorer = findViewById(R.id.Explorer);
        explorer.setNebulaURL(NebulaURL);
        explorer.setSessionCookies(SessionCookie);
        explorer.setExplorerPath(findViewById(R.id.ExplorerPath));
        explorer.UpdatePath("/");
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            getWindow().setBackgroundDrawableResource(R.drawable.homepage_light);
        } else if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            getWindow().setBackgroundDrawableResource(R.drawable.homepage_dark);
        }
    }
}