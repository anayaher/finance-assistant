package com.example.bsaassistant.reportactivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bsaassistant.R;
import com.example.bsaassistant.dialogues.JkDialog;
import com.example.bsaassistant.dialogues.JkRepDialog;
import com.example.bsaassistant.dialogues.salaryDialogue;

public class ReportMainActivity extends AppCompatActivity {

    Button salarybtn;
    Button jkBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_main);
        salarybtn = findViewById(R.id.reportMainSalbtn);
        jkBtn = findViewById(R.id.reportMainJkBtn);
        jkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JkRepDialog jkRepDialog = new JkRepDialog(ReportMainActivity.this);
                jkRepDialog.show();

            }
        });
        salarybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create Dialogue Here



                salaryDialogue customDialog = new salaryDialogue(ReportMainActivity.this);
                customDialog.show();


            }
        });
    }
}