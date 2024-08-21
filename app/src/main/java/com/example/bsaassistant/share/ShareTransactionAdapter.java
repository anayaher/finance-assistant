package com.example.bsaassistant.share;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class ShareTransactionAdapter extends RecyclerView.Adapter<ShareTransactionAdapter.ViewHolder> {
    ArrayList<shareTransactionModel> shareTransactionModels;

    Context context;

    public ShareTransactionAdapter(Context context,ArrayList<shareTransactionModel>shareTransactionModels){
        this.context = context;
        this.shareTransactionModels = shareTransactionModels;
    }

    @NonNull
    @Override
    public ShareTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.shares_record_disp,parent,false);
       return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ShareTransactionAdapter.ViewHolder holder, int position) {
        shareTransactionModel shareTransactionModel = shareTransactionModels.get(position);
        holder.date.setText(shareTransactionModel.getDate());
        holder.amount.setText(String.valueOf(shareTransactionModel.getAmt()));


    }

    @Override
    public int getItemCount() {
        return shareTransactionModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date,amount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dispSharesDate);
            amount = itemView.findViewById(R.id.dispSharesAmount);

        }
    }
}
