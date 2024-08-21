package com.example.bsaassistant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;
import com.example.bsaassistant.datamodels.salaryModel;

import java.util.ArrayList;

public class salSlipAdapter extends RecyclerView.Adapter<salSlipAdapter.ViewHolder> {

    Context context;
    ArrayList<salaryModel> salarymodels;


    public salSlipAdapter(Context context, ArrayList<salaryModel> salarymodels){
        this.context = context;
        this.salarymodels =salarymodels;

    }
    @NonNull
    @Override
    public salSlipAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.display_salary_report,parent,false);
        return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull salSlipAdapter.ViewHolder holder, int position) {

        holder.month.setText(salarymodels.get(position).getMonth());
        holder.basic.setText(String.valueOf(salarymodels.get(position).getBasic()));
        holder.hra.setText(String.valueOf(salarymodels.get(position).getBasic()*salarymodels.get(position).getHra()/100));
        holder.da.setText(String.valueOf(salarymodels.get(position).getBasic()*salarymodels.get(position).getDa()/100));
        holder.ta.setText(String.valueOf(salarymodels.get(position).getTa()));
        holder.Other.setText(String.valueOf(salarymodels.get(position).getOther()));
        holder.totalSalhead.setText(String.valueOf(salarymodels.get(position).getSalHeadTotal()));
        holder.it.setText(String.valueOf(salarymodels.get(position).getIt()));
        holder.pt.setText(String.valueOf(salarymodels.get(position).getPt()));
        holder.gpf.setText(String.valueOf(salarymodels.get(position).getGpf()));
        holder.janseva.setText(String.valueOf(salarymodels.get(position).getJanse()));
        holder.vidyasevak.setText(String.valueOf(salarymodels.get(position).getVidya()));
        holder.other1.setText(String.valueOf(salarymodels.get(position).getDedOther()));
        holder.totalDedHead.setText(String.valueOf(salarymodels.get(position).getDedHeadTotal()));
        holder.payInHand.setText(String.valueOf(salarymodels.get(position).getPayInHand()));












    }

    @Override
    public int getItemCount() {
        return salarymodels.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView month,other1,other2,basic,hra,da,ta,totalSalhead,Other,it,pt,gpf,tdc,janseva,shivkrupa,abhyudaya,vidyasevak,totalDedHead,payInHand;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.dpPaysheetMonth);
            basic = itemView.findViewById(R.id.dpPaysheetBasic);
            hra = itemView.findViewById(R.id.dpPaysheetHra);
            da = itemView.findViewById(R.id.dpPaysheetDa);
            ta = itemView.findViewById(R.id.dpPaysheetTa);
            Other = itemView.findViewById(R.id.dpPaysheetOther);
            other1 = itemView.findViewById(R.id.dpPaysheetOther1);


            totalSalhead  = itemView.findViewById(R.id.dpPaysheetTotalSal);
            it = itemView.findViewById(R.id.dpPaysheetIt);
            pt = itemView.findViewById(R.id.dpPaysheetPt);
            gpf  =itemView.findViewById(R.id.dpPaysheetGpf);

            janseva = itemView.findViewById(R.id.dpPaysheetJanseva);

            vidyasevak =  itemView.findViewById(R.id.dpPaysheetVidyasevak);
            totalDedHead= itemView.findViewById(R.id.dpPaysheetTotalDed);
            payInHand =  itemView.findViewById(R.id.dpPaysheetPayInHand);



        }
    }
}

