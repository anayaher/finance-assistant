package com.example.bsaassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsaassistant.adapters.yeneDeneAdapter;
import com.example.bsaassistant.databases.yenedeneDBhelper;

import java.util.ArrayList;

public class MainYeneDeneActivity extends AppCompatActivity {
    Button add;
    RecyclerView recyclerView;
    ArrayList<String> YDheadArr,YDdateArr;
    ArrayList<Integer> YDinArr,YDoutArr;
    TextView totalYene,totalDene,baltv;
    ArrayList<Integer>srno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_yene_dene);
        add = findViewById(R.id.addtransBtn);
        YDheadArr = new ArrayList<>();
        YDdateArr = new ArrayList<>();
        YDinArr = new ArrayList<>();
        YDoutArr = new ArrayList<>();
        srno = new ArrayList<>();
        totalDene = findViewById(R.id.YDtotalDenetv);
        totalYene = findViewById(R.id.YDtotalYenetv);
        baltv = findViewById(R.id.MainBalancetvYD);

        recyclerView = findViewById(R.id.YDrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainYeneDeneActivity.this));
        yenedeneDBhelper yenedeneDBhelper = new yenedeneDBhelper(MainYeneDeneActivity.this);
        Cursor data = yenedeneDBhelper.fetchYeneDene();
        baltv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yenedeneDBhelper yenedeneDBhelper = new yenedeneDBhelper(MainYeneDeneActivity.this);
                yenedeneDBhelper.deleteAll();

            }
        });
        if(data.getCount() == 0){

        }else{


            while(data.moveToNext()){

                YDdateArr.add(data.getString(4));
                srno.add(data.getInt(0));
                YDheadArr.add(data.getString(1));
                YDinArr.add(data.getInt(2));
                YDoutArr.add(data.getInt(3));



            }
            yeneDeneAdapter adapter = new yeneDeneAdapter(MainYeneDeneActivity.this,YDdateArr, YDheadArr, YDinArr, YDoutArr,srno);
            recyclerView.setAdapter(adapter);



        }
        int sum = 0;
        for (int i = 0;i<YDinArr.size();i++){
            sum += YDinArr.get(i);



        }totalYene.setText(String.valueOf(sum));
        int Dene = 0;
        for (int i = 0;i<YDinArr.size();i++){
            Dene += YDoutArr.get(i);



        }totalDene.setText(String.valueOf(Dene));
        baltv.setText(String.valueOf(sum-Dene));




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainYeneDeneActivity.this,AddYeneDene.class);
                startActivity(intent);


            }
        });

    }


    public  void fetchData(){
        yenedeneDBhelper yenedeneDBhelper = new yenedeneDBhelper(MainYeneDeneActivity.this);
        Cursor data2 = yenedeneDBhelper.fetchYeneDene();
        if(data2.getCount() == 0){

        }else{


            while (data2.moveToNext()){
                YDdateArr.add(data2.getString(4));
                YDheadArr.add(data2.getString(1));
                srno.add(data2.getInt(0));
                YDinArr.add(data2.getInt(2));
                YDoutArr.add(data2.getInt(3));



            }
            yeneDeneAdapter adapter2 = new yeneDeneAdapter(MainYeneDeneActivity.this,YDdateArr, YDheadArr, YDinArr, YDoutArr,srno);
            recyclerView.setAdapter(adapter2);



        }



    }public void onResume() {
        super.onResume();
        YDinArr.clear();
        YDoutArr.clear();
        YDdateArr.clear();
        YDheadArr.clear();
        srno.clear();
        fetchData();
        recyclerView.scrollToPosition(YDdateArr.size()-1);
        int sum = 0;
        for (int i = 0;i<YDinArr.size();i++){
            sum += YDinArr.get(i);



        }totalYene.setText(String.valueOf(sum));
        int Dene = 0;
        for (int i = 0;i<YDinArr.size();i++){
            Dene += YDoutArr.get(i);



        }totalDene.setText(String.valueOf(Dene));
        baltv.setText(String.valueOf(sum-Dene));
















    }
}