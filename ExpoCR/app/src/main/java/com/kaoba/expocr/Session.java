package com.kaoba.expocr;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Jimmi on 02/07/2017.
 */

public class Session {
    private SharedPreferences prefs;

    public Session(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUsername(String username){
        prefs.edit().putString("username",username).commit();
    }
    public void setUserId(String id){
        prefs.edit().putString("userId",id).commit();
    }

    public String getUsername(){
        return prefs.getString("username","");
    }

    public String getUserId(){
        return prefs.getString("userId","");
    }

    public void logout(){
        prefs.edit().clear().commit();
    }
}
