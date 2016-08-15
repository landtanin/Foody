package com.appdever.foody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.appdever.foody.manager.KeyStore;
import com.appdever.foody.manager.SharedPreference;

/**
 * Created by arisak on 28/7/2559.
 */
public class Splash extends Activity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 2000L;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);
        handler = new Handler();

        runnable = new Runnable() {
            public void run() {

                SharedPreference sharedPreference = new SharedPreference(getApplicationContext());

                if (sharedPreference.getStatus().equals("1")) {

                    Intent intent = new Intent(Splash.this, HomeActivity.class);
                    intent.putExtra(KeyStore.SIGNUP_TO_HOME_STATUS, "fromSplash");
                    startActivity(intent);
                    finish();

                }
                else{

                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        };
    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }
}
