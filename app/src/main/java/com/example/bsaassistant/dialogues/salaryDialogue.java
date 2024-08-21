package com.example.bsaassistant.dialogues;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bsaassistant.R;
import com.example.bsaassistant.reportactivities.SalReportActivity;
import com.example.bsaassistant.salaryactivities.SalSlipActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class salaryDialogue extends Dialog {
    private TextView fromDateTextView;
    private EditText fromDateEditText;
    private TextView toDateTextView;
    private EditText toDateEditText;
    private Button searchButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener,mDateSetListener2;

    public salaryDialogue(Context context) {
        super(context);
        setContentView(R.layout.salary_date_dialogue);

        // Initialize views
        fromDateTextView = findViewById(R.id.fromDateTextView);
        fromDateEditText = findViewById(R.id.fromDateEditText);
        toDateTextView = findViewById(R.id.toDateTextView);
        toDateEditText = findViewById(R.id.toDateEditText);
        searchButton = findViewById(R.id.searchButton);

        toDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();


                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.DAY_OF_MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                //setting the dialog
                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        fromDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();


                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.DAY_OF_MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                //setting the dialog
                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,mDateSetListener2,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = year + "-" + month + "-" + day;

                SimpleDateFormat receivedDateFormat = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
                Date parsedDate = null;
                try {
                    parsedDate = receivedDateFormat.parse(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                // Format the date as "yyyy-mm-dd"
                SimpleDateFormat desiredDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = desiredDateFormat.format(parsedDate);

                toDateEditText.setText(formattedDate);
            }
        };
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = year + "-" + month + "-" + day;

                SimpleDateFormat receivedDateFormat = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
                Date parsedDate = null;
                try {
                    parsedDate = receivedDateFormat.parse(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                // Format the date as "yyyy-mm-dd"
                SimpleDateFormat desiredDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = desiredDateFormat.format(parsedDate);

                fromDateEditText.setText(formattedDate);
            }
        };

        // Set click listener for searchButton
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform search or any desired action
                String fromDate = fromDateEditText.getText().toString();
                String toDate = toDateEditText.getText().toString();

                Intent intent = new Intent(context, SalReportActivity.class);
                intent.putExtra("passSalFromDate",fromDate);
                intent.putExtra("passSalToDate",toDate);
                context.startActivity(intent);
                // TODO: Implement search functionality with fromDate and toDate

                // Close the dialog
                dismiss();
            }
        });
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}

