package com.riseghost.nebulamobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        String SessionCookie = new ReadSessionCookie("NebulaSessionCookie",getApplicationContext()).read();

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            getWindow().setBackgroundDrawableResource(R.drawable.homepage_light);
        } else if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            getWindow().setBackgroundDrawableResource(R.drawable.homepage_dark);
        }
        try {
            NebulaDir dir = new NebulaDir(getIntent().getStringExtra("NebulaURL"),"",SessionCookie);
            for(int index = 0; index < dir.getResponseJSON().length() / 2; index++){
                String element = dir.getResponseJSON().getString(String.valueOf(index)).toString() + " " + dir.getResponseJSON().get("type_" + String.valueOf(index)).toString();
                Log.d("JSONDIRNEBULA",element);
            }
            Log.d("JSONDIRNEBULA",dir.getResponseJSON().toString());
        } catch (Exception e) {
            Log.e("NEBULA_HOMEPAGE",e.getMessage());
        }


    }
}