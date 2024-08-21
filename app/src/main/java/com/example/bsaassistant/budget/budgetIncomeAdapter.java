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

public class budgetIncomeAdapter extends RecyclerView.Adapter<budgetIncomeAdapter.ViewHolder> {

    Context context;
    ArrayList<budgetIncomeModel> budgetIncomeModels;
    private budgetInterface budgetInterface;
    private  TextView incomeTv,expTv,balTv;
    budgetIncomeAdapter(Context context ,ArrayList<budgetIncomeModel> budgetIncomeModels,TextView incomeTv,TextView expTv,TextView balTv,budgetInterface budgetInterface){
        this.context = context;
        this.budgetIncomeModels = budgetIncomeModels;
        this.incomeTv = incomeTv;
        this.expTv = expTv;
        this.budgetInterface = budgetInterface;

        this.balTv = balTv;
        updateTotal();
    }
    @NonNull
    @Override
    public budgetIncomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.disp_budget,parent,false);
      return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull budgetIncomeAdapter.ViewHolder holder, int position) {
        budgetIncomeModel budgetIncomeModel = budgetIncomeModels.get(position);
        String  name = budgetIncomeModel.getName();
        int amount = budgetIncomeModel.getAmount();
        //jk
        JamaKharachDbHelper jamaKharachDbHelper = new JamaKharachDbHelper(context);
        //getLatest Sr to enter
        int sr = jamaKharachDbHelper.getLatestSr();
        String  date = getCurrentDate();
        int latestBal = jamaKharachDbHelper.getCurrentBal();
        int newBal = latestBal + amount;



        holder.nameTv.setTextColor(ContextCompat.getColor(context,R.color.blue));

        holder.amountTv.setTextColor(ContextCompat.getColor(context,R.color.blue));

        holder.nameTv.setText(String.valueOf(budgetIncomeModels.get(position).getName()));
        holder.amountTv.setText(String.valueOf(budgetIncomeModels.get(position).getAmount()));
        holder.checkBox.setChecked(budgetIncomeModels.get(position).isChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                budgetIncomeModel.setChecked(b);
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
                            jamaKharachDbHelper.insertJk(sr,date,1,amount,newBal,name,"Budget","recieved  from " + name);
                            budgetIncomeDbhelper budgetIncomeDbhelper = new budgetIncomeDbhelper(context);
                            budgetIncomeDbhelper.deleteIncomeSource(name);
                            Toast.makeText(context, "SuccessFully Added to Jk", Toast.LENGTH_SHORT).show();
                            budgetInterface.onFunctionCalled();

                        }

                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (budgetInterface != null){
                            budgetIncomeDbhelper budgetIncomeDbhelper = new budgetIncomeDbhelper(context);
                            budgetIncomeDbhelper.deleteIncomeSource(name);
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
        return budgetIncomeModels.size();
    }

    public void updateTotal(){
        int total = 0;
        for (budgetIncomeModel budgetIncomeModel : budgetIncomeModels){
            if (budgetIncomeModel.isChecked()){
                total += budgetIncomeModel.getAmount();

            }
        }
        incomeTv.setText(String.valueOf(total));
        int currExp = Integer.parseInt(expTv.getText().toString());

        int newBal = total - currExp;
        balTv.setText(String.valueOf(newBal));
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
            nameTv = itemView.findViewById(R.id.dispBudgetName);
            amountTv = itemView.findViewById(R.id.dispBudgetAmt);
            linearLayout = itemView.findViewById(R.id.dispBudgetLl);


            checkBox = itemView.findViewById(R.id.dispbudgetCheck);

        }
}
}
