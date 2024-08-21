package com.example.bsaassistant.budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsaassistant.R;
import com.example.bsaassistant.databases.JamaKharachDbHelper;

import java.util.ArrayList;

public class BudgetActivity extends AppCompatActivity implements  budgetInterface {
    Button add;
  ArrayList<budgetIncomeModel> budgetIncomeModels;
  ArrayList<budgetExpenseModel>budgetExpenseModels;


    TextView totalIncomeEdt,totalExpEdt,totalBalTv;
    RecyclerView incRv,expRv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        add = findViewById(R.id.addBudget);
        totalExpEdt = findViewById(R.id.budgetExpenseTv);
        totalIncomeEdt = findViewById(R.id.budgetIncomeTv);
        totalBalTv = findViewById(R.id.budgetBalanceTv);


        incRv = findViewById(R.id.budgetSourceRv);
        expRv = findViewById(R.id.budgetExpenseRv);
        incRv.setLayoutManager(new LinearLayoutManager(BudgetActivity.this));
        expRv.setLayoutManager(new LinearLayoutManager(BudgetActivity.this));
        addDefaultBudget();
        setView();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBudgetDialog addBudgetDialog  = new addBudgetDialog(BudgetActivity.this);
                addBudgetDialog.show();
                addBudgetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        setView();

                    }
                });
            }
        });

      

    }
   private void addDefaultBudget(){

        int amt = 0;
       JamaKharachDbHelper jamaKharachDbHelper = new JamaKharachDbHelper(this);
       amt  = jamaKharachDbHelper.getCurrentBal();
       budgetIncomeDbhelper budgetIncomeDbhelper  = new budgetIncomeDbhelper(this);
               budgetIncomeDbhelper.addIncomeSource("JkBalance",amt,0);


   }

    private void setView() {
        budgetIncomeDbhelper budgetIncomeDbhelper = new budgetIncomeDbhelper(BudgetActivity.this);
        budgetExpenseDbhelper budgetExpenseDbhelper = new budgetExpenseDbhelper(BudgetActivity.this);


        budgetIncomeModels = new ArrayList<>();
        budgetExpenseModels = new ArrayList<>();
        budgetIncomeModels = budgetIncomeDbhelper.getIncomeSources();
        budgetExpenseModels = budgetExpenseDbhelper.getExpenseSource();


        budgetIncomeAdapter budgetIncomeAdapter  = new budgetIncomeAdapter(BudgetActivity.this,budgetIncomeModels,totalIncomeEdt,totalExpEdt,totalBalTv,this);
        budgetExpenseAdapter budgetExpenseAdapter = new budgetExpenseAdapter(BudgetActivity.this,budgetExpenseModels,totalExpEdt,totalIncomeEdt,totalBalTv,this);

        incRv.setAdapter(budgetIncomeAdapter);
        expRv.setAdapter(budgetExpenseAdapter);






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        budgetIncomeDbhelper budgetIncomeDbhelper = new budgetIncomeDbhelper(this);
        budgetIncomeDbhelper.deleteIncomeSource("JkBalance");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        budgetIncomeDbhelper budgetIncomeDbhelper = new budgetIncomeDbhelper(this);
        budgetIncomeDbhelper.deleteIncomeSource("JkBalance");
    }

    @Override
    public void onFunctionCalled() {
        setView();

    }

}