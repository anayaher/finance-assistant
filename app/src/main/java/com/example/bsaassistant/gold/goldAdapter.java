package com.example.bsaassistant.gold;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class goldAdapter extends RecyclerView.Adapter<goldAdapter.ViewHolder> {
    Context context;
    ArrayList<Integer>srnoArr,wigArr,priceArr,rateArr,makeChargeArr;
    ArrayList<String>nameArr,dateArr,jewelerArr;

    public goldAdapter(Context context,
                       ArrayList<Integer> srnoArr, ArrayList<Integer> wigArr, ArrayList<Integer> priceArr, ArrayList<Integer> rateArr, ArrayList<Integer> makeChargeArr,
                       ArrayList<String> nameArr, ArrayList<String> dateArr, ArrayList<String> jewelerArr){
        this.context = context;
        this.dateArr = dateArr;
        this.srnoArr = srnoArr;
        this.wigArr = wigArr;
        this.priceArr = priceArr;
        this.rateArr = rateArr;
        this.makeChargeArr = makeChargeArr;
        this.nameArr = nameArr;
        this.jewelerArr = jewelerArr;


    }
    public goldAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.disp_gold,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull goldAdapter.ViewHolder holder, int position) {
        holder.name.setText(nameArr.get(position));
        String cName = nameArr.get(position);
        Integer cAmt = priceArr.get(position);
        Integer cRate = rateArr.get(position);
        Integer cWig = wigArr.get(position);
        Integer cMakeCharge = makeChargeArr.get(position);
        Integer srNo = srnoArr.get(position);
        String cJewel = jewelerArr.get(position);
        String cDate = dateArr.get(position);

        holder.amount.setText(String.valueOf(priceArr.get(position)));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(cName)
                        .setMessage("What do you wish to do?")
                        .setPositiveButton("View", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context, GoldRecord.class);
                                intent.putExtra("passGoldName", cName);
                                intent.putExtra("passGoldJewel",cJewel);
                                intent.putExtra("passGoldDate", cDate);
                                intent.putExtra("passGoldRate", cRate);
                                intent.putExtra("passGoldMakeCharge", cMakeCharge);
                                intent.putExtra("passGoldPrice", cAmt);
                                intent.putExtra("passGoldWig",cWig);
                                intent.putExtra("passSave", "EDIT");
                                intent.putExtra("passMainDetail", "Details");
                                intent.putExtra("passId", srNo);


                                context.startActivity(intent);

                            }
                        }).setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                goldDbHelper goldDbHelper = new goldDbHelper(context);
                                goldDbHelper.deleteGold(srNo);
                                srnoArr.remove(holder.getAdapterPosition());
                                nameArr.remove(holder.getAdapterPosition());
                                dateArr.remove(holder.getAdapterPosition());
                                priceArr.remove(holder.getAdapterPosition());
                                rateArr.remove(holder.getAdapterPosition());
                                wigArr.remove(holder.getAdapterPosition());
                                makeChargeArr.remove(holder.getAdapterPosition());
                                jewelerArr.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();

                            }
                        });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return srnoArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,amount;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {


            super(itemView);
            name = itemView.findViewById(R.id.dispGoldName);
            linearLayout = itemView.findViewById(R.id.dispGoldLl);
            amount = itemView.findViewById(R.id.dispGoldAmount);
        }
    }
}