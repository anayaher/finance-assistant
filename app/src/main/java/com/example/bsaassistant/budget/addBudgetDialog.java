package com.example.bsaassistant.budget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bsaassistant.R;
import com.google.android.material.textfield.TextInputEditText;

public class addBudgetDialog extends Dialog {
RadioButton incR,expR;
AutoCompleteTextView nameEdt;
TextInputEditText amtEdt;
Button save;


    public addBudgetDialog(@NonNull Context context) {
        super(context);
        LayoutInflater inflater = getLayoutInflater();


        View view = inflater.inflate(R.layout.dialogue_add_budget, null);

        incR = view.findViewById(R.id.dialogueBudgetIncRadio);
        expR = view.findViewById(R.id.dialoguebudgetExpenseRadio);
        nameEdt = view.findViewById(R.id.addBudgetNameEdt);
        amtEdt = view.findViewById(R.id.addBudgetAmtEdt);

        save = view.findViewById(R.id.addBudgetSaveBtn);
        budgetIncomeDbhelper budgetIncomeDbhelper = new budgetIncomeDbhelper(context);
        budgetExpenseDbhelper budgetExpenseDbhelper = new budgetExpenseDbhelper(context);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!nameEdt.getText().equals("") && !amtEdt.getText().equals("")){

                   if (incR.isChecked()){
                       //adding to income
                       budgetIncomeDbhelper.addIncomeSource(nameEdt.getText().toString(),Integer.parseInt(amtEdt.getText().toString()),0);
                       Toast.makeText(context, "Income Saved SuccessFully", Toast.LENGTH_SHORT).show();
                       dismiss();

                   }
                   else if (expR.isChecked()){
                       budgetExpenseDbhelper.addExpenseSource(nameEdt.getText().toString(),Integer.parseInt(amtEdt.getText().toString()),0);
                       Toast.makeText(context, "Expense Saved SuccessFully", Toast.LENGTH_SHORT).show();
                       dismiss();

                       //adding to Expense

                   }
                   else {
                       Toast.makeText(context, "Please Enter Name And Field", Toast.LENGTH_SHORT).show();
                   }
               }
            }
        });

        setContentView(view);

        Window window = getWindow();
        if (window != null){
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }


}
