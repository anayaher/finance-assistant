package com.example.bsaassistant.share;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bsaassistant.R;
import com.example.bsaassistant.bankLoan.LoanMain;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AddShareDialog extends Dialog {
    TextInputEditText name,prev,emi,amount;


    Button add;


    public AddShareDialog(@NonNull Context context) {
        super(context);
        LayoutInflater inflater = getLayoutInflater();
        String date = "12/12/12";


        View view = inflater.inflate(R.layout.dialog_add_share, null);
        add = view.findViewById(R.id.shareDialogButton);
        name = view.findViewById(R.id.shareDialogNameTv);
        prev = view.findViewById(R.id.shareDialogBalTv);
        emi = view.findViewById(R.id.sharesDialogAmountTv);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM-yyyy"));
        }


        String finalDate = date;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllViews()) {
                    ShareDbHelper shareDbHelper = new ShareDbHelper(getContext());
                    ShareTransactionDbHelper shareTransactionDbHelper = new ShareTransactionDbHelper(getContext());




                    shareDbHelper.addShare(name.getText().toString(),Integer.parseInt(prev.getText().toString()),Integer.parseInt(emi.getText().toString()),0);
                    shareTransactionDbHelper.addShareTransaction(name.getText().toString(),Integer.parseInt(prev.getText().toString()), finalDate);

                    Toast.makeText(getContext(), "Share Created SuccessFully", Toast.LENGTH_SHORT).show();
                    dismiss();
                }

            }
        });
        Window window = getWindow();

        setContentView(view);
        if (window != null)
        {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }

    private boolean checkAllViews() {
        return !TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(prev.getText()) && !TextUtils.isEmpty(emi.getText()) ;
    }
}
