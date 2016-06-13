package com.appdever.foody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class signupActivity extends AppCompatActivity {

    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        bindWidget();

        controlActivity();

//        setTitle("                            สมัครสมาชิก");

//        getSupportActionBar()

    }

    private void controlActivity() {

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent objIntent = new Intent(signupActivity.this, MainActivity.class);
//                objIntent.putExtra("user", pilotName);
                startActivity(objIntent);
//                finish();

            }
        });
    }

    private void bindWidget() {

        cancelButton = (Button) findViewById(R.id.cancelButton);
    }
}
