package com.example.bsaassistant.dialogues;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bsaassistant.R;
import com.example.bsaassistant.databases.PayeeDbHelper;

import com.example.bsaassistant.reportactivities.JkreportActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

public class JkRepDialog  extends AlertDialog {
    DatePickerDialog.OnDateSetListener setListener,setListener2;
    Button search;
    EditText fromDate,toDate;
    AutoCompleteTextView payeeEdt;
    ArrayList<String> payee;
    ArrayAdapter<String> adapter;
    RadioButton radioButton,customRadioBtn;


    public JkRepDialog(Context context){
        super(context);


        LayoutInflater inflater = getLayoutInflater();


        View view = inflater.inflate(R.layout.dialog_jk_rep, null);
        fromDate = view.findViewById(R.id.fromDateEdt);
        toDate = view.findViewById(R.id.toDateEdt);
        payeeEdt = view.findViewById(R.id.payeeEdt);
        search = view.findViewById(R.id.jkReportSearchBtn);
        radioButton = view.findViewById(R.id.allRecordRadioBtn);
        customRadioBtn = view.findViewById(R.id.customRadioBtn);
        customRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payeeEdt.setText("");
                fromDate.setEnabled(true);
                toDate.setEnabled(true);

            }
        });
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payeeEdt.setText("ALL");

                fromDate.setEnabled(false);
                toDate.setEnabled(false);
            }
        });



        payee = new ArrayList<>();
        PayeeDbHelper payeeDbHelper = new PayeeDbHelper(context);
        Cursor data = payeeDbHelper.getPayee();
        if (data.getCount() == 0){


        }
        else {
            while (data.moveToNext()){


                payee.add(data.getString(0));
                Collections.sort(payee);



            }
            payee.add("ALL");
            adapter = new ArrayAdapter<>(context,R.layout.list_item_bsa,payee);
            payeeEdt.setAdapter(adapter);
        }
        Calendar calendar1 = Calendar.getInstance();
        final int  year = calendar1.get(Calendar.YEAR);
        final  int month = calendar1.get(Calendar.MONTH);
        final  int day  = calendar1.get(Calendar.DAY_OF_MONTH);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();
            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener2,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();
            }
        });setListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    String date = i+"-"+i1+"-"+i2;
                    DateTimeFormatter recievedDateFormat =  DateTimeFormatter.ofPattern("yyyy-M-d");
                    DateTimeFormatter desiredFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date1 = LocalDate.parse(date,recievedDateFormat);
                    String output = date1.format(desiredFormat);
                    toDate.setText(output);


                }




            }
        };




        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    String date = i+"-"+i1+"-"+i2;
                    DateTimeFormatter recievedDateFormat =  DateTimeFormatter.ofPattern("yyyy-M-d");
                    DateTimeFormatter desiredFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate date1 = LocalDate.parse(date,recievedDateFormat);
                    String output = date1.format(desiredFormat);
                    fromDate.setText(output);


                }






            }
        };
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButton.isChecked()){

                    if (payeeEdt.getText().toString().equals(""))
                    {
                        Toast.makeText(context, "Please Select Payee", Toast.LENGTH_SHORT).show();
                        payeeEdt.requestFocus();
                    }
                    else {
                        Intent intent = new Intent(context, JkreportActivity.class);
                        intent.putExtra("passFromDateJk", "START");
                        intent.putExtra("passToDateJk", "END");
                        intent.putExtra("passPayeeJk", "ALL");
                        context.startActivity(intent);

                    }
                }else {

                    if (fromDate.getText().toString().equals("")) {
                        Toast.makeText(context, "Please Select from Date", Toast.LENGTH_SHORT).show();
                        fromDate.requestFocus();

                    } else if (toDate.getText().toString().equals("")) {
                        Toast.makeText(context, "Please Enter To Date", Toast.LENGTH_SHORT).show();
                        toDate.requestFocus();

                    } else if (payeeEdt.getText().toString().equals("")) {
                        Toast.makeText(context, "Please Select Payee", Toast.LENGTH_SHORT).show();
                        payeeEdt.requestFocus();
                    } else {
                        Intent intent = new Intent(context, JkreportActivity.class);
                        intent.putExtra("passFromDateJk", fromDate.getText().toString());
                        intent.putExtra("passToDateJk", toDate.getText().toString());
                        intent.putExtra("passPayeeJk", payeeEdt.getText().toString());
                        context.startActivity(intent);

                    }
                }
            }
        });


        setView(view);
    }
}