package com.example.bsaassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bsaassistant.databases.PasswordDatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private TextView passwordTextView;
    private Button loginButton;
    private PasswordDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new PasswordDatabaseHelper(this);

        passwordTextView = findViewById(R.id.textView);
        loginButton = findViewById(R.id.buttonSubmit);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPassword = passwordTextView.getText().toString().trim();
                String savedPassword = dbHelper.getPassword();

                if (enteredPassword.equals("0000")) {
                    // Password is correct
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Prevent going back to LoginActivity
                } else {
                    // Password is incorrect
                    Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
