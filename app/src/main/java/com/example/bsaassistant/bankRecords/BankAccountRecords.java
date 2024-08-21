package com.example.bsaassistant.bankRecords;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class BankAccountRecords extends AppCompatActivity {
    TextInputEditText bankNameTv,ifscTv,accNoTv,cusNoTv,workTimeTv,closedOnTv;
    Button save,back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_accoutn_records);
        bankNameTv = findViewById(R.id.BankNameTv);
        ifscTv = findViewById(R.id.BankIfcsTv);
        accNoTv = findViewById(R.id.BankAccNoTv);
        cusNoTv = findViewById(R.id.BankCusNoTv);
        workTimeTv = findViewById(R.id.BankWorkTv);
        closedOnTv = findViewById(R.id.BankClosedTv);
        save = findViewById(R.id.BankSaveBtn);
        back = findViewById(R.id.BankBackBtn);
        try{
            bankNameTv.setText(getIntent().getStringExtra("passBankName"));
            ifscTv.setText(getIntent().getStringExtra("passIfscNo"));
            accNoTv.setText(getIntent().getStringExtra("passAccNo" ));
            cusNoTv.setText(String.valueOf(getIntent().getIntExtra("passCusNo", 0)));
            workTimeTv.setText(getIntent().getStringExtra("passWorkTime"));
            closedOnTv.setText(getIntent().getStringExtra("passClosedOn"));

        }catch (Exception e){

        }








        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bankNameTv.getText().toString().equals("")){
                    Toast.makeText(BankAccountRecords.this, "Enter Bank Name", Toast.LENGTH_SHORT).show();
                }else if(ifscTv.getText().toString().equals("")){
                    Toast.makeText(BankAccountRecords.this, "Enter IFSC Code", Toast.LENGTH_SHORT).show();
                }else if(accNoTv.getText().toString().equals("")){
                    Toast.makeText(BankAccountRecords.this, "Enter ACC No", Toast.LENGTH_SHORT).show();
                }else  if(cusNoTv.getText().toString().equals("")){
                    Toast.makeText(BankAccountRecords.this, "Enter Cus No", Toast.LENGTH_SHORT).show();
                }else if(workTimeTv.getText().toString().equals("")){
                    Toast.makeText(BankAccountRecords.this, "Enter Work Time", Toast.LENGTH_SHORT).show();
                }else if(closedOnTv.getText().toString().equals("")){
                    Toast.makeText(BankAccountRecords.this, "Enter Closed On", Toast.LENGTH_SHORT).show();
                }

                else {
                    bankRecordDbHelper bankRecordDbHelper = new bankRecordDbHelper(BankAccountRecords.this);
                    bankRecordDbHelper.addBankRecord(bankNameTv.getText().toString(),accNoTv.getText().toString(), Integer.parseInt(cusNoTv.getText().toString()), ifscTv.getText().toString(), closedOnTv.getText().toString(), workTimeTv.getText().toString());
                    bankNameTv.setText("");
                    accNoTv.setText("");
                    cusNoTv.setText("");
                    workTimeTv.setText("");
                    closedOnTv.setText("");




                }

            }
        });





    }
}