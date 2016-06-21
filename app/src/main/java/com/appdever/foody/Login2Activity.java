package com.appdever.foody;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;

=======
>>>>>>> dc4b94969c0d50ce592cf28d9ec46f741fad8530
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

<<<<<<< HEAD
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
=======
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
>>>>>>> dc4b94969c0d50ce592cf28d9ec46f741fad8530

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

<<<<<<< HEAD
                }*/

                if (LoginData())
                {
                    //success
=======
                }else {
                    if (SaveData()) {

//                        Toast.makeText(Login2Activity.this, "Saved successfully", Toast.LENGTH_SHORT).show();

                    }

>>>>>>> dc4b94969c0d50ce592cf28d9ec46f741fad8530
                }

            }
        });

    }

    private void bindWidget() {

      /*  username = (EditText) findViewById(R.id.usernameTextField);
        password = (EditText) findViewById(R.id.passwordTextField);*/
        loginButton = (Button) findViewById(R.id.loginButton);

    }

    public boolean LoginData()
    {
        final EditText username = (EditText)findViewById(R.id.usernameTextField);
        final EditText password = (EditText)findViewById(R.id.passwordTextField);

        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        String url = "http://foodyth.azurewebsites.net/foody/checkLogin.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("strUser", username.getText().toString()));
        params.add(new BasicNameValuePair("strPass", password.getText().toString()));

        /** Get result from Server (Return the JSON Code)
         * StatusID = ? [0=Failed,1=Complete]
         * MemberID = ? [Eg : 1]
         * Error	= ?	[On case error return custom error message]
         *
         * Eg Login Failed = {"StatusID":"0","MemberID":"0","Error":"Incorrect Username and Password"}
         * Eg Login Complete = {"StatusID":"1","MemberID":"2","Error":""}
         */

        String resultServer  = getHttpPost(url,params);

        /*** Default Value ***/
        String strStatusID = "0";
        String strMemberID = "0";
        String strError = "Unknow Status!";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strStatusID = c.getString("StatusID");
            strMemberID = c.getString("MemberID");
            strError = c.getString("Error");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        // Prepare Login
        if(strStatusID.equals("0"))
        {
            // Dialog
            ad.setTitle("Error! ");
            ad.setIcon(android.R.drawable.btn_star_big_on);
            ad.setPositiveButton("Close", null);
            ad.setMessage(strError);
            ad.show();
            username.setText("");
            password.setText("");
        }
        else
        {
            Toast.makeText(Login2Activity.this, "Login OK", Toast.LENGTH_SHORT).show();
            Intent newActivity = new Intent(Login2Activity.this,MainActivity.class);
            newActivity.putExtra("MemberID","");
            startActivity(newActivity);
        }
<<<<<<< HEAD
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
=======
        return str.toString();
    }


    private void bindWidget() {

        username = (EditText) findViewById(R.id.usernameTextField);
        password = (EditText) findViewById(R.id.passwordTextField);
>>>>>>> dc4b94969c0d50ce592cf28d9ec46f741fad8530

        loginButton = (Button) findViewById(R.id.loginButton);

    }
}
