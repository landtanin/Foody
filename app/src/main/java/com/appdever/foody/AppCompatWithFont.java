package com.appdever.foody;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by User on 29/10/2558.
 */
public class AppCompatWithFont extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();
//        overridePendingTransition(0, 0);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        overridePendingTransition(0, 0);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        overridePendingTransition(0, 0);
    }
}
