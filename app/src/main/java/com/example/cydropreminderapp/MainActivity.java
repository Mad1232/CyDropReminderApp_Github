package com.example.cydropreminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView signUpText = findViewById(R.id.textView2);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to signup screen
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class); //source => destination
                startActivity(intent);
            }
        });

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to pet screen when clicking login button
                Intent intent = new Intent(MainActivity.this, PetActivity.class);
                startActivity(intent);
            }
        });
    }
}
