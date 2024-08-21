package com.example.bsaassistant.appliances;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AppliancesRecord extends AppCompatActivity {
    TextInputEditText itemNametv,ExpiryDateTv,prizeTv,shopNameTv,boughtDateTv,warrantyDurationTv;
    Button save,back;
    DatePickerDialog.OnDateSetListener setListener,setListener2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances_record);
        itemNametv = findViewById(R.id.ApplianceitemNameTv);
        ExpiryDateTv = findViewById(R.id.ApplianceWarrantyExpiryTv);
        boughtDateTv = findViewById(R.id.ApplianceDateTIL);
        prizeTv = findViewById(R.id.AcademicPrizeTv);
        back = findViewById(R.id.AppliacneRecordBackbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppliancesRecord.super.onBackPressed();
            }
        });
        shopNameTv = findViewById(R.id.ApplianceShopNameTv);
        warrantyDurationTv = findViewById(R.id.ApplianceWarrantyDurationTv);
        save = findViewById(R.id.ApplianceRecordSaveBtn);
        getDate();
        getAllFields();







        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemNametv.getText().toString().equals("")){
                    Toast.makeText(AppliancesRecord.this, "Enter Title", Toast.LENGTH_SHORT).show();
                }else  if(prizeTv.getText().toString().equals("")){
                    Toast.makeText(AppliancesRecord.this, "Enter Prize", Toast.LENGTH_SHORT).show();
                }else  if ((shopNameTv.getText().toString().equals(""))){
                    Toast.makeText(AppliancesRecord.this, "Enter Shop Name", Toast.LENGTH_SHORT).show();
                }else if(boughtDateTv.getText().toString().equals("")){
                    Toast.makeText(AppliancesRecord.this, "Enter Bought Date", Toast.LENGTH_SHORT).show();
                }else if(warrantyDurationTv.getText().toString().equals("")){
                    Toast.makeText(AppliancesRecord.this, "Enter Warranty Duration", Toast.LENGTH_SHORT).show();
                }else if(ExpiryDateTv.getText().toString().equals("")){
                    Toast.makeText(AppliancesRecord.this, "Enter Expiry Date", Toast.LENGTH_SHORT).show();
                }
                else {

                   appliancesDbHelper appliancesDbHelper = new appliancesDbHelper(AppliancesRecord.this);
                   appliancesDbHelper.addAppliance(itemNametv.getText().toString(), prizeTv.getText().toString(), shopNameTv.getText().toString(), boughtDateTv.getText().toString(),warrantyDurationTv.getText().toString(),ExpiryDateTv.getText().toString());

                    itemNametv.setText("");
                    prizeTv.setText("");

                    shopNameTv.setText("");
                    boughtDateTv.setText("");
                    warrantyDurationTv.setText("");
                    ExpiryDateTv.setText("");
                    AppliancesRecord.super.onBackPressed();

                }
            }
        });




    }
    public  void  getDate(){
        Calendar calendar1 = Calendar.getInstance();

        final int  year = calendar1.get(Calendar.YEAR);
        final  int month = calendar1.get(Calendar.MONTH);
        final  int day  = calendar1.get(Calendar.DAY_OF_MONTH);
        boughtDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AppliancesRecord.this, android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener2,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;

                String date = i2+"/"+i1+"/"+i;
                boughtDateTv.setText(date);


            }
        };



        ExpiryDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AppliancesRecord.this, android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });


        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;

                String date = i2+"/"+i1+"/"+i;
                ExpiryDateTv.setText(date);


            }
        };


    }
    private void getAllFields() {
        itemNametv.setText(getIntent().getStringExtra("passItemName"));
        boughtDateTv.setText(getIntent().getStringExtra("passDateBought"));
        shopNameTv.setText(getIntent().getStringExtra("passShopName"));
        prizeTv.setText(getIntent().getStringExtra("passPrice"));
        ExpiryDateTv.setText(getIntent().getStringExtra("passExpiryDate"));
        warrantyDurationTv.setText(getIntent().getStringExtra("passWarrantyDuration"));


    }
}