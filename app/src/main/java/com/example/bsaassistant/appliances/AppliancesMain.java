package com.example.bsaassistant.appliances;

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

public class AppliancesMain extends AppCompatActivity {

    Button add;
    RecyclerView recyclerView;
    ArrayList<Integer> srnoArr;
    ArrayList<String >itemNameArrm,priceArr,ShopArr,dateBoughtArr,warrantyDurationArr,expiryDateArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances_main);
        add = findViewById(R.id.addApplianceMainbtn);
        recyclerView  = findViewById(R.id.recylerviewAppliancesMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(AppliancesMain.this));
        srnoArr = new ArrayList<>();
        itemNameArrm = new ArrayList<>();
        priceArr = new ArrayList<>();
        ShopArr = new ArrayList<>();
        dateBoughtArr = new ArrayList<>();
        warrantyDurationArr = new ArrayList<>();
        expiryDateArr = new ArrayList<>();
        appliancesDbHelper appliancesDbHelper = new appliancesDbHelper(AppliancesMain.this);
        Cursor data = appliancesDbHelper.fetchAppliance();
        if(data.getCount() == 0){

        }else {
         while (data.moveToNext()){
             srnoArr.add(data.getInt(0));
             itemNameArrm.add(data.getString(1));
             priceArr.add(data.getString(2));
             ShopArr.add(data.getString(3));
             dateBoughtArr.add(data.getString(4));
             warrantyDurationArr.add(data.getString(5));
             expiryDateArr.add(data.getString(6));

         }
         applianceAdapter applianceAdapter = new applianceAdapter(AppliancesMain.this,srnoArr, itemNameArrm, priceArr,ShopArr,dateBoughtArr,warrantyDurationArr,expiryDateArr);
         recyclerView.setAdapter(applianceAdapter);
        }











        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent  intent = new Intent(AppliancesMain.this,AppliancesRecord.class);
                 startActivity(intent);

            }
        });

    }public void onResume() {
        super.onResume();
        srnoArr.clear();
        itemNameArrm.clear();
        priceArr.clear();
        ShopArr.clear();
        dateBoughtArr.clear();
        warrantyDurationArr.clear();
        expiryDateArr.clear();
        appliancesDbHelper appliancesDbHelper = new appliancesDbHelper(AppliancesMain.this);
        Cursor data = appliancesDbHelper.fetchAppliance();
        if(data.getCount() == 0){

        }else {
            while (data.moveToNext()){
                srnoArr.add(data.getInt(0));
                itemNameArrm.add(data.getString(1));
                priceArr.add(data.getString(2));
                ShopArr.add(data.getString(3));
                dateBoughtArr.add(data.getString(4));
                warrantyDurationArr.add(data.getString(5));
                expiryDateArr.add(data.getString(6));

            }
            applianceAdapter applianceAdapter = new applianceAdapter(AppliancesMain.this,srnoArr, itemNameArrm, priceArr,ShopArr,dateBoughtArr,warrantyDurationArr,expiryDateArr);
            recyclerView.setAdapter(applianceAdapter);
        }

    }
}