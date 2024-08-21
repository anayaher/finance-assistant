package com.example.bsaassistant.Exam;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;


public class Exam_3 extends Fragment {
    TextInputEditText subject,rate,nos,total;
    Button addBtn,substractBtn;
    DatePickerDialog.OnDateSetListener setListener;
    private  String mang;


    Button save;
    String sdds;
    TextInputEditText rateTv, dateTv, nosTv;
    RecyclerView supervisionRV;
    AutoCompleteTextView subjNameTv;
    ArrayList<String> subjArr,dateArr;
    ArrayList<Integer> rateArr,nosArr,srNoArr;
    TextView totaltv;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exam_3,container,false);

        rate = view.findViewById(R.id.rateEdtSupervision);
        nos = view.findViewById(R.id.nosEdtSupervision);
        addBtn = view.findViewById(R.id.addSupervision);
        dateTv = view.findViewById(R.id.dateEdtSupervision);
        substractBtn = view.findViewById(R.id.removeSupervision);
        supervisionRV = view.findViewById(R.id.recylerviewSupervision);
        save = view.findViewById(R.id.addNewSupervision);
        totaltv = view.findViewById(R.id.supervisionTotalTv);
        supervisionRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bundle bundle = getArguments();
        String anay = bundle.getString("passString");
        this.mang = anay;
        dateArr = new ArrayList<>();
        rateArr = new ArrayList<>();
        nosArr = new ArrayList<>();
        srNoArr = new ArrayList<>();
        supervisionDbHelper paperSettingDbHelper = new supervisionDbHelper(getActivity().getBaseContext());
        Cursor data = paperSettingDbHelper.fetchPaperSetting(anay);
        ArrayList<Integer> total = new ArrayList<>();
        if(data.getCount() == 0){

        }else {
            while (data.moveToNext()) {
                srNoArr.add(data.getInt(0));
                dateArr.add(data.getString(2));
                rateArr.add(data.getInt(3));
                nosArr.add(data.getInt(4));


            }

            supervisionAdapter supervisionAdapter = new supervisionAdapter(getActivity(),srNoArr,dateArr,nosArr, rateArr);
            supervisionRV.setAdapter(supervisionAdapter);
        }

        nos.setText("1");

        nos.setEnabled(false);
        Calendar calendar1 = Calendar.getInstance();

        final int  year = calendar1.get(Calendar.YEAR);
        final  int month = calendar1.get(Calendar.MONTH);
        final  int day  = calendar1.get(Calendar.DAY_OF_MONTH);
        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener,year,month,day);

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
        ratesDbHelper ratesDbHelper = new ratesDbHelper(getActivity());
        Cursor rateC = ratesDbHelper.fetchRates();
        int rateVal = 0;
        if(rateC.getCount()==0){
            rate.setText("0");
        }else {
            while (rateC.moveToNext()){
                rateVal = rateC.getInt(1);
            }
            rate.setText(String.valueOf(rateVal));

        }
        ratesDbHelper ratesDbHelper2 = new ratesDbHelper(getActivity());
        Cursor ratesC = ratesDbHelper2.fetchRates();
        int rateVal2 = 0;
        int sum = 0;
        if (ratesC.getCount() == 0){

        }else {

            while (ratesC.moveToNext()) {
                rateVal2 = ratesC.getInt(1);
            }
        }

            for (int i = 0; i < nosArr.size(); i++) {
                sum += nosArr.get(i);


            }
            int totalfinal = sum * rateVal2;
            totaltv.setText(String.valueOf(totalfinal));




        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Integer supervisions = Integer.parseInt(nos.getText().toString());
              nos.setText(String.valueOf(supervisions+1));
            }
        });
        substractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nos.getText().toString().equals("0")){

                }else {
                    Integer supervisions = Integer.parseInt(nos.getText().toString());
                    nos.setText(String.valueOf(supervisions - 1));
                }
            }
        });
        int finalRateVal = rateVal;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateTv.getText().toString().equals("") || rate.getText().toString().equals("") || nos.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "! Pls Enter All Details", Toast.LENGTH_SHORT).show();
                }else {
                    supervisionDbHelper supervisionDbHelper = new supervisionDbHelper(getActivity());
                    supervisionDbHelper.addSuperVision(mang, dateTv.getText().toString(), finalRateVal,Integer.parseInt(nos.getText().toString()));
                    dateTv.setText("");
                    nos.setText("0");




                    srNoArr.clear();
                    dateArr.clear();
                    rateArr.clear();
                    nosArr.clear();


                    supervisionDbHelper paperSettingDbHelper = new supervisionDbHelper(getActivity().getBaseContext());
                    Cursor data = paperSettingDbHelper.fetchPaperSetting(anay);
                    ArrayList<Integer> total = new ArrayList<>();
                    if(data.getCount() == 0){

                    }else {
                        while (data.moveToNext()) {
                            srNoArr.add(data.getInt(0));
                            dateArr.add(data.getString(2));
                            rateArr.add(data.getInt(3));
                            nosArr.add(data.getInt(4));


                        }

                        supervisionAdapter supervisionAdapter = new supervisionAdapter(getActivity(),srNoArr,dateArr,nosArr, rateArr);
                        supervisionRV.setAdapter(supervisionAdapter);
                    }
                    ratesDbHelper ratesDbHelper = new ratesDbHelper(getActivity());
                    Cursor rateC = ratesDbHelper.fetchRates();
                    int rateVal = 0;
                    if(rateC.getCount()==0){
                        rate.setText("0");
                    }else {
                        while (rateC.moveToNext()){
                            rateVal = rateC.getInt(1);
                        }
                        rate.setText(String.valueOf(rateVal));

                    }
                    ratesDbHelper ratesDbHelper2 = new ratesDbHelper(getActivity());
                    Cursor ratesC = ratesDbHelper2.fetchRates();
                    int rateVal2 = 0;
                    int sum = 0;
                    if (ratesC.getCount() == 0){

                    }else {

                        while (ratesC.moveToNext()) {
                            rateVal2 = ratesC.getInt(1);
                        }
                    }

                    for (int i = 0; i < nosArr.size(); i++) {
                        sum += nosArr.get(i);


                    }
                    int totalfinal = sum * rateVal2;
                    totaltv.setText(String.valueOf(totalfinal));



                }

            }
        });
//



        // Inflate the layout for this fragment
        return view;
    }
}