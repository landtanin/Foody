package com.appdever.foody;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;

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


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
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
