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

public class editProfileActivity extends AppCompatActivity {

    private ImageButton addImageButton;
    private EditText oldPassTextField, newPassTextField, verifyNewPassTextField;
    private Button cancelButton, okButton;

    private static final int REQUEST_IMAGE_SELECTOR = 1;

    private String selectedImagePath;

    private File mCurrentPhoto;

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

                dispatchPhotoSelectionIntent();

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



    private void bindWidget() {

        addImageButton = (ImageButton) findViewById(R.id.addImageButton);

        oldPassTextField = (EditText) findViewById(R.id.oldPassTextField);
        newPassTextField = (EditText) findViewById(R.id.newPassTextField);
        verifyNewPassTextField = (EditText) findViewById(R.id.verifyNewPassTextField);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        okButton = (Button) findViewById(R.id.okButton);

    }
}
