package com.example.bsaassistant.gold;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class GoldMain extends AppCompatActivity {
    TextView totalTv,totalweight;
    Button add;
    RecyclerView recyclerView;
    ArrayList<Integer> srnoArr,wigArr,priceArr,rateArr,makeChargeArr,gstArr,costArr;
    ArrayList<String>nameArr,dateArr,jewelerArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_main);
        totalTv = findViewById(R.id.goldMainTotal);
        add = findViewById(R.id.goldMainAddGold);
        srnoArr = new ArrayList<>();
        wigArr = new ArrayList<>();
        priceArr = new ArrayList<>();
        rateArr = new ArrayList<>();
        makeChargeArr = new ArrayList<>();
        nameArr = new ArrayList<>();
        dateArr = new ArrayList<>();
        jewelerArr = new ArrayList<>();
        totalweight = findViewById(R.id.goldMainTotalWeight);



        recyclerView = findViewById(R.id.recylerviewGoldMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(GoldMain.this));
        setRecyclerView();




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoldMain.this,GoldRecord.class);
                startActivity(intent);
            }
        });
    }

    private void setRecyclerView() {
        goldDbHelper goldDbHelper = new goldDbHelper(GoldMain.this);
        Cursor data = goldDbHelper.fetchGold();
        int sum = 0;
        int tw = 0;
        if (data.getCount() == 0){}
        else {

            while (data.moveToNext()) {
                srnoArr.add(data.getInt(0));
                nameArr.add(data.getString(1));
                dateArr.add(data.getString(2));
                priceArr.add(data.getInt(3));
                makeChargeArr.add(data.getInt(4));
                wigArr.add(data.getInt(5));
                rateArr.add(data.getInt(6));
                jewelerArr.add(data.getString(9));

            }
            for (int i = 0;i<priceArr.size();i++){
                sum += priceArr.get(i);

            }
            for (int l = 0;l<wigArr.size();l++){
                tw += wigArr.get(l);
            }
            String twf = String.valueOf(tw) + " gms";
            totalweight.setText(twf)  ;
            totalTv.setText(String.valueOf(sum));
            goldAdapter goldAdapter = new goldAdapter(GoldMain.this,srnoArr,wigArr,priceArr,rateArr,makeChargeArr,nameArr,dateArr,jewelerArr);
            recyclerView.setAdapter(goldAdapter);
        }

    }public void onResume() {

        super.onResume();
        srnoArr.clear();
        nameArr.clear();
        dateArr.clear();
        priceArr.clear();
        makeChargeArr.clear();
        wigArr.clear();
        rateArr.clear();
        jewelerArr.clear();
        setRecyclerView();
    }
}