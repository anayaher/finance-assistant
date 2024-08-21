package com.example.bsaassistant.carMaintainance;

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

public class CarMaintainanceMain extends AppCompatActivity {
    RecyclerView recyclerView;
    Button add;
    ArrayList<Integer> srnoArr,costArr;
    ArrayList<String>typeArr,dateArr,workArr,detailArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_maintainance_main);
        recyclerView = findViewById(R.id.recylerviewCarMain);
        add = findViewById(R.id.addCarMaintainanceBtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(CarMaintainanceMain.this));
        srnoArr = new ArrayList<>();
        costArr = new ArrayList<>();
        typeArr = new ArrayList<>();
        dateArr = new ArrayList<>();
        workArr = new ArrayList<>();
        detailArr = new ArrayList<>();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CarMaintainanceMain.this,CarMaintainaneRecords.class);
                startActivity(intent);
            }
        });
        carMaintainanceDbHelper carMaintainanceDbHelper = new carMaintainanceDbHelper(CarMaintainanceMain.this);
        Cursor data = carMaintainanceDbHelper.fetchMaintainance();
        if(data.getCount()==0){

        }else {
            while (data.moveToNext()){
                srnoArr.add(data.getInt(0));
                typeArr.add(data.getString(1));
                costArr.add(data.getInt(2));
                dateArr.add(data.getString(3));
                workArr.add(data.getString(4));
                detailArr.add(data.getString(5));
            }
            carMaintainanceAdpater carMaintainanceAdpater = new carMaintainanceAdpater(CarMaintainanceMain.this,srnoArr,costArr, typeArr, dateArr, workArr, detailArr);
            recyclerView.setAdapter(carMaintainanceAdpater);
        }

    }

    public void onResume() {
        super.onResume();
        srnoArr.clear();
        typeArr.clear();
        dateArr.clear();
        workArr.clear();
        detailArr.clear();
        costArr.clear();
        carMaintainanceDbHelper carMaintainanceDbHelper = new carMaintainanceDbHelper(CarMaintainanceMain.this);
        Cursor data = carMaintainanceDbHelper.fetchMaintainance();
        if(data.getCount()==0){

        }else {
            while (data.moveToNext()){
                srnoArr.add(data.getInt(0));
                typeArr.add(data.getString(1));
                costArr.add(data.getInt(2));
                dateArr.add(data.getString(3));
                workArr.add(data.getString(4));
                detailArr.add(data.getString(5));
            }
            carMaintainanceAdpater carMaintainanceAdpater = new carMaintainanceAdpater(CarMaintainanceMain.this,srnoArr,costArr, typeArr, dateArr, workArr, detailArr);
            recyclerView.setAdapter(carMaintainanceAdpater);
        }
    }
}