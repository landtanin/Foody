package com.appdever.foody;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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
