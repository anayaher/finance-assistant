package com.example.bsaassistant.bankLoan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class LoanEmiRecords extends AppCompatActivity {
    TextView bankNameTv;
RecyclerView recyclerView;
 ArrayList<String> dateArr,bankNameArr;
    ArrayList<Integer>srNoArr;
    Integer premium = 16300;
    String emiName = "anay";
   Double roi = 11.88;
   Integer loanAmt = 100000;

Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_emi_records);
    bankNameTv =findViewById(R.id.emiRecordLoanName);
    recyclerView = findViewById(R.id.emiRecordRecyclerView);
    back = findViewById(R.id.emiRecordBackBtn);
    dateArr = new ArrayList<>();
    bankNameArr = new ArrayList<>();
    srNoArr = new ArrayList<>();


    emiName = getIntent().getStringExtra("passEmiName");

    getEmiData();

    bankNameTv.setText(emiName);

    recyclerView.setLayoutManager(new LinearLayoutManager(LoanEmiRecords.this));
    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
    }

    private void getEmiData() {
        emiDbHelper emiDbHelper = new emiDbHelper(LoanEmiRecords.this);
        Cursor data = emiDbHelper.fetchEmi(emiName);
        if(data.getCount() == 0){}
        else {
            while (data.moveToNext()){
                bankNameArr.add(data.getString(1));
                dateArr.add(data.getString(2));
                srNoArr.add(data.getInt(0));
               premium = data.getInt(4);
               roi = data.getDouble(5);
               loanAmt = data.getInt(7);



            }
            emiAdapter emiAdapter = new emiAdapter(LoanEmiRecords.this,roi,bankNameArr,dateArr,srNoArr,premium,loanAmt);

            recyclerView.setAdapter(emiAdapter);

        }

    }


}