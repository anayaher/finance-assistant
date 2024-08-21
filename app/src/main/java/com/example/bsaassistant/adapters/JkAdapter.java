package com.example.bsaassistant.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.JkDetailsActivity;
import com.example.bsaassistant.R;
import com.example.bsaassistant.databases.JamaKharachDbHelper;
import com.example.bsaassistant.datamodels.jkModel;

import java.util.ArrayList;

public class JkAdapter extends RecyclerView.Adapter<JkAdapter.ViewHolder> {
    Context context;

    ArrayList<jkModel> jkModels;
    private OnItemDeleteListener onItemDeleteListener;
    public interface OnItemDeleteListener {
        void onItemDeleted(int position);
    }
    public JkAdapter(Context context,ArrayList<jkModel> jkModels){
        this.context = context;
        this.jkModels = jkModels;
    }

    @NonNull
    @Override
    public JkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.display_jk,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JkAdapter.ViewHolder holder, int position) {

        Color color = null;
        jkModel jkModel = jkModels.get(position);


        String sign = null;
        if (jkModels.get(position).getType() == 1){
            sign = "+";

           holder.amtEdt.setTextColor(ContextCompat.getColor(context,R.color.green));

        }
        else {
            sign = "-";
            holder.amtEdt.setTextColor(Color.RED);
        }

        //get Details for Detailss Activity
        String  sr = String.valueOf(jkModel.getSr());
        String date = jkModel.getDate();
        String amount = String.valueOf(jkModel.getAmt());
        String payee = jkModel.getPayee();
        String head = jkModel.getHead();
        String details = jkModel.getDetails();
        int type = jkModel.getType();


        holder.srEdt.setText(String.valueOf(jkModels.get(position).getSr()));
        holder.dateEdt.setText(jkModels.get(position).getDate());
        holder.amtEdt.setText(String.valueOf(sign + jkModels.get(position).getAmt()));
        holder.payeeEdt.setText(String.valueOf(jkModels.get(position).getPayee()));
        holder.headEdt.setText(jkModels.get(position).getHead());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Warning");
                builder.setMessage("Do You Wish To Delete This Transaction");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int id = jkModels.get(holder.getAdapterPosition()).getSr();

                        if (onItemDeleteListener != null) {
                            onItemDeleteListener.onItemDeleted(id);
                        }



                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                       Intent intent = new Intent(context,JkDetailsActivity.class);
                       intent.putExtra("PassSerial",sr);
                       intent.putExtra("PassDate",date);
                       intent.putExtra("PassAmount",amount);
                       intent.putExtra("PassType",type);
                       intent.putExtra("PassPayee",payee);
                       intent.putExtra("PassHead",head);
                       intent.putExtra("PassDetails",details);


                       context.startActivity(intent);

                       }
                });
                builder.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return jkModels.size();
    }
    public void setOnItemDeleteListener(OnItemDeleteListener listener) {
        this.onItemDeleteListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView srEdt,amtEdt,payeeEdt,headEdt,dateEdt;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            srEdt = itemView.findViewById(R.id.jkDispSr);
            amtEdt = itemView.findViewById(R.id.jkDispAmt);
            cardView = itemView.findViewById(R.id.dpJkCard);
            payeeEdt = itemView.findViewById(R.id.jkDispPayee);
            headEdt = itemView.findViewById(R.id.jkDispHead);
            dateEdt = itemView.findViewById(R.id.jkDispDate);


        }
    }

}
