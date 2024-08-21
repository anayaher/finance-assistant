package com.example.bsaassistant.bankLoan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class LoanMain extends AppCompatActivity {
    TextView totalTv;
    RecyclerView recyclerView;
    Button add;
    ArrayList<String> bankNameArr, dateArr, accNoArr;
    ArrayList<Integer> srNoArr, amountArr, tenureArr,emiArr;
    ArrayList<Float> roiArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_main);
        totalTv = findViewById(R.id.loanMainTotalTv);
        recyclerView = findViewById(R.id.recylerviewLoanMain);
        add = findViewById(R.id.loanMainAddBtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(LoanMain.this));
        srNoArr = new ArrayList<>();
        bankNameArr = new ArrayList<>();
        dateArr = new ArrayList<>();
        emiArr = new ArrayList<>();
        accNoArr = new ArrayList<>();
        amountArr = new ArrayList<>();
        tenureArr = new ArrayList<>();
        roiArr = new ArrayList<>();
        setRecyclerView();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoanMain.this, LoanRecords.class);
                startActivity(intent);
            }
        });
    }

    private void setRecyclerView() {
        loanDbHelper loanDbHelper = new loanDbHelper(LoanMain.this);

        Cursor data = loanDbHelper.fetchLoan();
        int sum = 0;
        if (data.getCount() == 0) {

        } else {

            while (data.moveToNext()) {
                srNoArr.add(data.getInt(0));
                bankNameArr.add(data.getString(1));
                amountArr.add(data.getInt(2));
                tenureArr.add(data.getInt(3));
                roiArr.add(data.getFloat(4));
                emiArr.add(data.getInt(5));
                dateArr.add(data.getString(6));
                accNoArr.add(data.getString(7));

            }
            for (int i = 0; i < amountArr.size(); i++) {
                sum += amountArr.get(i);

            }
            totalTv.setText(String.valueOf(sum));
            loanAdapter loanAdapter = new loanAdapter(LoanMain.this,emiArr, bankNameArr, dateArr, accNoArr, srNoArr, amountArr, tenureArr, roiArr);


            recyclerView.setAdapter(loanAdapter);

        }
    }public void onResume() {

        super.onResume();
        srNoArr.clear();
        bankNameArr.clear();
        dateArr.clear();
        amountArr.clear();
        roiArr.clear();
        tenureArr.clear();accNoArr.clear();
        emiArr.clear();

        setRecyclerView();
    }
}