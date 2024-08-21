package com.example.bsaassistant.Academics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class academicAdapter  extends RecyclerView.Adapter<academicAdapter.ViewHolder> {

    Context context;
    ArrayList<Integer>srno;
    ArrayList<String>topArr,venueArr,dateFromArr,dateToArr,themeArr,detailsArr;
    academicAdapter(Context context,ArrayList<Integer>srnoArr,ArrayList<String>topArr,ArrayList<String>venueArr,
                    ArrayList<String>dateFromArr,ArrayList<String>dateToArr,ArrayList<String>themeArr,ArrayList<String>
                    detailsArr){
        this.context = context;
        this.dateFromArr = dateFromArr;
        this.dateToArr = dateToArr;
        this.themeArr = themeArr;
        this.venueArr = venueArr;
         this.topArr = topArr;
         this.srno = srnoArr;
         this.detailsArr = detailsArr;
    }
    @NonNull
    @Override
    public academicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.academic_disp,null);

       return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull academicAdapter.ViewHolder holder, int position) {
        holder.topName.setText(topArr.get(position));
       int id = srno.get(position);
        academicDbHelper academicDbHelper = new academicDbHelper(context);
        String topNameS = topArr.get(position);
        String cvenue = venueArr.get(position);
        String cDetail = detailsArr.get(position);
        String CdateFrom = dateFromArr.get(position);
        String cdateTo = dateToArr.get(position);
        String cTheme =  themeArr.get(position);

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle(topNameS)
                            .setMessage("What Would You Like To Do ?")
                            .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    academicDbHelper.deleteOne(id);
                                    srno.remove(holder.getAdapterPosition());
                                    topArr.remove(holder.getAdapterPosition());
                                    venueArr.remove(holder.getAdapterPosition());
                                    dateFromArr.remove(holder.getAdapterPosition());
                                    dateToArr.remove(holder.getAdapterPosition());
                                    themeArr.remove(holder.getAdapterPosition());
                                    detailsArr.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());

                                }
                            })
                            .setPositiveButton("View", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(context,AcademicRecords.class);
                                    intent.putExtra("passTop", topNameS);
                                    intent.putExtra("passVenue", cvenue);
                                    intent.putExtra("passDetail", cDetail);
                                    intent.putExtra("passDateFrom", CdateFrom);
                                    intent.putExtra("passDateTo", cdateTo);
                                    intent.putExtra("passTheme",cTheme);
                                    context.startActivity(intent);
                                }
                            });
                    builder.show();
                }
            });


    }

    @Override
    public int getItemCount() {
        return srno.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView topName;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {


            super(itemView);
            topName = itemView.findViewById(R.id.dispAcademicNameTv);
            linearLayout = itemView.findViewById(R.id.AcademicMainlinearLayoutTop);

        }
    }
}
