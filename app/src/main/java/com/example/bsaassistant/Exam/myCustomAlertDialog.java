package com.example.bsaassistant.Exam;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.bsaassistant.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class myCustomAlertDialog extends AlertDialog {
    DatePickerDialog.OnDateSetListener setListener;


    Button save;
    String sdds;
    TextInputEditText  rateTv, dateTv, nosTv;
    AutoCompleteTextView subjNameTv;
    ArrayList<String> subjects;
    ArrayAdapter<String> subadapter,headArrayAdapter;
    private myinterface listener;





    protected myCustomAlertDialog(Context context, String reciveStringTodialog) {


        super(context);

        this.sdds = reciveStringTodialog;










        LayoutInflater inflater = getLayoutInflater();
        listener = (myinterface)context;

        View view = inflater.inflate(R.layout.papersetting_dialog, null);
        save = view.findViewById(R.id.paperSettingSaveBtn);
        subjNameTv = view.findViewById(R.id.subjectTextviewPS);
        Calendar calendar1 = Calendar.getInstance();

        final int  year = calendar1.get(Calendar.YEAR);
        final  int month = calendar1.get(Calendar.MONTH);
        final  int day  = calendar1.get(Calendar.DAY_OF_MONTH);


        rateTv = view.findViewById(R.id.rateTextviewPS);
        rateTv.setEnabled(false);
        dateTv = view.findViewById(R.id.dateTextviewPS);
        nosTv = view.findViewById(R.id.nosTextviewPS);
        subjects = new ArrayList<>();
        setView(view);
        ratesDbHelper ratesDbHelper = new ratesDbHelper(context);
        Cursor ratesCursor = ratesDbHelper.fetchRates();
        if (ratesCursor.getCount()==0){
            rateTv.setText("0");
        }else {
            String rate = null;
            while (ratesCursor.moveToNext()) {
                rate = ratesCursor.getString(2);
            }
            rateTv.setText(rate);

        }SubjectDbhelper subjectDbhelper = new SubjectDbhelper(context);
        Cursor subCursor = subjectDbhelper.fetchSubject();
        if (subCursor.getCount()==0){

        }else {
            while (subCursor.moveToNext()){
                subjects.add(subCursor.getString(0));
            }
            subadapter = new ArrayAdapter<>(context,R.layout.list_item_bsa,subjects);
            subjNameTv.setAdapter(subadapter);

        }

        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Dialog_MinWidth  ,setListener,year,month,day);

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
                if (subjNameTv.getText().toString().equals("") || rateTv.getText().toString().equals("") || dateTv.getText().toString().equals("") || nosTv.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "pls Enter All Values", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        paperSettingDbHelper paperSettingDbHelper = new paperSettingDbHelper(context);


                        paperSettingDbHelper.addPaperSetting(sdds, subjNameTv.getText().toString(), dateTv.getText().toString(), Integer.parseInt(rateTv.getText().toString()), Integer.parseInt(nosTv.getText().toString()));
                       listener.refresh();
                        dismiss();


                    } catch (Exception e) {

                    }

                }


            }
        });


    }



    public interface myinterface {
        void refresh();

    }


}
