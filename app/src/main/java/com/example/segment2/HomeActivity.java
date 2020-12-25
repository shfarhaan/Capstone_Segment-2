package com.example.segment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    TextView textView;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Assign ID's to textview and button.
        textView = (TextView)findViewById(R.id.TextViewUserEmail);
        logout = (Button)findViewById(R.id.button_logout);

        // Receiving value into activity using intent.
        String TempHolder = getIntent().getStringExtra("UserEmailTAG");

        // Setting up received value into EditText.
        textView.setText(textView.getText() + TempHolder);

        // Adding click listener to logout button.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Showing Echo Response Message Coming From Server.
                Toast.makeText(HomeActivity.this, "Logged Out Successfully", Toast.LENGTH_LONG).show();

                // Closing the current activity.
                finish();

                // Redirect to Main Login activity after log out.
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);

                startActivity(intent);

            }
        });
    }
}