package com.example.bsaassistant.Exam;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class examMainAdapter extends RecyclerView.Adapter<examMainAdapter.ViewHolder>{
    Context context;
    ArrayList<String> examArr;
    ArrayList<Integer>ratesPC,ratesS,ratesPS;
    ArrayList<Integer>nosPC,nosS,nosPS;
    examMainAdapter(Context context,ArrayList<String> examArr){
        this.context = context;
        this.examArr = examArr;

    }

    @NonNull
    @Override
    public examMainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.exam_main_display,parent,false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull examMainAdapter.ViewHolder holder, int position) {

        examMainDbhelper examMainDbhelper = new examMainDbhelper(context);
        Cursor data = examMainDbhelper.fetchState();
        if(data.moveToPosition(position)){
            int state = data.getInt(1);
            if (state == 1) {
                holder.examLl.setBackgroundColor(Color.RED);
            }else {

            }

        }


        holder.ExamName.setText(String.valueOf(examArr.get(position)));
        String examName = String.valueOf(examArr.get(position));
        holder.ExamAmount.setText("0");
        holder.examDeleteLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("DELETE")
                        .setMessage("Are You Sure ?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               examMainDbhelper examMainDbhelper1 = new examMainDbhelper(context);
                               examMainDbhelper1.deleteExam(examName);
                                examArr.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        });
                builder.show();
            }
        });
        holder.examLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(examName)
                        .setMessage("What Would you Like To Do ? ")
                        .setNegativeButton("Paid", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(context)
                                        .setTitle("Warning")
                                        .setMessage("Really?")

                                        .setNegativeButton("No", null)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                holder.examLl.setBackgroundColor(Color.RED);
                                                examMainDbhelper examMainDbhelper = new examMainDbhelper(context);
                                                examMainDbhelper.setPaid(examName);
                                            }
                                        });
                                builder1.show();
                            }
                        })
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context,ExamBill.class);
                                intent.putExtra("passExamName", examName);

                                context.startActivity(intent);

                            }
                        });
                builder.show();

            }
        });
        ratesPC = new ArrayList<>();
        ratesS = new ArrayList<>();
        ratesPS = new ArrayList<>();
        nosPC = new ArrayList<>();
        nosS = new ArrayList<>();
        nosPS = new ArrayList<>();
        paperCheckingDbHelper paperCheckingDbHelper = new paperCheckingDbHelper(context);
        supervisionDbHelper supervisionDbHelper = new supervisionDbHelper(context);
        paperSettingDbHelper paperSettingDbHelper = new paperSettingDbHelper(context);
        ratesDbHelper ratesDbHelper = new ratesDbHelper(context);
        Cursor dataPC = paperCheckingDbHelper.fetchPaperChecking(examName);
        Cursor dataS =supervisionDbHelper.fetchPaperSetting(examName);
        Cursor dataPS =paperSettingDbHelper.fetchPaperSetting(examName);
        Cursor ratesC = ratesDbHelper.fetchRates();
        int mainTotalPaperCheckeds = 0;
        int mainTotalSupervisions = 0;
        int mainTotalPaperSetteds = 0;
        int totalPc = 0;
        int totalS = 0;
        int totalPs= 0;
        int ratesPc = 0;
        int ratesS = 0;
        int ratesPs = 0;

        if(ratesC.getCount()==0){

        }
        else {
            while (ratesC.moveToNext()){
                ratesPc = ratesC.getInt(0);
                ratesS = ratesC.getInt(1);
                ratesPs = ratesC.getInt(2);
            }
        }
        if(dataPC.getCount()==0){

        }else {
            while (dataPC.moveToNext()){
                nosPC.add(dataPC.getInt(5));

            }
            for (int i = 0;i<nosPC.size();i++){
                totalPc += nosPC.get(i);

            }



        }
        ////
        //
        if (dataS.getCount()==0) {


        }
        else {
            while (dataS.moveToNext()){
                nosS.add(dataS.getInt(4));

            }
            for (int i = 0;i<nosS.size();i++){
                totalS += nosS.get(i);

            }



        }
        if (dataPS.getCount() == 0){


        }else {

            while (dataPS.moveToNext()){
                nosPS.add(dataPS.getInt(5));

            }
            for (int i = 0;i<nosPS.size();i++){
                totalPs += nosPS.get(i);

            }




        }
        mainTotalPaperCheckeds = totalPc*ratesPc;
        mainTotalSupervisions = totalS*ratesS;
        mainTotalPaperSetteds = totalPs*ratesPs;
        holder.total.setText(String.valueOf(mainTotalPaperCheckeds+mainTotalSupervisions+mainTotalPaperSetteds));

    }

    private void getExamTotal(String examName) {



    }

    @Override
    public int getItemCount() {
        return examArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ExamName,ExamAmount,total;
        LinearLayout examLl,examDeleteLl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ExamName = itemView.findViewById(R.id.examNameMaintv);
            total = itemView.findViewById(R.id.examAmountMainTv);
            ExamAmount = itemView.findViewById(R.id.examAmountMainTv);
            examLl = itemView.findViewById(R.id.examMainLinearLayout);
            examDeleteLl = itemView.findViewById(R.id.DeleteLl);
            examMainDbhelper examMainDbhelper = new examMainDbhelper(context);
            Cursor data = examMainDbhelper.fetchState();
            Integer one = 1;


            }
        }
    }

