package com.appdever.foody;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class editProfileActivity extends AppCompatActivity {

    private ImageButton addImageButton;
    private EditText newPassTextField, verifyNewPassTextField;
    private Button cancelButton, okButton;

    private static final int REQUEST_IMAGE_SELECTOR = 1;

//    private String selectedImagePath;
//
    private File mCurrentPhoto;

    private String targetUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        bindWidget();

        targetUser = getIntent().getStringExtra("userIntent");

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

//                String strOldPass = oldPassTextField.getText().toString().trim();
                String strNewPass = newPassTextField.getText().toString().trim();
                String strVerifyPass = verifyNewPassTextField.getText().toString().trim();

                if (strNewPass.equals("") || strVerifyPass.equals("")) {

                    Toast.makeText(editProfileActivity.this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();

                } else {
                    if (SaveData()) {

                        Toast.makeText(editProfileActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();

                    }


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

//        oldPassTextField = (EditText) findViewById(R.id.oldPassTextField);
        newPassTextField = (EditText) findViewById(R.id.newPassTextField);
        verifyNewPassTextField = (EditText) findViewById(R.id.verifyNewPassTextField);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        okButton = (Button) findViewById(R.id.okButton);

    }

    public boolean SaveData()
    {

//        final EditText username = (EditText) findViewById(R.id.usernameTextField);
//        final EditText email = (EditText) findViewById(R.id.emailTextField);
//        final EditText password = (EditText) findViewById(R.id.passwordTextField);
//        final EditText verifyPass = (EditText) findViewById(R.id.verifyPassTextField);

        final EditText newPass= (EditText) findViewById(R.id.newPassTextField);
        final EditText verifyNewPass= (EditText) findViewById(R.id.verifyNewPassTextField);


//        Log.e("base64", encoded);

        // Dialog
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        ad.setTitle("Error! ");
        ad.setIcon(android.R.drawable.btn_star_big_on);
        ad.setPositiveButton("Close", null);

        // Check Old Password
//        if(oldPass.getText().length() == 0 || verifyPass.getText().length() == 0 )
//        {
//            ad.setMessage("Please input [Password/Confirm Password] ");
//            ad.show();
//            password.requestFocus();
//            return false;
//        }
        // Check Password and Confirm Password (Match)

        if(!newPass.getText().toString().equals(verifyNewPass.getText().toString()))
        {
            ad.setMessage("Unmatched password!");
            ad.show();
            verifyNewPass.requestFocus();
            return false;
        }


        String url = "http://foodyth.azurewebsites.net/foody/saveADDData.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sUsername", targetUser));
        params.add(new BasicNameValuePair("sPassword", newPass.getText().toString()));
//        params.add(new BasicNameValuePair("sEmail", email.getText().toString()));
//        params.add(new BasicNameValuePair("sPicture", encoded));



        /** Get result from Server (Return the JSON Code)
         * StatusID = ? [0=Failed,1=Complete]
         * Error	= ?	[On case error return custom error message]
         *
         * Eg Save Failed = {"StatusID":"0","Error":"Email Exists!"}
         * Eg Save Complete = {"StatusID":"1","Error":""}
         */

        String resultServer  = getHttpPost(url,params);

        /*** Default Value ***/
        String strStatusID = "0";
        String strError = "Unknown Status!";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strStatusID = c.getString("StatusID");
            strError = c.getString("Error");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Prepare Save Data
        if(strStatusID.equals("0"))
        {
            ad.setMessage(strError);
            ad.show();
        }
        else
        {
            newPass.setText("");
//            password.setText("");
//            verifyPass.setText("");
//            email.setText("");
        }


        return true;
    }


    public String getHttpPost(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

}
