package com.example.bsaassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bsaassistant.databases.PayeeDbHelper;
import com.example.bsaassistant.databases.yenedeneDBhelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class AddYeneDene extends AppCompatActivity {
    TextInputEditText dateEdt,amountedt;
    AutoCompleteTextView head;
    Button save;
    RadioGroup RG1;

    RadioButton R1,R2;
    RecyclerView recyclerView;
    DatePickerDialog.OnDateSetListener setListener;
    ArrayAdapter<String> payeeArrayAdapter,headArrayAdapter;
    ArrayList<String> datearr,payeearr,incomearr,expensearr,balancearr,payeeList,headList,headarr,detailsarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_yene_dene);
        dateEdt = findViewById(R.id.YDdate);
        headList = new ArrayList<>();


        R1 = findViewById(R.id.RYene);
        R2 = findViewById(R.id.RDene);
        amountedt = findViewById(R.id.YDamount);

        save = findViewById(R.id.yenedeneSaveBtn);

        head = findViewById(R.id.YDhead);
        getHead();

        Calendar calendar1 = Calendar.getInstance();

        final int  year = calendar1.get(Calendar.YEAR);
        final  int month = calendar1.get(Calendar.MONTH);
        final  int day  = calendar1.get(Calendar.DAY_OF_MONTH);



        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddYeneDene.this, android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });


        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;

                String date = i2+"/"+i1+"/"+i;
                dateEdt.setText(date);


            }
        };
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dateEdt.getText().toString().equals("")){
                    Toast.makeText(AddYeneDene.this, "Please Enter Date", Toast.LENGTH_SHORT).show();

                }
                else if(head.getText().toString().equals("")){
                    Toast.makeText(AddYeneDene.this, "Please Enter Head", Toast.LENGTH_SHORT).show();

                }
                else if(amountedt.getText().toString().equals("")){
                    Toast.makeText(AddYeneDene.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();

                }
                else if (!R1.isChecked() && !R2.isChecked()){
                    Toast.makeText(AddYeneDene.this, "Please Select Transaction Type", Toast.LENGTH_SHORT).show();

                }

                else
                {
                    try {


                        addTranscation();
                        Toast.makeText(AddYeneDene.this, "Entry Successfull", Toast.LENGTH_SHORT).show();
                        head.setText("");
                        dateEdt.setText("");
                        amountedt.setText("");

                    }catch (Exception e){
                        Toast.makeText(AddYeneDene.this, "Entry Failed", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }
    public void getHead(){
        PayeeDbHelper helper = new PayeeDbHelper(AddYeneDene.this);
        Cursor headlistc = helper.getPayee();
        if (headlistc.getCount() == 0){
            headList.add("expense 1 ");
            headList.add("expense 2");
        }
        else{
            while (headlistc.moveToNext()){

                headList.add(headlistc.getString(0));
            }
        }

        headArrayAdapter = new ArrayAdapter<String>(AddYeneDene.this,R.layout.list_item_bsa,headList);
        head.setAdapter(headArrayAdapter);
    }public void addTranscation(){
        yenedeneDBhelper yenedeneDBhelper = new yenedeneDBhelper(AddYeneDene.this);



        if(R1.isChecked()){
            yenedeneDBhelper.addYeneDene(head.getText().toString(),Integer.parseInt(amountedt.getText().toString()),0, dateEdt.getText().toString());


        }else {
            yenedeneDBhelper.addYeneDene(head.getText().toString(),0,Integer.parseInt(amountedt.getText().toString()), dateEdt.getText().toString());



        }




    }

}