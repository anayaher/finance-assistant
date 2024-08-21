package com.example.bsaassistant.gold;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class GoldRecord extends AppCompatActivity {
    TextInputEditText nameTv,dateTv,makingCTv,priceTv,wigTv,rateTv,jewNameTv,costTv,gstTotal,makinCTotalTv;

    Button save;
    TextView detail;
    DatePickerDialog.OnDateSetListener setListener;
    String saveS = "SAVE";
    String detailS = "Enter Detail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_record);
        save = findViewById(R.id.goldRecordSaveBtn);
        detail = findViewById(R.id.EnterDetailTv);
        Integer id = getIntent().getIntExtra("passId", 0);



        costTv= findViewById(R.id.costTv);
        gstTotal = findViewById(R.id.goldRecordTotalGst);
        makinCTotalTv = findViewById(R.id.goldRecordMakingChargesTotal);

        nameTv = findViewById(R.id.goldRecordNameTv);
        dateTv =findViewById(R.id.goldRecordDateTv);
        makingCTv = findViewById(R.id.goldRecordMakingChargesTv);
        priceTv  = findViewById(R.id.goldRecordPriceTv);
        wigTv = findViewById(R.id.goldRecordWIGtv);
        rateTv =findViewById(R.id.goldRecordRateTv);
        jewNameTv = findViewById(R.id.goldRecordJewelerTv);


        setAllFields();

        save.setText(saveS);


        detail.setText(detailS);

        setDateTv();
        makingCTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(makingCTv.getText().toString().equals("0") || wigTv.getText().toString().equals("0")){
                    makinCTotalTv.setText("0");
                }else  if(makingCTv.getText().toString().length()>0 && wigTv.getText().toString().length()>0){
                    Integer gstTotali = Math.round(Float.parseFloat(wigTv.getText().toString())) * Integer.parseInt(makingCTv.getText().toString());
                    makinCTotalTv.setText(String.valueOf(gstTotali));

                }
            }
        });
        gstTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(costTv.getText().toString().equals("0") || gstTotal.getText().toString().equals("0") || makinCTotalTv.getText().toString().equals("0")){
                    priceTv.setText("0");
                }else  if(gstTotal.getText().toString().length()>0 && costTv.getText().toString().length()>0){
                    Integer gstTotali = Math.round(Float.parseFloat(costTv.getText().toString())) + Integer.parseInt(makinCTotalTv.getText().toString()) + Integer.parseInt(gstTotal.getText().toString());
                    priceTv.setText(String.valueOf(gstTotali));

                }

            }
        });



        rateTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (wigTv.getText().toString().equals("0") || rateTv.getText().toString().equals("0")){
                    costTv.setText("0");

                }
                else if(wigTv.getText().toString().length() > 0 && rateTv.getText().toString().length() > 0 ){
                    Float cost = Integer.parseInt(rateTv.getText().toString()) * Float.parseFloat(wigTv.getText().toString());
                    costTv.setText(String.valueOf(cost));

                }

            }
        });




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(save.getText().toString().equals("EDIT")){

                    if (nameTv.getText().toString().equals("")){
                        Toast.makeText(GoldRecord.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    }else if(dateTv.getText().toString().equals("")){
                        Toast.makeText(GoldRecord.this, "EnterDate", Toast.LENGTH_SHORT).show();
                    }else if(priceTv.getText().toString().equals("")){
                        Toast.makeText(GoldRecord.this, "Enter Price", Toast.LENGTH_SHORT).show();

                    }else if(makingCTv.getText().toString().equals(""))
                    {
                        Toast.makeText(GoldRecord.this, "Enter Making Charges", Toast.LENGTH_SHORT).show();

                    }else if(wigTv.getText().toString().equals("")){

                        Toast.makeText(GoldRecord.this, "Enter Weight", Toast.LENGTH_SHORT).show();
                    }else
                    if (jewNameTv.getText().toString().equals("")){
                        Toast.makeText(GoldRecord.this, "Enter Jeweler Name", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        goldDbHelper goldDbHelper = new goldDbHelper(GoldRecord.this);
                        goldDbHelper.updateGold(id,nameTv.getText().toString(), dateTv.getText().toString(), Integer.parseInt(priceTv.getText().toString()), Float.parseFloat(makingCTv.getText().toString()), Float.parseFloat(wigTv.getText().toString()), Integer.parseInt(rateTv.getText().toString()),Integer.parseInt(gstTotal.getText().toString()),Math.round(Float.parseFloat(costTv.getText().toString())), jewNameTv.getText().toString());
                        nameTv.setText("");
                        priceTv.setText("");
                        dateTv.setText("");
                        rateTv.setText("");
                        wigTv.setText("");
                        jewNameTv.setText("");
                        makingCTv.setText("");
                        makinCTotalTv.setText("");
                        gstTotal.setText("");
                        costTv.setText("");
                        Toast.makeText(GoldRecord.this, "Gold Updated Successfully", Toast.LENGTH_SHORT).show();


                    }
                }else if (save.getText().toString().equals("SAVE")){
                    goldDbHelper goldDbHelper = new goldDbHelper(GoldRecord.this);
                    goldDbHelper.addGold(nameTv.getText().toString(), dateTv.getText().toString(), Integer.parseInt(priceTv.getText().toString()), Float.parseFloat(makingCTv.getText().toString()), Float.parseFloat(wigTv.getText().toString()), Integer.parseInt(rateTv.getText().toString()),Integer.parseInt(gstTotal.getText().toString()),Math.round(Float.parseFloat(costTv.getText().toString())), jewNameTv.getText().toString());
                    nameTv.setText("");
                    priceTv.setText("");
                    dateTv.setText("");
                    rateTv.setText("");
                    wigTv.setText("");
                    jewNameTv.setText("");
                    makingCTv.setText("");
                    makinCTotalTv.setText("");
                    gstTotal.setText("");
                    costTv.setText("");
                    Toast.makeText(GoldRecord.this, "Gold Added Successfully", Toast.LENGTH_SHORT).show();


                }
            }
        });

    }


    private void setAllFields() {

        nameTv.setText(getIntent().getStringExtra("passGoldName"));
        dateTv.setText(getIntent().getStringExtra("passGoldDate"));
        priceTv.setText(String.valueOf(getIntent().getIntExtra("passGoldPrice", 0)));
        rateTv.setText(String.valueOf(getIntent().getIntExtra("passGoldRate", 0)));
        wigTv.setText(String.valueOf(getIntent().getIntExtra("passGoldWig", 0)));
        makingCTv.setText(String.valueOf(getIntent().getIntExtra("passGoldMakeCharge", 0)));
        jewNameTv.setText(getIntent().getStringExtra("passGoldJewel" ));
        Float cost = Integer.parseInt(rateTv.getText().toString()) * Float.parseFloat(wigTv.getText().toString());
        costTv.setText(String.valueOf(cost));

        Integer makingTotal = Integer.parseInt(makingCTv.getText().toString()) * Math.round(Float.parseFloat(wigTv.getText().toString()));
        makinCTotalTv.setText(String.valueOf(makingTotal));
        Integer price = Integer.parseInt(priceTv.getText().toString());
        Integer costF = Math.round(cost);

        Integer gstTotald = price-costF-makingTotal;
        gstTotal.setText(String.valueOf(gstTotald));

        saveS = getIntent().getStringExtra("passSave");
        detailS = getIntent().getStringExtra("passMainDetail");
        if(saveS == null){
            saveS  = "SAVE";
            detailS = "Enter Details";

        }




    }
    public void setDateTv(){
        Calendar calendar1 = Calendar.getInstance();

        final int  year = calendar1.get(Calendar.YEAR);
        final  int month = calendar1.get(Calendar.MONTH);
        final  int day  = calendar1.get(Calendar.DAY_OF_MONTH);

        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(GoldRecord.this, android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener,year,month,day);

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
    }

}