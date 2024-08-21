package com.example.bsaassistant.carMaintainance;

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

public class carMaintainanceAdpater extends RecyclerView.Adapter<carMaintainanceAdpater.ViewHolder> {
    Context context;
    ArrayList<Integer> srnoArr,costArr;
    ArrayList<String>typeArr,dateArr,workArr,detailArr;
    carMaintainanceAdpater(Context context,ArrayList<Integer>srnoArr,ArrayList<Integer>costArr,ArrayList<String>typeArr,
                           ArrayList<String>dateArr,ArrayList<String>workArr,ArrayList<String>detailArr){
        this.context =  context;
        this.costArr = costArr;
        this.dateArr = dateArr;
        this.srnoArr = srnoArr;
        this.typeArr = typeArr;
        this.workArr = workArr;
        this.detailArr = detailArr;

    }
    @NonNull
    @Override
    public carMaintainanceAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.carmaitainance_disp, null);
       return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull carMaintainanceAdpater.ViewHolder holder, int position) {
        holder.name.setText(typeArr.get(position));
        holder.cost.setText(String.valueOf(costArr.get(position)));
        holder.date.setText(dateArr.get(position));
        carMaintainanceDbHelper carMaintainanceDbHelper = new carMaintainanceDbHelper(context);
        int id = srnoArr.get(position);
        String cName = typeArr.get(position);
        String cDate = dateArr.get(position);
        String cWork = workArr.get(position);
        int cCost = costArr.get(position);
        String cDetail = detailArr.get(position);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(cName)
                        .setMessage("What you want ?")
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                carMaintainanceDbHelper.deleteOne(id);
                                srnoArr.remove(holder.getAdapterPosition());
                                typeArr.remove(holder.getAdapterPosition());
                                costArr.remove(holder.getAdapterPosition());
                                dateArr.remove(holder.getAdapterPosition());
                                workArr.remove(holder.getAdapterPosition());
                                detailArr.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        }).setPositiveButton("View Record", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context,CarMaintainaneRecords.class);
                                intent.putExtra("passType", cName);
                                intent.putExtra("passDateCar", cDate);
                                intent.putExtra("passWork", cWork);
                                intent.putExtra("passCost", cCost);
                                intent.putExtra("passDetailCar", cDetail);
                                context.startActivity(intent);


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
        TextView cost,date,name;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cost = itemView.findViewById(R.id.dispMaintainanceCost);
            date = itemView.findViewById(R.id.dispMaintainanceDate);
            name = itemView.findViewById(R.id.dispMaintainceName);
            linearLayout = itemView.findViewById(R.id.dispMaintainanceLl);

        }
    }
}
