package com.example.bsaassistant.Academics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bsaassistant.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AcadamicsMain extends AppCompatActivity {
    RecyclerView recyclerView;
    Button add;
    ArrayList<Integer>srno;
    ArrayList<String>topArr,venueArr,dateFromArr,dateToArr,themeArr,detailsArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acadamics_main);
        add = findViewById(R.id.addBAcademicMainBtn);
          srno = new ArrayList<>();
        topArr = new ArrayList<>();
        venueArr  = new ArrayList<>();
        dateFromArr = new ArrayList<>();
        dateToArr = new ArrayList<>();
        themeArr = new ArrayList<>();
        detailsArr = new ArrayList<>();
        recyclerView = findViewById(R.id.recylerviewAcademicMain);


        recyclerView.setLayoutManager(new LinearLayoutManager(AcadamicsMain.this));


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcadamicsMain.this,AcademicRecords.class);
                startActivity(intent);
            }
        });
        academicDbHelper academicDbHelper = new academicDbHelper(AcadamicsMain.this);
        Cursor data = academicDbHelper.fetchAcademic();
        if(data.getCount()==0){

        }else {
            while (data.moveToNext()){
                srno.add(data.getInt(0));
                topArr.add(data.getString(1));
                venueArr.add(data.getString(2));
                dateFromArr.add(data.getString(3));
                dateToArr.add(data.getString(4));
                detailsArr.add(data.getString(5));
                themeArr.add(data.getString(6));


            }
            academicAdapter academicAdapter = new academicAdapter(AcadamicsMain.this,srno,topArr,venueArr,dateFromArr,dateToArr,themeArr, detailsArr);
            recyclerView.setAdapter(academicAdapter);

        }
    }
    public void onResume() {
        super.onResume();
        srno.clear();
        topArr.clear();
        venueArr.clear();
        dateFromArr.clear();
        dateToArr.clear();
        detailsArr.clear();
        themeArr.clear();
        academicDbHelper academicDbHelper = new academicDbHelper(AcadamicsMain.this);
        Cursor data = academicDbHelper.fetchAcademic();
        if (data.getCount() == 0) {

        } else {
            while (data.moveToNext()) {
                srno.add(data.getInt(0));
                topArr.add(data.getString(1));
                venueArr.add(data.getString(2));
                dateFromArr.add(data.getString(3));
                dateToArr.add(data.getString(4));
                detailsArr.add(data.getString(5));
                themeArr.add(data.getString(6));


            }
            academicAdapter academicAdapter = new academicAdapter(AcadamicsMain.this, srno, topArr, venueArr, dateFromArr, dateToArr, themeArr, detailsArr);
            recyclerView.setAdapter(academicAdapter);

        }


    }
}