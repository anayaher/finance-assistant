package com.example.bsaassistant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;
import com.example.bsaassistant.datamodels.jkModel;

import java.util.ArrayList;

public class JkRepAdapter extends RecyclerView.Adapter<JkRepAdapter.ViewHolder> {
    Context context;

    ArrayList<jkModel> jkModels;




    public JkRepAdapter(Context context,ArrayList<jkModel> jkModels){
        this.context = context;
        this.jkModels = jkModels;
    }
    @NonNull
    @Override
    public  JkRepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.display_jk_rep,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull JkRepAdapter.ViewHolder holder, int position) {

        int type = jkModels.get(position).getType();

        holder.srEdt.setText(String.valueOf(jkModels.get(position).getSr()));
        holder.dateEdt.setText(String.valueOf(jkModels.get(position).getDate()));
       if (type == 1){
           holder.incomeEdt.setText(String.valueOf(jkModels.get(position).getAmt()));
           holder.expenseEdt.setText("0");
       }
       else {
           holder.expenseEdt.setText(String.valueOf(jkModels.get(position).getAmt()));
           holder.incomeEdt.setText("0");

       }

        holder.payeeEdt.setText(String.valueOf(jkModels.get(position).getPayee()));
        holder.headEdt.setText(String.valueOf(jkModels.get(position).getHead()));
        holder.detailsEdt.setText(String.valueOf(jkModels.get(position).getDetails()));


    }



    @Override
    public int getItemCount() {
        return jkModels.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView srEdt,dateEdt,incomeEdt,expenseEdt,amountEdt,payeeEdt,headEdt,detailsEdt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            srEdt = itemView.findViewById(R.id.dpJkSrno);
            dateEdt = itemView.findViewById(R.id.dpJkDate);
           incomeEdt = itemView.findViewById(R
                   .id.dpJkIncome);
           expenseEdt = itemView.findViewById(R
                   .id.dpJkExpense);

            payeeEdt = itemView.findViewById(R.id.dpjkPayee);
            headEdt = itemView.findViewById(R.id.dpJkHead);
            detailsEdt = itemView.findViewById(R.id.dpJkDetails);

        }
    }
}
