package com.riseghost.nebulamobile;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class ReadSessionCookie {
    private final String SharedPreferenceName;
    private final Context context;
    public ReadSessionCookie(String SharedPrefenceName, Context context){
        this.SharedPreferenceName = SharedPrefenceName;
        this.context = context;
    }

    public String read(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(this.SharedPreferenceName,MODE_PRIVATE);
        return sharedPreferences.getString("cookie","");
    }
}
