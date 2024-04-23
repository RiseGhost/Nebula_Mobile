package com.riseghost.nebulamobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
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
        NebulaRequestSessionsCookie r = new NebulaRequestSessionsCookie(url.getText().toString(),getApplicationContext());
        btn_login.setOnClickListener((event) -> {
            try {
                r.join();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(r.getSharedPreferencesFilename(),MODE_PRIVATE);
                String cookie = sharedPreferences.getString("cookie","");
                NebulaLogin login = new NebulaLogin(url.getText().toString(),cookie,email.getText().toString(),password.getText().toString());
                Toast.makeText(getApplicationContext(),login.getResponseJSON(),Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Erro to connect server",Toast.LENGTH_LONG).show();
            }
        });
    }

}