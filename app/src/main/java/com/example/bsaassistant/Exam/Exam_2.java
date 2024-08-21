package com.example.bsaassistant.Exam;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Exam_2 extends Fragment {
    private  String anayfinal;
    TextInputEditText subject,rate,nos;
    TextView totaltv;
    Button save;
    RecyclerView paperSettingRecylerView;
    private  String mang;
    ArrayList<String> subjArr,dateArr;
    ArrayList<Integer> rateArr,nosArr,srNoArr;


    public Exam_2() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_exam_2, container,false);
        save =  view.findViewById(R.id.addNewPaperChecking);
        paperSettingRecylerView = view.findViewById(R.id.recylerviewPaperChecking);
        paperSettingRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        totaltv = (TextView) view.findViewById(R.id.paperCheckingTotal);





        Bundle bundle = getArguments();
        String anay = bundle.getString("passString");
        this.mang = anay;
        srNoArr = new ArrayList<>();
        subjArr = new ArrayList<>();
        dateArr = new ArrayList<>();
        rateArr = new ArrayList<>();
        nosArr = new ArrayList<>();
        paperCheckingDbHelper paperSettingDbHelper = new paperCheckingDbHelper(getActivity().getBaseContext());
        Cursor data = paperSettingDbHelper.fetchPaperChecking(anay);
        ArrayList<Integer> total = new ArrayList<>();
        if(data.getCount() == 0){

        }else {
            while (data.moveToNext()){
                srNoArr.add(data.getInt(0));
                subjArr.add(data.getString(2));
                dateArr.add(data.getString(3));
                rateArr.add(data.getInt(4));
                nosArr.add(data.getInt(5));


            }
            paperCheckingAdapter paperChekingAdapter = new paperCheckingAdapter(getActivity(),srNoArr,subjArr,dateArr,nosArr,rateArr);
            paperSettingRecylerView.setAdapter(paperChekingAdapter);
            ratesDbHelper ratesDbHelper = new ratesDbHelper(getActivity());
            Cursor ratesC = ratesDbHelper.fetchRates();
            int rateVal = 0;
            int sum = 0;
            if (ratesC.getCount() == 0){

            }else {

                while (ratesC.moveToNext()) {
                    rateVal = ratesC.getInt(0);
                }
            }

                for (int i = 0; i < nosArr.size(); i++) {
                    sum += nosArr.get(i);


                }
                int totalfinal = sum * rateVal;
                totaltv.setText(String.valueOf(totalfinal));
            }







        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alert = new paperCheckingDialog(getActivity(),anay);

                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        try {
                            paperCheckingDbHelper paperSettingDbHelper = new paperCheckingDbHelper(getActivity().getBaseContext());
                            Cursor data2 = paperSettingDbHelper.fetchPaperChecking(mang);
                            srNoArr.clear();
                            subjArr.clear();
                            dateArr.clear();
                            rateArr.clear();
                            nosArr.clear();

                            if(data2.getCount() == 0){

                            }else {
                                while (data2.moveToNext()) {
                                    srNoArr.add(data2.getInt(0));
                                    subjArr.add(data2.getString(2));
                                    dateArr.add(data2.getString(3));
                                    rateArr.add(data2.getInt(4));
                                    nosArr.add(data2.getInt(5));

                                }
                                paperCheckingAdapter paperChekingAdapter = new paperCheckingAdapter(getActivity(), srNoArr, subjArr, dateArr, nosArr, rateArr);
                                paperSettingRecylerView.setAdapter(paperChekingAdapter);

                            }
                            ratesDbHelper ratesDbHelper = new ratesDbHelper(getActivity());
                            Cursor ratesC = ratesDbHelper.fetchRates();
                            int rateVal = 0;
                            int sum = 0;
                            if (ratesC.getCount() == 0){

                            }else {

                                while (ratesC.moveToNext()) {
                                    rateVal = ratesC.getInt(0);
                                }
                            }

                            for (int i = 0; i < nosArr.size(); i++) {
                                sum += nosArr.get(i);


                            }
                            int totalfinal = sum * rateVal;
                            totaltv.setText(String.valueOf(totalfinal));


                        }catch (Exception e){
                            Log.d(TAG, "onDismiss: " +e);

                        }



                    }
                });

                alert.show();



            }
        });
        return view;

    }
}