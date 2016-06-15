package com.appdever.foody;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class signupActivity extends AppCompatActivity {

    private Button cancelButton, okButton;
    private EditText username, email, password, verifyPass;
    private ImageButton addImageButton;

//    private ImageView test;

    //    private static final int SELECT_PICTURE = 1;
    private static final int REQUEST_IMAGE_SELECTOR = 1;
//    private String selectedImagePath;

    private File mCurrentPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        bindWidget();

//        test = (ImageView) findViewById(R.id.imageView3);

        controlActivity();

    }

    private void controlActivity() {

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,
//                        "Select Picture"), SELECT_PICTURE);
                dispatchPhotoSelectionIntent();


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent objIntent = new Intent(signupActivity.this, MainActivity.class);

                startActivity(objIntent);

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

                    Toast.makeText(signupActivity.this, "กรุณากรอกข้อมูลให้ครบถ้วน 555", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void dispatchPhotoSelectionIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(galleryIntent, REQUEST_IMAGE_SELECTOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        switch (requestCode) {
//            case REQUEST_IMAGE_SELECTOR:
        if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(), filePathColumn, null, null, null);
            if (cursor == null || cursor.getCount() < 1) {
                mCurrentPhoto = null;
            }
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (columnIndex < 0) { // no column index
                mCurrentPhoto = null;
            }
            mCurrentPhoto = new File(cursor.getString(columnIndex));

            if (mCurrentPhoto != null) {

                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = getThumbnail(imageUri);
                    addImageButton.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            cursor.close();
        } else {
            mCurrentPhoto = null;
        }
//                break;
//            case REQUEST_IMAGE_CAPTURE:
//                if (resultCode != Activity.RESULT_OK) {
//                    mCurrentPhoto = null;
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);


    }

    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = this.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 1024) ? (originalSize / 1024) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = this.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }

//    public String getPath(Uri uri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
//        if (cursor != null) {
//
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//
//
//            return cursor.getString(column_index);
//
//        } else {
//            cursor.close();
//            return null;
//        }
//
//    }
    //    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                selectedImagePath = getPath(selectedImageUri);
//            }
//        }
//    }

//    public String getPath(Uri uri) {
//        // just some safety built in
//        if( uri == null ) {
//            return null;
//        }
//        // try to retrieve the image from the media store first
//        // this will only work for images selected from gallery
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        if( cursor != null ){
//            int column_index = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        }
//        // this is our fallback here
//        return uri.getPath();
//    }

    private void bindWidget() {

        cancelButton = (Button) findViewById(R.id.cancelButton);
        okButton = (Button) findViewById(R.id.okButton);

        username = (EditText) findViewById(R.id.usernameTextField);
        email = (EditText) findViewById(R.id.emailTextField);
        password = (EditText) findViewById(R.id.passwordTextField);
        verifyPass = (EditText) findViewById(R.id.verifyPassTextField);

        addImageButton = (ImageButton) findViewById(R.id.addImageButton);

    }
}
