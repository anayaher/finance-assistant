package com.example.bsaassistant.goldLoan;

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

public class GoldLoanMain extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Integer>tenureArr,amountArr;
    ArrayList<String>nameArr,dateArr,MdateArr,itemsArr;
    ArrayList<Double>roiArr;

    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_loan_main);
        recyclerView = findViewById(R.id.recylerviewGoldMain);
        add = findViewById(R.id.addLoanGoldMain);
        tenureArr = new ArrayList<>();
        amountArr = new ArrayList<>();
        nameArr = new ArrayList<>();
        dateArr = new ArrayList<>();
        MdateArr = new ArrayList<>();
        itemsArr = new ArrayList<>();
        roiArr = new ArrayList<>();
        getSetData();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoldLoanMain.this, GoldLoanActivity.class);
                startActivity(intent);

            }
        });


        recyclerView.setLayoutManager( new LinearLayoutManager(GoldLoanMain.this));

    }

    public void getSetData() {
goldLoanDbHelper goldLoanDbHelper  = new goldLoanDbHelper(GoldLoanMain.this);
Cursor data = goldLoanDbHelper.fetchGoldLoan();
if(data.getCount()==0){

}
else {
    while (data.moveToNext()){
        nameArr.add(data.getString(0));
        dateArr.add(data.getString(1));
       amountArr.add(data.getInt(2));
       tenureArr.add(data.getInt(3));
       roiArr.add(data.getDouble(4));
       MdateArr.add(data.getString(5));
       itemsArr.add(data.getString(6));


    }
    goldLoanAdapter goldLoanAdapter = new goldLoanAdapter(GoldLoanMain.this,tenureArr,amountArr, nameArr, dateArr, MdateArr, itemsArr, roiArr);
    recyclerView.setAdapter(goldLoanAdapter);

}
    }
    public void onResume() {

        super.onResume();
        nameArr.clear();
        dateArr.clear();
        amountArr.clear();
        tenureArr.clear();
        roiArr.clear();
        MdateArr.clear();
        itemsArr.clear();
        getSetData();
    }
}