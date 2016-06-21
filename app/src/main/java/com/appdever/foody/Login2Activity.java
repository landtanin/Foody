package com.appdever.foody;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Login2Activity extends AppCompatActivity {

    private Button loginButton;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        bindWidget();

        controlActivity();

    }

    private void controlActivity() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strUsername = username.getText().toString().trim();
                String strPassword = password.getText().toString().trim();

                if (strUsername.equals("") || strPassword.equals("")) {

                    Toast.makeText(Login2Activity.this,"กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();

                }else {
                    if (SaveData()) {

//                        Toast.makeText(Login2Activity.this, "Saved successfully", Toast.LENGTH_SHORT).show();

                    }

                }

            }

        });

    }

    public boolean SaveData()
    {

//        final EditText username = (EditText) findViewById(R.id.usernameTextField);
//        final EditText email = (EditText) findViewById(R.id.emailTextField);
//        final EditText password = (EditText) findViewById(R.id.passwordTextField);
//        final EditText verifyPass = (EditText) findViewById(R.id.verifyPassTextField);

//        final EditText newPass= (EditText) findViewById(R.id.newPassTextField);
//        final EditText verifyNewPass= (EditText) findViewById(R.id.verifyNewPassTextField);


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

//        if(!newPass.getText().toString().equals(verifyNewPass.getText().toString()))
//        {
//            ad.setMessage("Unmatched password!");
//            ad.show();
//            verifyNewPass.requestFocus();
//            return false;
//        }


        String url = "http://foodyth.azurewebsites.net/foody/saveADDData.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sUsername", username.getText().toString()));
        params.add(new BasicNameValuePair("sPassword", password.getText().toString()));
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
            username.setText("");
            password.setText("");
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


    private void bindWidget() {

        username = (EditText) findViewById(R.id.usernameTextField);
        password = (EditText) findViewById(R.id.passwordTextField);

        loginButton = (Button) findViewById(R.id.loginButton);

    }
}
