package com.appdever.foody.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by arisak on 25/7/2559.
 */
public class SharedPreference {

        private final String KEY_PREFS = "prefs_user";

        private  SharedPreferences sp;
        private  SharedPreferences.Editor editor;

//    sp = getSharedPreferences("Name_Status", Context.MODE_PRIVATE);
//    editor = sp.edit();



        public SharedPreference(Context context) {
            sp = context.getSharedPreferences(KEY_PREFS, Context.MODE_PRIVATE);
            editor = sp.edit();

        }

    public void setStatus(String status) {
        editor.putString("My_Status",status );
        editor.commit();
    }

    public String getStatus() {

        return  sp.getString("My_Status","0" );
    }



}
