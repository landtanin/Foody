package com.appdever.foody;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView creator_names = (TextView) findViewById(R.id.creator_name);
        creator_names.setText(R.string.app_creator_names);
    }
}
