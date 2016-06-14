package com.appdever.foody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class editProfileActivity extends AppCompatActivity {

    private ImageButton addImageButton;
    private EditText oldPassTextField, newPassTextField, verifyNewPassTextField;
    private Button cancelButton, okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        bindWidget();

        buttonController();

    }

    private void buttonController() {

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent objIntent = new Intent(editProfileActivity.this, MainActivity.class);
//                objIntent.putExtra("user", pilotName);
                startActivity(objIntent);
//                finish();
            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strNewPass = oldPassTextField.getText().toString().trim();
                String strOldPass = newPassTextField.getText().toString().trim();
                String strVerifyPass = verifyNewPassTextField.getText().toString().trim();

                if (strNewPass.equals("") || strOldPass.equals("") || strVerifyPass.equals("")) {

                    Toast.makeText(editProfileActivity.this,"กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void bindWidget() {

        addImageButton = (ImageButton) findViewById(R.id.addImageButton);

        oldPassTextField = (EditText) findViewById(R.id.oldPassTextField);
        newPassTextField = (EditText) findViewById(R.id.newPassTextField);
        verifyNewPassTextField = (EditText) findViewById(R.id.verifyNewPassTextField);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        okButton = (Button) findViewById(R.id.okButton);

    }
}
