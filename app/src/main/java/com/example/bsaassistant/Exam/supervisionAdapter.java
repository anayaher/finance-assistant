package com.example.bsaassistant.Exam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class supervisionAdapter extends RecyclerView.Adapter<supervisionAdapter.ViewHolder> {
    Context context;

    ArrayList<String> dateArr;
    ArrayList<Integer> rateArr;
    ArrayList<Integer> nosArr;
    ArrayList<Integer>sr;
    supervisionAdapter(Context context,ArrayList<Integer>sr,ArrayList<String>dateArr,ArrayList<Integer>nosArr,ArrayList<Integer>rateArr){
        this.context = context;
        this.sr =sr;

        this.rateArr = rateArr;
        this.dateArr = dateArr;
        this.nosArr = nosArr;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.supervision_disp,parent,false);

       ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.dispNos.setText(String.valueOf(nosArr.get(position)));
        holder.dispRate.setText(String.valueOf(rateArr.get(position)));
        holder.dispDate.setText(dateArr.get(position));

    }

    @Override
    public int getItemCount() {
        return sr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dispDate,dispSubj,dispRate,dispNos;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dispDate = itemView.findViewById(R.id.sDispDate);
            dispRate = itemView.findViewById(R.id.sDispRate);
            dispNos = itemView.findViewById(R.id.sDispNos);
        }
    }
}
