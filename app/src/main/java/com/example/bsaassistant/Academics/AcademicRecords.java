package com.example.bsaassistant.Academics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AcademicRecords extends AppCompatActivity {
    TextInputEditText titleTv,dateTv,venueTv,themeTv,detailsTv,dateFrom;
    Button save,back;
    DatePickerDialog.OnDateSetListener setListener,setListener2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_records);
        titleTv = findViewById(R.id.AcademicTitleTv);
        dateTv = findViewById(R.id.AcademicDateTv);
        dateFrom = findViewById(R.id.AcademicDateFromTv);

        venueTv = findViewById(R.id.AcademicVenueTv);
        themeTv = findViewById(R.id.AcademicThemeTv);
        detailsTv = findViewById(R.id.AcademicDetailsTv);
        save = findViewById(R.id.AcademicSaveBtn);
        back = findViewById(R.id.AcademicBackBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AcademicRecords.super.onBackPressed();
            }
        });
        getDate();
        getAllFields();
        academicDbHelper academicDbHelper = new academicDbHelper(AcademicRecords.this);




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titleTv.getText().toString().equals("")){
                    Toast.makeText(AcademicRecords.this, "Enter Title", Toast.LENGTH_SHORT).show();
                }else  if(dateFrom.getText().toString().equals("")){
                    Toast.makeText(AcademicRecords.this, "Enter From Date", Toast.LENGTH_SHORT).show();
                }else  if ((dateTv.getText().toString().equals(""))){
                    Toast.makeText(AcademicRecords.this, "Enter Date To", Toast.LENGTH_SHORT).show();
                }else if(venueTv.getText().toString().equals("")){
                    Toast.makeText(AcademicRecords.this, "Enter Venue", Toast.LENGTH_SHORT).show();
                }else if(themeTv.getText().toString().equals("")){
                    Toast.makeText(AcademicRecords.this, "Enter Theme", Toast.LENGTH_SHORT).show();
                }else {
                  academicDbHelper academicDbHelper = new academicDbHelper(AcademicRecords.this);
                  academicDbHelper.addAcademic(titleTv.getText().toString(),venueTv.getText().toString(),dateFrom.getText().toString(),dateTv.getText().toString(),detailsTv.getText().toString(),themeTv.getText().toString());
                  themeTv.setText("");
                  venueTv.setText("");
                  dateFrom.setText("");
                  dateTv.setText("");
                  titleTv.setText("");
                  detailsTv.setText("");
                  AcademicRecords.super.onBackPressed();

                }
            }
        });
    }

    private void getAllFields() {
        dateTv.setText(getIntent().getStringExtra("passDateTo"));
        dateFrom.setText(getIntent().getStringExtra("passDateFrom"));
        venueTv.setText(getIntent().getStringExtra("passVenue"));
        titleTv.setText(getIntent().getStringExtra("passTop"));
        detailsTv.setText(getIntent().getStringExtra("passDetail"));
        themeTv.setText(getIntent().getStringExtra("passTheme"));


    }


    public  void  getDate(){
        Calendar calendar1 = Calendar.getInstance();

        final int  year = calendar1.get(Calendar.YEAR);
        final  int month = calendar1.get(Calendar.MONTH);
        final  int day  = calendar1.get(Calendar.DAY_OF_MONTH);
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AcademicRecords.this, android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener2,year,month,day);

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



        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AcademicRecords.this, android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });


        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;

                String date = i2+"/"+i1+"/"+i;
                dateFrom.setText(date);


            }
        };


    }
}