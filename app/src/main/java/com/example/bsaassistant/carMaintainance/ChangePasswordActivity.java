package com.example.bsaassistant.carMaintainance;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bsaassistant.R;
import com.example.bsaassistant.databases.PasswordDatabaseHelper;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText newPasswordEditText;
    private Button changePasswordButton;
    private PasswordDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        dbHelper = new PasswordDatabaseHelper(this);

        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString().trim();

                // Insert the new password into the database
                dbHelper.insertPassword(newPassword);

                Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                // Finish the activity and go back to MainActivity
                finish();
            }
        });
    }
}