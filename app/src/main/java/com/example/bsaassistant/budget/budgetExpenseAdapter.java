package com.example.bsaassistant.budget;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;
import com.example.bsaassistant.databases.JamaKharachDbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class budgetExpenseAdapter extends RecyclerView.Adapter<budgetExpenseAdapter.ViewHolder> {
    private TextView expenseTv,incTv,balTv;
    Context context;
    private  budgetInterface budgetInterface;
    ArrayList<budgetExpenseModel> budgetExpenseModels;

    budgetExpenseAdapter(Context context,ArrayList<budgetExpenseModel> budgetExpenseModels,TextView expenseTv,TextView incTv,TextView balTv,budgetInterface budgetInterface) {
        this.budgetExpenseModels = budgetExpenseModels;
        this.context = context;
        this.expenseTv = expenseTv;
        this.incTv = incTv;
        this.budgetInterface = budgetInterface;
        this.balTv = balTv;

        updateTotal();
    }

    @NonNull
    @Override
    public budgetExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.disp_budget,parent,false);
       return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull budgetExpenseAdapter.ViewHolder holder, int position) {
        budgetExpenseModel budgetExpenseModel = budgetExpenseModels.get(position);
        String name = budgetExpenseModel.getName();
        int CurrBal = budgetExpenseModel.getAmount();
        int amount = budgetExpenseModel.getAmount();

        //jk
        JamaKharachDbHelper jamaKharachDbHelper = new JamaKharachDbHelper(context);
        int sr = jamaKharachDbHelper.getLatestSr();
        int bal = jamaKharachDbHelper.getCurrentBal();
        String date = getCurrentDate();
        int newBal = bal- CurrBal;

        holder.nameTv.setTextColor(ContextCompat.getColor(context,R.color.red));
        holder.amountTv.setTextColor(ContextCompat.getColor(context,R.color.red));

        holder.nameTv.setText(String.valueOf(budgetExpenseModels.get(position).getName()));
        holder.amountTv.setText(String.valueOf(budgetExpenseModels.get(position).getAmount()));
        holder.checkBox.setChecked(budgetExpenseModels.get(position).isChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                budgetExpenseModel.setChecked(b);
                updateTotal();

            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select An Action");
                builder.setMessage("Do You Wish To Delete Or Add This Income in Jk");
                builder.setPositiveButton("Paid", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        if (budgetInterface != null){
                            jamaKharachDbHelper.insertJk(sr,date,0,amount,newBal,name,"Budget","Paid  To " + name);
                            budgetExpenseDbhelper budgetExpenseDbhelper = new budgetExpenseDbhelper(context);
                            budgetExpenseDbhelper.deleteExpenseSource(name);
                            Toast.makeText(context, "SuccessFully Added to Jk", Toast.LENGTH_SHORT).show();
                            budgetInterface.onFunctionCalled();

                        }
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (budgetInterface != null){
                            budgetExpenseDbhelper budgetIncomeDbhelper = new budgetExpenseDbhelper(context);
                            budgetIncomeDbhelper.deleteExpenseSource(name);
                            Toast.makeText(context, "Deleted Income  " + name, Toast.LENGTH_SHORT).show();
                            budgetInterface.onFunctionCalled();
                        }
                    }
                });
                builder.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return budgetExpenseModels.size();
    }
    private  void  updateTotal(){
        int total =0;

        for (budgetExpenseModel budgetExpenseModel : budgetExpenseModels){
            if (budgetExpenseModel.isChecked()){
                total += budgetExpenseModel.getAmount();

            }
        }
        int currInc = Integer.parseInt(incTv.getText().toString());
        int bal = currInc - total;
        expenseTv.setText(String.valueOf(total));
        balTv.setText(String.valueOf(bal));

    }
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv,amountTv;
        LinearLayout linearLayout;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.dispBudgetLl);

            nameTv = itemView.findViewById(R.id.dispBudgetName);
            amountTv = itemView.findViewById(R.id.dispBudgetAmt);
            checkBox = itemView.findViewById(R.id.dispbudgetCheck);

        }
    }
}
