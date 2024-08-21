package com.example.bsaassistant.goldLoan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.example.bsaassistant.gold.goldDbHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class GoldLoanActivity extends AppCompatActivity {
    TextView nameTv,amountTv,dateTv,tenureTv,roiTv,maturityDateTv;
    AutoCompleteTextView itemsTv;
    Button save;
    DatePickerDialog.OnDateSetListener setListener,getSetListener;
    String tv = "ENTER DETAILS";
    String btn = "SAVE";
    ArrayList<String>items;

    TextView glTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_loan);
    nameTv = findViewById(R.id.goldLoanNameTv);
    amountTv = findViewById(R.id.goldLoanAmountTv);
    dateTv = findViewById(R.id.goldLoanDateTv);
    tenureTv = findViewById(R.id.goldLoanTenureTv);
    roiTv = findViewById(R.id.goldLaonroiTv);
    maturityDateTv  = findViewById(R.id.goldMaturityDateTv);
    itemsTv = findViewById(R.id.goldLoanItemsTv);
    save = findViewById(R.id.goldLoanSavebtn);
    glTv =findViewById(R.id.goldLoanTv);
    tv  = getIntent().getStringExtra("passTv");
    btn = getIntent().getStringExtra("passBtn");
    if (tv == null){
        tv = "ENTER DETAILS";
        btn = "SAVE";

    }
    glTv.setText(tv);
    save.setText(btn);
    items = new ArrayList<>();


    goldDbHelper goldDbHelper = new goldDbHelper(GoldLoanActivity.this);
        Cursor data = goldDbHelper.fetchGold();
        if (data.getCount() ==  0){

        }else {
            while (data.moveToNext()){
                items.add(data.getString(1));

            }
            ArrayAdapter arrayAdapter = new ArrayAdapter<>(GoldLoanActivity.this,R.layout.list_item_bsa,items);
            itemsTv.setAdapter(arrayAdapter);
        }

    setFields();
        Calendar calendar1 = Calendar.getInstance();

        final int  year = calendar1.get(Calendar.YEAR);
        final  int month = calendar1.get(Calendar.MONTH);
        final  int day  = calendar1.get(Calendar.DAY_OF_MONTH);
        maturityDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(GoldLoanActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth  ,getSetListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        getSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;


                String date = i2+"/"+i1+"/"+i;
                maturityDateTv.setText(date);


            }
        };
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(GoldLoanActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;


                String date = i2+"/"+i1+"/"+i;
                dateTv.setText(date);


            }
        };



    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (save.getText().toString().equals("SAVE")) {
                if (nameTv.getText().toString().equals("")) {
                    Toast.makeText(GoldLoanActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (amountTv.getText().toString().equals("")) {
                    Toast.makeText(GoldLoanActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (dateTv.getText().toString().equals("")) {
                    Toast.makeText(GoldLoanActivity.this, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (
                        tenureTv.getText().toString().equals("")
                ) {
                    Toast.makeText(GoldLoanActivity.this, "Enter Tenure", Toast.LENGTH_SHORT).show();
                } else if (roiTv.getText().toString().equals("")) {
                    Toast.makeText(GoldLoanActivity.this, "Enter Roi", Toast.LENGTH_SHORT).show();
                } else if (maturityDateTv.getText().toString().equals("")) {
                    Toast.makeText(GoldLoanActivity.this, "Enter Maturity Date", Toast.LENGTH_SHORT).show();
                } else if (itemsTv.getText().toString().equals("")) {
                    Toast.makeText(GoldLoanActivity.this, "Enter Items", Toast.LENGTH_SHORT).show();
                } else {
                    goldLoanDbHelper goldLoanDbHelper = new goldLoanDbHelper(GoldLoanActivity.this);
                    goldLoanDbHelper.addGoldLoan(nameTv.getText().toString(), Integer.parseInt(amountTv.getText().toString()), dateTv.getText().toString(), Integer.parseInt(tenureTv.getText().toString()), Double.parseDouble(roiTv.getText().toString()), maturityDateTv.getText().toString(), itemsTv.getText().toString());
                    Toast.makeText(GoldLoanActivity.this, "Gold Loan Added Successfully", Toast.LENGTH_SHORT).show();

                    nameTv.setText("");
                    maturityDateTv.setText("");
                    roiTv.setText("");
                    itemsTv.setText("");
                    dateTv.setText("");
                    tenureTv.setText("");
                    amountTv.setText("");


                }
            }else if (save.getText().toString().equals("BACK")){
                finish();

            }




        }
    });






    }

    private void setFields() {
        nameTv.setText(getIntent().getStringExtra("passgName" ));
        amountTv.setText(String.valueOf(getIntent().getIntExtra("passgAmt", 0)));
        dateTv.setText(getIntent().getStringExtra("passgDate"));
        tenureTv.setText(String.valueOf(getIntent().getIntExtra("passgTenure",0 )));
        roiTv.setText(String.valueOf(getIntent().getDoubleExtra("passgRoi", 0)));
        maturityDateTv.setText(getIntent().getStringExtra("passgMDate"));
        itemsTv.setText(getIntent().getStringExtra("passgItems"));
    }


}