package com.riseghost.nebulamobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.riseghost.nebulamobile.XMLElements.Explorer;
import com.riseghost.nebulamobile.XMLElements.NebulaUserInfo;

public class HomePage extends AppCompatActivity {
    private String NebulaURL;
    private String SessionCookie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        SessionCookie = new ReadSessionCookie("NebulaSessionCookie",getApplicationContext()).read();
        NebulaURL = getIntent().getStringExtra("NebulaURL");
        Explorer explorer = findViewById(R.id.Explorer);
        explorer.setNebulaURL(NebulaURL);
        explorer.setSessionCookies(SessionCookie);
        explorer.setExplorerPath(findViewById(R.id.ExplorerPath));
        explorer.UpdatePath("/");
        UpdateUserName();
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            getWindow().setBackgroundDrawableResource(R.drawable.homepage_light);
        } else if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            getWindow().setBackgroundDrawableResource(R.drawable.homepage_dark);
        }
    }

    public void UpdateUserName() {
        TextView UserName = findViewById(R.id.UserName);
        try{
            NebulaUserInfo nebulaUserInfo = new NebulaUserInfo(NebulaURL,SessionCookie);
            UserName.setText("Hi 👋🏻\n" + nebulaUserInfo.getUser().getName());
        }   catch (InterruptedException e){
            UserName.setText("Undefined");
        }
    }
}