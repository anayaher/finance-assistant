package com.example.bsaassistant.dialogues;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bsaassistant.R;
import com.example.bsaassistant.databases.JamaKharachDbHelper;
import com.example.bsaassistant.databases.PayeeDbHelper;
import com.example.bsaassistant.databases.salaryDatabases.HeadDbHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class JkDialog extends Dialog {
    TextInputEditText srNoEdt,dateEdt,amtEdt,detailsEdt;
    RadioButton incRadio,expRadio;

    AutoCompleteTextView payeeEdt,headEdt;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    Button save;
    int year,month,day;
    ArrayAdapter<String> payeeAdapter,headAdapter;
    public JkDialog( Context context,ArrayAdapter<String> payeeAdapter,ArrayAdapter<String>headAdapter,int sr,int bal) {
        super(context);
        setContentView(R.layout.dialogue_jk);
        Window window = getWindow();
        if (window != null){
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        }


        srNoEdt = findViewById(R.id.jkDialogSrEdt);
        dateEdt = findViewById(R.id.jkDialogDateEdt);
        amtEdt = findViewById(R.id.jkDialogAmtEdt);
        incRadio = findViewById(R.id.jkDialogIncR);
        expRadio = findViewById(R.id.jkDialogExpR);
        detailsEdt = findViewById(R.id.jkDialogDetEdt);

        save = findViewById(R.id.jkDialogSaveBtn);

        incRadio.setChecked(true);

        payeeEdt = findViewById(R.id.jkDialogPayeeEdt);
        headEdt = findViewById(R.id.jkDialogHeadEdt);

        srNoEdt.setText(String.valueOf(sr));
        payeeEdt.setAdapter(payeeAdapter);
        headEdt.setAdapter(headAdapter);
        Calendar calendar = null;
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month= month+1;
        String date = year + "-" + month + "-" + day;
        SimpleDateFormat receivedDateFormat = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
        Date parsedDate = null;
        try {
            parsedDate = receivedDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        SimpleDateFormat desiredDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        assert parsedDate != null;
        String formattedDate = desiredDateFormat.format(parsedDate);

        dateEdt.setText(formattedDate);
        incRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amtEdt.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.showSoftInput(amtEdt, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        expRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amtEdt.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.showSoftInput(amtEdt, InputMethodManager.SHOW_IMPLICIT);
                }


            }
        });


        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = null;
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);

                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog,onDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {


                String date = year + "-" + month + "-" + day;
                SimpleDateFormat receivedDateFormat = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
                Date parsedDate = null;
                try {
                    parsedDate = receivedDateFormat.parse(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                SimpleDateFormat desiredDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                assert parsedDate != null;
                String formattedDate = desiredDateFormat.format(parsedDate);

                dateEdt.setText(formattedDate);
            }
        };







        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currBal= 0;
                if(allTextViewAreAllCompleted()){

                if (incRadio.isChecked()){


                    currBal = bal + Integer.parseInt(amtEdt.getText().toString());
                    //Add Payee to DB
                    PayeeDbHelper payeeDbHelper  = new PayeeDbHelper(context);
                    HeadDbHelper headDbHelper  = new HeadDbHelper(context);

                    payeeDbHelper.addPayee(payeeEdt.getText().toString());
                    headDbHelper.addHead(headEdt.getText().toString());
                //add Income

                    JamaKharachDbHelper jamaKharachDbHelper = new JamaKharachDbHelper(context);
                    jamaKharachDbHelper.insertJk(Integer.parseInt(srNoEdt.getText().toString()),dateEdt.getText().toString(),1,Integer.parseInt(amtEdt.getText().toString()),currBal,payeeEdt.getText().toString(),headEdt.getText().toString(),
                            detailsEdt.getText().toString());
                    Toast.makeText(context, "Income " + amtEdt.getText().toString() + " added", Toast.LENGTH_SHORT).show();
                    clearAllFields();
                    dismiss();


                }
                else {
                    //add Expense
                    currBal = bal-Integer.parseInt(amtEdt.getText().toString());

                    //Add Payee to DB
                    PayeeDbHelper payeeDbHelper  = new PayeeDbHelper(context);
                    HeadDbHelper headDbHelper  = new HeadDbHelper(context);

                    payeeDbHelper.addPayee(payeeEdt.getText().toString());
                    headDbHelper.addHead(headEdt.getText().toString());

                    JamaKharachDbHelper jamaKharachDbHelper = new JamaKharachDbHelper(context);
                    jamaKharachDbHelper.insertJk(Integer.parseInt(srNoEdt.getText().toString()),dateEdt.getText().toString(),0,Integer.parseInt(amtEdt.getText().toString()),currBal,payeeEdt.getText().toString(),headEdt.getText().toString(),
                            detailsEdt.getText().toString());
                    Toast.makeText(context, "Expense " + amtEdt.getText().toString() + " added", Toast.LENGTH_SHORT).show();
                clearAllFields();
                dismiss();
                }


                }else {
                    Toast.makeText(context, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }

            }
        });









    }

    private void clearAllFields() {
        dateEdt.setText("");
        amtEdt.setText("");
        payeeEdt.setText("");
        headEdt.setText("");
        detailsEdt.setText("");
        srNoEdt.setText("");

    }

    private boolean allTextViewAreAllCompleted() {
        return !TextUtils.isEmpty(dateEdt.getText()) && !TextUtils.isEmpty(payeeEdt.getText()) &&
                !TextUtils.isEmpty(amtEdt.getText()) && !TextUtils.isEmpty(headEdt.getText());
    }

    private void setView(Context context,int sr) {

        //set Date





    }
}
