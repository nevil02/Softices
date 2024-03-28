package com.softices;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        sp = context.getSharedPreferences("Softices", 0);
        editor = sp.edit();
    }

    public void setString(String key, String value) {
        editor.putString(key, value).apply();
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }
}
