package com.appdever.foody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button signUpButton, loginButton;
    TextView skipText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindWidget();

        controlActivity();

    }

    private void controlActivity() {

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent objIntent = new Intent(MainActivity.this, signupActivity.class);
//                objIntent.putExtra("user", pilotName);
                startActivity(objIntent);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent objIntent = new Intent(MainActivity.this, Login2Activity.class);
//                objIntent.putExtra("user", pilotName);
                startActivity(objIntent);

            }
        });

        skipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent objIntent = new Intent(MainActivity.this, HomeActivity.class);
//                objIntent.putExtra("user", pilotName);
                startActivity(objIntent);

            }
        });

    }

    private void bindWidget() {

        signUpButton = (Button) findViewById(R.id.signUpButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        skipText=(TextView) findViewById(R.id.skipText);

    }
}
