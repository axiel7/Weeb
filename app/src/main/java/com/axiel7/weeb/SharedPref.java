package com.axiel7.weeb;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences mySharedPref ;
    public SharedPref(Context context) {
        mySharedPref = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }
    // this method will save the nightMode State
    public void setNightModeState(int state) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putInt("ThemeMode", state);
        editor.commit();
    }
    // this method will load the Night Mode State
    public int loadNightModeState() {
        int state = mySharedPref.getInt("ThemeMode",-1);
        return  state;
    }
}
