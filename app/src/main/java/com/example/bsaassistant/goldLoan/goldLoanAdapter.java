package com.example.bsaassistant.goldLoan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class goldLoanAdapter extends RecyclerView.Adapter<goldLoanAdapter.ViewHolder> {
    Context context;
    ArrayList<Integer>tenureArr,amountArr;
    ArrayList<String>nameArr,dateArr,MdateArr,itemsArr;
    ArrayList<Double>roiArr;
    goldLoanAdapter(Context context,ArrayList<Integer>tenureArr,ArrayList<Integer>amountArr,ArrayList<String>nameArr,ArrayList<String>dateArr,ArrayList<String>mdateArr,ArrayList<String>itemsArr,ArrayList<Double>roiArr){
        this.amountArr = amountArr;
        this.context = context;
        this.roiArr = roiArr;
        this.nameArr = nameArr;
        this.dateArr = dateArr;
        this.MdateArr = mdateArr;
        this.itemsArr =itemsArr;
        this.tenureArr = tenureArr;

    }
    public goldLoanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.disp_gold_laon, parent,false);
    return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull goldLoanAdapter.ViewHolder holder, int position) {
        String cName = nameArr.get(position);
        Integer cAmt = amountArr.get(position);
        Integer cTenure = tenureArr.get(position);
        Double cRoi = roiArr.get(position);
        String cItem  = itemsArr.get(position);
        String cdate =  dateArr.get(position);
        String cMdate = MdateArr.get(position);


        holder.name.setText(nameArr.get(position));
        holder.amount.setText(String.valueOf(amountArr.get(position)));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete " +cName + " ?")
                        .setMessage("Are You Sure? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                goldLoanDbHelper goldLoanDbHelper = new goldLoanDbHelper(context);
                                goldLoanDbHelper.deleteGoldLoan(cName);
                                nameArr.remove(holder.getAdapterPosition());
                                amountArr.remove(holder.getAdapterPosition());
                                tenureArr.remove(holder.getAdapterPosition());
                                roiArr.remove(holder.getAdapterPosition());
                                itemsArr.remove(holder.getAdapterPosition());
                                dateArr.remove(holder.getAdapterPosition());
                                MdateArr.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();

                            }
                        })
                                .setNeutralButton("No", null);
                builder.show();
            }
        });
        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,GoldLoanActivity.class);
                intent.putExtra("passgName", cName);
                intent.putExtra("passgAmt", cAmt);
                intent.putExtra("passgTenure", cTenure);
                intent.putExtra("passgRoi", cRoi);
                intent.putExtra("passgDate",cdate);
                intent.putExtra("passgMdate", cMdate);
                intent.putExtra("passgItems", cItem);
                intent.putExtra("passTv", "Details");
                intent.putExtra("passBtn", "BACK");


                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return nameArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,amount;
        LinearLayout main;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dispGoldLoanName);
            amount = itemView.findViewById(R.id.dispGoldLoanAmount);
            delete = itemView.findViewById(R.id.GoldloanDelImage);
            main  = itemView.findViewById(R.id.dispGoldLoanLl);



        }
    }
}
