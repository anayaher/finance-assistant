package com.example.bsaassistant.carMaintainance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class CarMaintainaneRecords extends AppCompatActivity {
    AutoCompleteTextView type;
    TextInputEditText dateTv,workShopNameTv,costTv,detailTv;
    ArrayList<Integer>srnoArr,costArr;
    ArrayList<String>typeArr,dateArr,workArr,detailArr;
    Button save;
    DatePickerDialog.OnDateSetListener setListener,setListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_maintainane_records);
        type  = findViewById(R.id.CarMaintainanceTypeTv);
        dateTv = findViewById(R.id.CarMaintainanceDateTV);
        workShopNameTv = findViewById(R.id.CarMaintainanceWorkshopTv);
        costTv = findViewById(R.id.CarMainainanceCostTv);
        detailTv = findViewById(R.id.CarMaintainanceDetailTv);
        save = findViewById(R.id.CarMaintainanceSaveBtn);
        getDate();
        getAllfields();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.getText().toString().equals("")){
                    Toast.makeText(CarMaintainaneRecords.this, "Enter Type", Toast.LENGTH_SHORT).show();
                }else  if (dateTv.getText().toString().equals("")){
                    Toast.makeText(CarMaintainaneRecords.this, "Enter Date", Toast.LENGTH_SHORT).show();

                }else if (costTv.getText().toString().equals("")){
                    Toast.makeText(CarMaintainaneRecords.this, "Enter Cost", Toast.LENGTH_SHORT).show();
                }else if(workShopNameTv.getText().toString().equals("")){
                    Toast.makeText(CarMaintainaneRecords.this, "Enter Workshop Name", Toast.LENGTH_SHORT).show();
                }else{
                    carMaintainanceDbHelper carMaintainanceDbHelper = new carMaintainanceDbHelper(CarMaintainaneRecords.this);
                    carMaintainanceDbHelper.addMaintainance(type.getText().toString(),dateTv.getText().toString(),Integer.parseInt(costTv.getText().toString()),workShopNameTv.getText().toString(),detailTv.getText().toString());
                    CarMaintainaneRecords.super.onBackPressed();


                }


            }
        });

    }

    private void getDate() {
        Calendar calendar1 = Calendar.getInstance();

        final int  year = calendar1.get(Calendar.YEAR);
        final  int month = calendar1.get(Calendar.MONTH);
        final  int day  = calendar1.get(Calendar.DAY_OF_MONTH);
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CarMaintainaneRecords.this, android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener2,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;

                String date = i2+"/"+i1+"/"+i;
                dateTv.setText(date);


            }
        };


    }

    private void getAllfields() {
        dateTv.setText(getIntent().getStringExtra("passDateCar"));
        type.setText(getIntent().getStringExtra("passType"));
        costTv.setText(String.valueOf(getIntent().getIntExtra("passCost", 0)));
        workShopNameTv.setText(getIntent().getStringExtra("passWork"));
        detailTv.setText(getIntent().getStringExtra("passDetailCar"));

    }
}