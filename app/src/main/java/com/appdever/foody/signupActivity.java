package com.appdever.foody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signupActivity extends AppCompatActivity {

    private Button cancelButton, okButton;
    private EditText username, email, password, verifyPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        bindWidget();

        controlActivity();

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

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strUsername = username.getText().toString().trim();
                String strEmail = email.getText().toString().trim();
                String strPassword = password.getText().toString().trim();
                String strVerifyPass = verifyPass.getText().toString().trim();

                if (strUsername.equals("") || strEmail.equals("") || strPassword.equals("") || strVerifyPass.equals("")) {

                    Toast.makeText(signupActivity.this,"กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void bindWidget() {

        cancelButton = (Button) findViewById(R.id.cancelButton);
        okButton = (Button) findViewById(R.id.okButton);

        username = (EditText) findViewById(R.id.usernameTextField);
        email = (EditText) findViewById(R.id.emailTextField);
        password = (EditText) findViewById(R.id.passwordTextField);
        verifyPass = (EditText) findViewById(R.id.verifyPassTextField);

    }
}
