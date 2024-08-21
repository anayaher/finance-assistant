package com.example.bsaassistant.bankLoan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class LoanRecords extends AppCompatActivity {
    TextInputEditText bankNameTv,amountTv,tenureTv,roiTv,dateTv,accNoTv,emiTv;
    TextView detailTv;
    Button save;
    String saveS = "SAVE";
    ArrayList<String>dateNewArr;
    Integer tenure = 0;


    String detailS = "Enter Detail";
    DatePickerDialog.OnDateSetListener setListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_records);
        bankNameTv = findViewById(R.id.loanRecordNameTv);
        emiTv = findViewById(R.id.loanRecordEmiTv);
        dateNewArr = new ArrayList<>();

        amountTv = findViewById(R.id.loanRecordAmountTv);
        tenureTv = findViewById(R.id.loanRecordTenureTv);
        roiTv = findViewById(R.id.loanRecordROItv);
        dateTv = findViewById(R.id.loanRecordDateTv);
        accNoTv = findViewById(R.id.loanRecordAccNoTv);
        save = findViewById(R.id.loanRecordSaveBtn);
        detailTv = findViewById(R.id.EnterDetailTvLoan);

        setAllFields();


        save.setText(saveS);
        detailTv.setText(detailS);


            Calendar calendar1 = Calendar.getInstance();

            final int  year = calendar1.get(Calendar.YEAR);
            final  int month = calendar1.get(Calendar.MONTH);
            final  int day  = calendar1.get(Calendar.DAY_OF_MONTH);

            dateTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tenureTv.getText().toString().equals("0") || tenureTv.getText().toString().equals("0")) {
                        Toast.makeText(LoanRecords.this, "Enter Tenure", Toast.LENGTH_SHORT).show();

                    }else {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(LoanRecords.this, android.R.style.Theme_Holo_Dialog_MinWidth, setListener, year, month, day);

                        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        datePickerDialog.show();
                        tenure = Integer.parseInt(tenureTv.getText().toString());

                    }
                }
            });




            setListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    i1=i1+1;
                    String dateCurrent = i2+"/"+i1+"/"+i;
                    dateNewArr.add(i2+"/"+i1+"/"+i);
                    for (int j = 0;j<tenure;j++){
                        i1=i1+1;
                        if(i1>12){
                            i=i+1;
                            i1=1;

                        }
                        String date = i2+"/"+i1+"/"+i;
                        dateNewArr.add(date);


                    }


                    dateTv.setText(dateCurrent);




                }







            };





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(save.getText().toString().equals("SAVE")){

                    if (tenureTv.getText().toString().equals("")) {

                        Toast.makeText(LoanRecords.this, "Enter tenure", Toast.LENGTH_SHORT).show();
                    }

                else if (bankNameTv.getText().toString().equals("")) {
                    Toast.makeText(LoanRecords.this, "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (amountTv.getText().toString().equals("")) {
                    Toast.makeText(LoanRecords.this, "Enter amount", Toast.LENGTH_SHORT).show();

                } else if (roiTv.getText().toString().equals("")) {
                    Toast.makeText(LoanRecords.this, "Enter roi", Toast.LENGTH_SHORT).show();

                } else if (tenureTv.getText().toString().equals("")) {

                    Toast.makeText(LoanRecords.this, "Enter tenure", Toast.LENGTH_SHORT).show();
                }
                else if (dateTv.getText().toString().equals("")) {
                    Toast.makeText(LoanRecords.this, "Enter date", Toast.LENGTH_SHORT).show();
                } else if (accNoTv.getText().toString().equals("")) {
                    Toast.makeText(LoanRecords.this, "Enter Account No Tv", Toast.LENGTH_SHORT).show();
                } else
                {
                    tenure = Integer.parseInt(tenureTv.getText().toString());
                    loanDbHelper loanDbHelper = new loanDbHelper(LoanRecords.this);
                    loanDbHelper.addLoan(bankNameTv.getText().toString(), Integer.parseInt(amountTv.getText().toString()), Integer.parseInt(tenureTv.getText().toString()), Integer.parseInt(emiTv.getText().toString()),Float.parseFloat(roiTv.getText().toString()), dateTv.getText().toString(), accNoTv.getText().toString());
                    emiDbHelper emiDbHelper =  new emiDbHelper(LoanRecords.this);
                    addEmi();

                    bankNameTv.setText("");
                    accNoTv.setText("");
                    dateTv.setText("");
                    amountTv.setText("");
                    roiTv.setText("");
                    tenureTv.setText("");
                    emiTv.setText("");

                    Toast.makeText(LoanRecords.this, "Loan Added Successfully", Toast.LENGTH_SHORT).show();


                }
                }
                else if(save.getText().toString().equals("BACK")){
                    finish();


                }
            }
        });

    }

    private void addEmi() {
       Integer tenureInYears = Integer.parseInt(tenureTv.getText().toString())/12;
       Float IntrestPerYear = (Float.parseFloat(roiTv.getText().toString())/ 100)
               * Integer.parseInt(amountTv.getText().toString());
       Float totalAmtToBePaid = IntrestPerYear * tenureInYears + Integer.parseInt(amountTv.getText().toString());
       Integer noOfEmis =
               Integer.parseInt(tenureTv.getText().toString());

       emiDbHelper emiDbHelper = new emiDbHelper(LoanRecords.this);
      for (int i = 0;i<noOfEmis;i++){
          emiDbHelper.addEmi(bankNameTv.getText().toString(), dateNewArr.get(0+i),"DEC-2022",Integer.parseInt(emiTv.getText().toString()),Double.parseDouble(roiTv.getText().toString()) ,Integer.parseInt(amountTv.getText().toString()),0);

      }


    }

    private void setAllFields() {

        bankNameTv.setText(getIntent().getStringExtra("passLoanName"));
        dateTv.setText(getIntent().getStringExtra("passLoanDate"));
        amountTv.setText(String.valueOf(getIntent().getIntExtra("passLoanAmount", 0)));
        roiTv.setText(String.valueOf(getIntent().getFloatExtra("passLoanRoi", 0)));
        tenureTv.setText(String.valueOf(getIntent().getIntExtra("passLoanTenure", 0)));
        accNoTv.setText(String.valueOf(getIntent().getStringExtra("passLoanAccNo" )));
        emiTv.setText(String.valueOf(getIntent().getIntExtra("passLoanEmi",0)));



        saveS = getIntent().getStringExtra("passSaveLoan");
        detailS = getIntent().getStringExtra("passMainDetailLoan");
        if(saveS == null){
            saveS  = "SAVE";
            detailS = "Enter Details";

        }




    }

    public  void addEmidates(){

           ;
       };
    }
