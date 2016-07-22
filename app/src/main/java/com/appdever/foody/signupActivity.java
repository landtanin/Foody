package com.appdever.foody;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class signupActivity extends AppCompatActivity {


    private Button cancelButton, okButton;
    private ImageButton addImageButton;
    /*private EditText username, email, password, verifyPass;*/

    /*private TextView alertUser, alertEmail, alertPass, alertRepass;*/

//    private ImageView test;

    //    private static final int SELECT_PICTURE = m1;
    private static final int REQUEST_IMAGE_SELECTOR = 1;
//    private String selectedImagePath;

    private ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
    private Bitmap bitmap;
    private String encoded;

    private File mCurrentPhoto;

    public static String myUsername = null;

//    private ProgressBar spinner;

    private EditText name, username, email, password, verifyPass;

    private AlertDialog.Builder ad = null;

    private ProgressDialog progress = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        bindWidget();

//        spinner.setVisibility(View.GONE);

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

                if (checkEmptyField()&&bitmap!=null) {

                    loadingPage();
//                spinner.setVisibility(View.VISIBLE);
                    final Handler handler = new Handler();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            if (bitmap != null) {

                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, mByteArrayOutputStream);
                                byte[] byteArray = mByteArrayOutputStream.toByteArray();
                                encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (SaveData()) {
                                            progress.dismiss();

                                            Toast.makeText(signupActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();

                                            Intent objIntent = new Intent(signupActivity.this, HomeActivity.class);
                                            objIntent.putExtra("userIntent", myUsername);
                                            startActivity(objIntent);
                                        }
                                    }
                                });
//                                Toast.makeText(signupActivity.this, "Please select picture", Toast.LENGTH_SHORT).show();
//                    Log.v("base64", encoded);

                            }


                            // When Save Complete
//                    spinner.setVisibility(View.GONE);


                        }
                    });
                    t.start();

