package com.example.bsaassistant.Exam;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.google.android.material.textfield.TextInputEditText;

public class setRates extends AppCompatActivity {
    TextInputEditText pcRateTv,psRateTv,sRateTv;
    Button save,update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_rates);
        pcRateTv = findViewById(R.id.paperCheckingTv);
        psRateTv = findViewById(R.id.paperSettingTv);
        sRateTv = findViewById(R.id.supervisionRateTv);
        save = findViewById(R.id.saveRateBtn);
        update = findViewById(R.id.updateRateBtn);
        ratesDbHelper ratesDbHelper = new ratesDbHelper(setRates.this);
        Cursor check = ratesDbHelper.fetchRates();
        save.setEnabled(false);
        if(check.getCount() == 0){
         pcRateTv.setText("0");
         psRateTv.setText("0");
         sRateTv.setText("0");
        }else {
            while (check.moveToNext()){
                pcRateTv.setText(String.valueOf(check.getInt(0)));
                psRateTv.setText(String.valueOf(check.getInt(1)));
                sRateTv.setText(String.valueOf(check.getInt(2)));
            }
        }



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pcRateTv.getText().toString().equals("") || psRateTv.getText().toString().equals("") ||sRateTv.getText().toString().equals("")){
                    Toast.makeText(setRates.this, "Enter Rates", Toast.LENGTH_SHORT).show();


                }
                else {
                    ratesDbHelper ratesDbHelper = new ratesDbHelper(setRates.this);
                    Cursor data = ratesDbHelper.fetchRates();
                    if (data.getCount()==0){
                        ratesDbHelper.addRates(Integer.parseInt(pcRateTv.getText().toString()),Integer.parseInt(sRateTv.getText().toString()),Integer.parseInt(psRateTv.getText().toString()));
                        pcRateTv.setEnabled(false);
                        psRateTv.setEnabled(false);
                        sRateTv.setEnabled(false);
                        save.setEnabled(false);

                    }else {
                        ratesDbHelper.deleteAll();
                        ratesDbHelper.addRates(Integer.parseInt(pcRateTv.getText().toString()),Integer.parseInt(sRateTv.getText().toString()),Integer.parseInt(psRateTv.getText().toString()));
                        pcRateTv.setEnabled(false);
                        psRateTv.setEnabled(false);
                        sRateTv.setEnabled(false);
                        save.setEnabled(false);
                    }



                }
            }
        });update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save.setEnabled(true);
                pcRateTv.setEnabled(true);
                psRateTv.setEnabled(true);
                sRateTv.setEnabled(true);

            }
        });


    }
}