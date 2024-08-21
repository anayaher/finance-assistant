package com.example.bsaassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsaassistant.datamodels.jkModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JkDetailsActivity extends AppCompatActivity {
    TextView srTv,dateTv,amtTv,payeeTv,headTv,detailsTv;
    Button back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jk_details);
        srTv = findViewById(R.id.jkDetailsSrTv);
        dateTv= findViewById(R.id.jkDetailsDateTv);
        back = findViewById(R.id.jkDetailsBackBtn);

        amtTv = findViewById(R.id.jkDetailsAmountTv);
        payeeTv = findViewById(R.id.jkDetailsPayeeTv);
        headTv = findViewById(R.id.jkDetailsHeadTv);
        detailsTv = findViewById(R.id.jkDetailsDetailsTv);
        //Set Data
        String status = null;

        String sr = "sr";
        String date = "date";
        String amount ="amount";
        String payee ="payee";
        String head = "head";
        String details = "details";
        int type = 0;
       try {
           sr = getIntent().getStringExtra("PassSerial");
           date = getIntent().getStringExtra("PassDate");
           amount = getIntent().getStringExtra("PassAmount");
           payee = getIntent().getStringExtra("PassPayee");
           head = getIntent().getStringExtra("PassHead");
           details  = getIntent().getStringExtra("PassDetails");
           type = getIntent().getIntExtra("PassType",0);

       }catch (Exception e){
           Log.d("jkDetails", "onAssign: " + e);

       }
       if (type == 1){
           status = "Credited";

       }
       else {
           status = "Debited";

       }


       srTv.setText(String.format("Serial Number: %s", sr));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            DateTimeFormatter recievedDateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter desiredFormat = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
            LocalDate date1 = LocalDate.parse(date,recievedDateFormat);
            String output = date1.format(desiredFormat);
            dateTv.setText(String.format("Date: %s", output));

        }

        amtTv.setText(String.format("Amount: %s %s",status, amount));

        payeeTv.setText(String.format("Payee: %s", payee));

        headTv.setText(String.format("Head: %s", head));

        detailsTv.setText(String.format("Details: %s", details));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAndRemoveTask();;
            }
        });

    }
}