/*
                String strUsername = username.getText().toString().trim();
                String strEmail = email.getText().toString().trim();
                String strPassword = password.getText().toString().trim();
                String strVerifyPass = verifyPass.getText().toString().trim();
                TextView alertUser = (TextView)findViewById(R.id.alertUser);
                alertUser.setTextColor(Color.RED);
                TextView alertEmail = (TextView)findViewById(R.id.alertEmail);
                alertEmail.setTextColor(Color.RED);
                TextView alertPass = (TextView)findViewById(R.id.alertPass);
                alertPass.setTextColor(Color.RED);
                TextView alertRepass = (TextView)findViewById(R.id.alertRepass);
                alertRepass.setTextColor(Color.RED);

                *//*if (strUsername.equals("") || strEmail.equals("") || strPassword.equals("") || strVerifyPass.equals("")) {

                    Toast.makeText(signupActivity.this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();

                }*//*
                if(strUsername.equals (""))
                {
                    alertUser.setText("กรุณากรอกชื่อผู้ใช้");
                }
                else
                {
                    Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(strUsername);
                    boolean b = m.find();

                    if (b)
                    {
                        alertUser.setText("ชื่อผู้ใช้ต้องเป็นภาษาอังกฤษหรือตัวเลขเท่านั้น");
                    }
                    else if(strUsername.length() > 15)
                    {
                        alertUser.setText("ชื่อผู้ใช้ต้องไม่เกิน 15 ตัวอักษร");
                    }
                    else
                    {
                        alertUser.setText("");
                    }

                }
                if(strEmail.equals ("")) {
                    alertEmail.setText("กรุณากรอกอีเมล");
                }
                else if(!(strEmail.equals ("")))
                {
                    try {
                        String mydomain = strEmail;
                        String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
                        Boolean b = mydomain.matches(emailregex);

                        if (b == false) {
                            alertEmail.setText("อีเมลไม่ถูกต้อง");
                        }else if(b == true){
                            alertEmail.setText("");
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                }
                else
                {
                    alertEmail.setText("");
                    //check email
                }
                if(strPassword.equals (""))
                {
                    alertPass.setText("กรุณากรอกรหัสผ่าน");
                }
                else {
                    Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(strUsername);
                    boolean b = m.find();

                    if (b) {
                        alertPass.setText("รหัสผ่านต้องเป็นภาษาอังกฤษหรือตัวเลขเท่านั้น");
                    }
                    else if (strPassword.length() > 15) {
                        alertPass.setText("รหัสผ่านต้องมีความยาวระหว่าง 6 - 15 ตัวอักษร");
                    }
                    else if (strPassword.length() < 6) {
                        alertPass.setText("รหัสผ่านต้องมีความยาวระหว่าง 6 - 15 ตัวอักษร");
                    }
                    else
                    {
                        alertPass.setText("");
                    }
                }

                if (!(strPassword.equals (strVerifyPass)))
                {
                    alertRepass.setText ("รหัสผ่านไม่ตรงกัน");
                }
                else
                {
                    alertRepass.setText ("");
                }
            }*/
                } else {

                    Toast.makeText(signupActivity.this, "Please select picture", Toast.LENGTH_SHORT).show();

                }
        }
        });
    }

    private void loadingPage() {

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();

    }

    private boolean checkEmptyField() {

        // Dialog
        ad = new AlertDialog.Builder(this);

        ad.setTitle("Error! ");
        ad.setIcon(android.R.drawable.btn_star_big_on);
        ad.setPositiveButton("Close", null);


        // Check Username
        if(username.getText().length() == 0)
        {
            ad.setMessage("Please input [Username] ");
            ad.show();
            username.requestFocus();
            return false;
        }
        if(email.getText().length() == 0)
        {
            ad.setMessage("Please input [Email] ");
            ad.show();
            email.requestFocus();
            return false;
        }
        // Check Password
        if(password.getText().length() == 0 || verifyPass.getText().length() == 0 )
        {
            ad.setMessage("Please input [Password/Confirm Password] ");
            ad.show();
            password.requestFocus();
            return false;
        }
        // Check Password and Confirm Password (Match)
        if(!password.getText().toString().equals(verifyPass.getText().toString()))
        {
            ad.setMessage("Unmatched Password ");
            ad.show();
            verifyPass.requestFocus();
            return false;
        }

        return true;
    }

    private void dispatchPhotoSelectionIntent() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        this.startActivityForResult(galleryIntent, REQUEST_IMAGE_SELECTOR);

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_SELECTOR);

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
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (columnIndex < 0) { // no column index
                mCurrentPhoto = null;
            }

            String myUrl = data.getData().toString();
            if (myUrl.startsWith("content://com.google.android.apps.photos.content")) {

                Glide.with(this).load(Uri.parse(myUrl)).into(addImageButton);
                try {
                    bitmap = getThumbnail(Uri.parse(myUrl));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

//            Log.d("CURSORCHECK", String.valueOf(cursor.getString(columnIndex)));
//            mCurrentPhoto = new File(cursor.getString(columnIndex));
            mCurrentPhoto = new File(getPath(data.getData()));


            if (mCurrentPhoto != null) {

                Uri imageUri = data.getData();
                try {

                    bitmap = getThumbnail(imageUri);
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

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();


            return cursor.getString(column_index);

        } else {
            cursor.close();
            return null;
        }

    }
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

       /* username = (EditText) findViewById(R.id.usernameTextField);
        email = (EditText) findViewById(R.id.emailTextField);
        password = (EditText) findViewById(R.id.passwordTextField);
        verifyPass = (EditText) findViewById(R.id.verifyPassTextField);*/

        addImageButton = (ImageButton) findViewById(R.id.addImageButton);

//        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        name = (EditText) findViewById(R.id.edtName);
        username = (EditText) findViewById(R.id.usernameTextField);
        email = (EditText) findViewById(R.id.emailTextField);
        password = (EditText) findViewById(R.id.passwordTextField);
        verifyPass = (EditText) findViewById(R.id.verifyPassTextField);


    }

    public boolean SaveData()
    {

//        Log.e("base64", encoded);



        myUsername = username.getText().toString();

        String url = "http://foodyth.azurewebsites.net/foody/saveADDData.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sName", name.getText().toString()));
        params.add(new BasicNameValuePair("sUsername", username.getText().toString()));
        params.add(new BasicNameValuePair("sPassword", password.getText().toString()));
        params.add(new BasicNameValuePair("sEmail", email.getText().toString()));
        params.add(new BasicNameValuePair("sPicture", encoded));

        /** Get result from Server (Return the JSON Code)
         * StatusID = ? [0=Failed,m1=Complete]
         * Error	= ?	[On case error return custom error message]
         *
         * Eg Save Failed = {"StatusID":"0","Error":"Email Exists!"}
         * Eg Save Complete = {"StatusID":"m1","Error":""}
         */

        String resultServer  = getHttpPost(url,params);

        /*** Default Value ***/
        String strStatusID = "0";
        String strError = "Unknow Status!";

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
            name.setText("");
            username.setText("");
            password.setText("");
            verifyPass.setText("");
            email.setText("");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
