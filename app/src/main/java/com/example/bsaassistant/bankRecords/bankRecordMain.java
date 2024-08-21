package com.example.bsaassistant.bankRecords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class bankRecordMain extends AppCompatActivity {
    ArrayList<String> banksArr,ifcsArr,accNoArr,closedOnArr,workTimeArr;
    ArrayList<Integer>cusNoArr;

    RecyclerView recyclerView;
    Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_record_main);
        banksArr = new ArrayList<>();
        ifcsArr = new ArrayList<>();
        closedOnArr = new ArrayList<>();
        workTimeArr = new ArrayList<>();
        accNoArr = new ArrayList<>();
        cusNoArr = new ArrayList<>();
        recyclerView = findViewById(R.id.recylerviewBankMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        add = findViewById(R.id.addBankMainbtn);

        bankRecordDbHelper bankRecordDbHelper = new bankRecordDbHelper(this);
        Cursor data = bankRecordDbHelper.fetchAllBanks();
        if (data.getCount()== 0){

        }else {
            while (data.moveToNext()) {
                banksArr.add(data.getString(0));
                accNoArr.add(data.getString(1));
                ifcsArr.add(data.getString(2));
                cusNoArr.add(data.getInt(3));
                closedOnArr.add(data.getString(4));
                workTimeArr.add(data.getString(5));

            }
            bankRecordAdapter bankRecordAdapter = new bankRecordAdapter(this,banksArr,ifcsArr,closedOnArr,workTimeArr,accNoArr,cusNoArr);
            recyclerView.setAdapter(bankRecordAdapter);


        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bankRecordMain.this,BankAccountRecords.class);
                startActivity(intent);

            }
        });
    }

    public void onResume() {
        super.onResume();
        banksArr.clear();
        ifcsArr.clear();
        closedOnArr.clear();
        cusNoArr.clear();
        workTimeArr.clear();
        accNoArr.clear();
        bankRecordDbHelper bankRecordDbHelper = new bankRecordDbHelper(this);
        Cursor data = bankRecordDbHelper.fetchAllBanks();
        if (data.getCount()== 0){

        }else {
            while (data.moveToNext()) {
                banksArr.add(data.getString(0));
                accNoArr.add(data.getString(1));
                ifcsArr.add(data.getString(2));
                cusNoArr.add(data.getInt(3));
                closedOnArr.add(data.getString(4));
                workTimeArr.add(data.getString(5));

            }
            bankRecordAdapter bankRecordAdapter = new bankRecordAdapter(this,banksArr,ifcsArr,closedOnArr,workTimeArr,accNoArr,cusNoArr);
            recyclerView.setAdapter(bankRecordAdapter);


        }
    }
}