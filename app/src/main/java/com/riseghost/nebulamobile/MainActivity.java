package com.riseghost.nebulamobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText url = findViewById(R.id.url);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener((event) -> {
            try {
                NebulaRequestSessionsCookie r = new NebulaRequestSessionsCookie(url.getText().toString(),getApplicationContext());
                r.join();
                String cookie = new ReadSessionCookie(r.getSharedPreferencesFilename(),getApplicationContext()).read();
                NebulaLogin login = new NebulaLogin(url.getText().toString(),cookie,email.getText().toString(),password.getText().toString());
                Toast.makeText(getApplicationContext(),login.getResponseJSON().get("status").toString(),Toast.LENGTH_LONG).show();
                if (login.getResponseJSON().get("status").equals("Sucess")){
                    Intent intent = new Intent(this,HomePage.class);
                    intent.putExtra("NebulaURL",url.getText().toString());
                    startActivity(intent);
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Erro to connect server",Toast.LENGTH_LONG).show();
                Log.e("ERROMAINAC",e.getMessage());
            }
        });
    }
}