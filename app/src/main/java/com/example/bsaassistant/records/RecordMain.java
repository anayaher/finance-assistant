package com.example.bsaassistant.records;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bsaassistant.Academics.AcadamicsMain;
import com.example.bsaassistant.Exam.ExamMainPage;
import com.example.bsaassistant.R;
import com.example.bsaassistant.appliances.AppliancesMain;
import com.example.bsaassistant.bankRecords.bankRecordMain;
import com.example.bsaassistant.carMaintainance.CarMaintainanceMain;
import com.example.bsaassistant.document.DocumentActivity;

public class RecordMain<Findviewbyid> extends AppCompatActivity {
    Button Exambtn,BankBtn,AcademicBtn,recordBtn,carBtn,docBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_main);


         Exambtn = findViewById(R.id.exambillbtn);
         recordBtn = findViewById(R.id.RecordMainAppliancesBtn);
         carBtn = findViewById(R.id.RecordMainCarBtn);
         docBtn = findViewById(R.id.RecordMainDocumentBtn);

         docBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
Intent intent = new Intent(RecordMain.this, DocumentActivity.class);
startActivity(intent);
             }
         });
         carBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(RecordMain.this, CarMaintainanceMain.class);
                 startActivity(intent);
             }
         });
         recordBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(RecordMain.this, AppliancesMain.class);
                 startActivity(intent);
             }
         });
         AcademicBtn = findViewById(R.id.academicMainBtn);
         AcademicBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(RecordMain.this, AcadamicsMain.class);
                 startActivity(intent);
             }
         });

         BankBtn = findViewById(R.id.recordBankBtn);
         BankBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(RecordMain.this, bankRecordMain.class);
                 startActivity(intent);
             }
         });

         Exambtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(RecordMain.this, ExamMainPage.class);
            startActivity(intent);
        }

    });
    }}