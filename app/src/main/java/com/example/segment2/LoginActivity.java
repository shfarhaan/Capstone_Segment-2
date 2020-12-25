package com.example.segment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.enableDefaults();
        email = (EditText) findViewById(R.id.editText_Email);
        password = (EditText) findViewById(R.id.editText_Password);

        login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_id = email.getText().toString().trim();
                String password_id = password.getText().toString().trim();
                String result = null;
                InputStream is = null;
                StringBuilder sb = null;
                if (email_id.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please Enter Email ID", Toast.LENGTH_SHORT).show();
                } else {


                    try {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://192.168.1.10/login_android/user_login.php");
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                    } catch (Exception e) {
                        Log.e("log_tag", "Error in http connection" + e.toString());
                    }

                    //convert response to string
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.ISO_8859_1), 8);
                        sb = new StringBuilder();
                        sb.append(reader.readLine() + "\n");
                        String line = "0";

                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        result = sb.toString();

                    } catch (Exception e) {
                        Log.e("log_tag", "Error converting result " + e.toString());
                    }

                    //paring data
                    String email_param;
                    String pass_param;


                    try {
                        JSONArray jArray = new JSONArray(result);
                        JSONObject json_data = null;

                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);

                            email_param = json_data.getString("email").toString().trim();
                            pass_param = json_data.getString("password").toString().trim();

                            // comparing table record with user id and password
                            if (email_id.toString().equals(email_param.toString()) && password_id.toString().equals(pass_param.toString())) {

                                Intent i1=new Intent(getApplicationContext(),HomeActivity.class );
                                startActivity(i1);

                            }


                        }


                    } catch (JSONException e1) {
                        Toast.makeText(getBaseContext(), "No Record Found...", Toast.LENGTH_LONG).show();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }


            }


        });
    }
}