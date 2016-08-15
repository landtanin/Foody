package com.appdever.foody;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import com.appdever.foody.database.Member;
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

import io.realm.Realm;


public class signupActivity extends AppCompatActivity {


    private Button cancelButton, okButton;
    private ImageButton addImageButton;

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

//                                            Toast.makeText(signupActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();

                                            Intent objIntent = new Intent(signupActivity.this, HomeActivity.class);
                                            objIntent.putExtra("userIntent", myUsername);
                                            startActivity(objIntent);
                                        } else {
                                            Toast.makeText(signupActivity.this, "Sign up error", Toast.LENGTH_SHORT).show();
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

                } else {

                    Toast.makeText(signupActivity.this, "Please select picture", Toast.LENGTH_SHORT).show();

                }
        }
        });
    }

    private void loadingPage() {

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Loading...");
        progress.show();

    }

    private boolean checkEmptyField() {

        // Dialog
        ad = new AlertDialog.Builder(this);

        ad.setTitle("Error! ");

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
        String strError = "Unknown Status!";

        JSONObject c = null;

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
            ad.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    progress.dismiss();
                }
            }).create();
            ad.show();

            return false;
        }
        else
        {

            try {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Member member = new Member();
                member.setMemberID(c.getString("MemberID"));
                member.setUserName(c.getString("Username"));
                member.setName(c.getString("Name"));
                member.setEmail(c.getString("Email"));
                member.setPic(c.getString("Pic"));

                realm.copyToRealmOrUpdate(member);
//                realm.createOrUpdateObjectFromJson(Member.class,c);
                realm.commitTransaction();

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("member data error",e.toString());
            }

            name.setText("");
            username.setText("");
            password.setText("");
            verifyPass.setText("");
            email.setText("");

            return true;
        }

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
