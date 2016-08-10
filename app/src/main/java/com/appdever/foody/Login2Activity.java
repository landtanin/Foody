package com.appdever.foody;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appdever.foody.database.Member;
import com.appdever.foody.manager.SharedPreference;

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

import io.realm.Realm;

public class Login2Activity extends AppCompatActivity {

    private Button loginButton;
    private EditText username, password;


//    SharedPreferences sp;
//    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        bindWidget();

        controlActivity();

    }

    private void controlActivity() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  String strUsername = username.getText().toString().trim();
                String strPassword = password.getText().toString().trim();

                if (strUsername.equals("") || strPassword.equals("")) {

                    Toast.makeText(Login2Activity.this,"กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();

                }*/

                if (checkEmpty()) {

                    LoginData();

                }

            }
        });

    }

    private boolean checkEmpty() {


        if (password.getText().length() == 0 && username.getText().length() == 0){

            Toast.makeText(Login2Activity.this,"กรุณากรอกชื้อผู้ใช้และรหัสผ่าน", Toast.LENGTH_SHORT).show();

            return false;

        }else if (password.getText().length() == 0) {

            Toast.makeText(Login2Activity.this,"กรุณากรอกรหัสผ่าน", Toast.LENGTH_SHORT).show();

            return false;

        }else if (username.getText().length() == 0) {

            Toast.makeText(Login2Activity.this,"กรุณากรอกชื่อผู้ใช้", Toast.LENGTH_SHORT).show();

            return false;

        }

        return true;

    }

    private void bindWidget() {

        username = (EditText) findViewById(R.id.usernameTextField);
        password = (EditText) findViewById(R.id.passwordTextField);
        loginButton = (Button) findViewById(R.id.loginButton);

    }


    public boolean LoginData()
    {
//        username = (EditText)findViewById(R.id.usernameTextField);
//        password = (EditText)findViewById(R.id.passwordTextField);

        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        String url = "http://foodyth.azurewebsites.net/foody/checkLogin.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("strUser", username.getText().toString()));
        params.add(new BasicNameValuePair("strPass", password.getText().toString()));

        /** Get result from Server (Return the JSON Code)
         * StatusID = ? [0=Failed,m1=Complete]
         * MemberID = ? [Eg : m1]
         * Error	= ?	[On case error return custom error message]
         *
         * Eg Login Failed = {"StatusID":"0","MemberID":"0","Error":"Incorrect Username and Password"}
         * Eg Login Complete = {"StatusID":"m1","MemberID":"2","Error":""}
         */

        String resultServer  = getHttpPost(url,params);

        /*** Default Value ***/
        String strStatusID = "0";
        String strMemberID = "0";
        String strError = "Unknown Status!";

        JSONObject c = null;
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
      //      ad.setIcon(android.R.drawable.btn_star_big_on);
            ad.setPositiveButton("Close", null);
            ad.setMessage(strError);
            ad.show();
            username.setText("");
            password.setText("");
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
                Log.e("member data",e.toString());
            }

//            Toast.makeText(Login2Activity.this,"", Toast.LENGTH_SHORT).show();
            Intent newActivity = new Intent(Login2Activity.this,HomeActivity.class);
            newActivity.putExtra("MemberID","");



//            sp = getSharedPreferences("Name_Status", Context.MODE_PRIVATE);
//            editor = sp.edit();
//            editor.putString("My_Status",strStatusID );
//
            SharedPreference sharedPreference = new SharedPreference(this);
            sharedPreference.setStatus(strStatusID);

            Log.e("ShowLogin",String.valueOf(c));
            Log.e("ShowStatus",String.valueOf(strStatusID));

            startActivity(newActivity);
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
