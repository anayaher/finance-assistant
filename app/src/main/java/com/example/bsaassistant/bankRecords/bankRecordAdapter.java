package com.example.bsaassistant.bankRecords;

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

public class bankRecordAdapter extends RecyclerView.Adapter<bankRecordAdapter.ViewHolder> {
    Context context;
    ArrayList<String> bankNameArr,ifcsArr,closedOnArr,workTimeArr,accNoArr;
    ArrayList<Integer>cusNoArr;




    bankRecordAdapter(Context context,ArrayList<String>bankName,ArrayList<String>ifcsArr,ArrayList<String>closedOnArr,ArrayList<String>workTimeArr,ArrayList<String>accNoArr,ArrayList<Integer>cusNoArr){
        this.cusNoArr = cusNoArr;
        this.ifcsArr = ifcsArr;
        this.accNoArr = accNoArr;
        this.closedOnArr = closedOnArr;
        this.workTimeArr = workTimeArr;
        this.bankNameArr = bankName;
        this.context = context;
    }
    @NonNull
    @Override
    public bankRecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.bank_record_disp,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull bankRecordAdapter.ViewHolder holder, int position) {
        holder.bankNameTv.setText(String.valueOf(bankNameArr.get(position)));
        bankRecordDbHelper bankRecordDbHelper = new bankRecordDbHelper(context);
        String cBankName = String.valueOf(bankNameArr.get(position));
        String cAccNo = accNoArr.get(position);
        Integer cCusNo = cusNoArr.get(position);
        String cIfscNo = ifcsArr.get(position);
        String cWorkTime = workTimeArr.get(position);
        String cClosedOn = closedOnArr.get(position);






                holder.bankll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder =new AlertDialog.Builder(context)
                                .setTitle(cBankName)
                                .setMessage("What Would You Like to Do ? ")
                                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                      bankRecordDbHelper bankRecordDbHelper1 = new bankRecordDbHelper(context);
                                        bankRecordDbHelper1.DeleteBank(cBankName);
                                        bankNameArr.remove(holder.getAdapterPosition());
                                        accNoArr.remove(holder.getAdapterPosition());
                                        ifcsArr.remove(holder.getAdapterPosition());
                                        closedOnArr.remove(holder.getAdapterPosition());
                                        workTimeArr.remove(holder.getAdapterPosition());
                                        cusNoArr.remove(holder.getAdapterPosition());
                                        notifyItemRemoved(holder.getAdapterPosition());

                                    }
                                })
                                .setPositiveButton("View ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(context,BankAccountRecords.class);
                                        intent.putExtra("passBankName", cBankName);
                                        intent.putExtra("passAccNo", cAccNo);
                                        intent.putExtra("passCusNo", cCusNo);
                                        intent.putExtra("passWorkTime", cWorkTime);
                                        intent.putExtra("passClosedOn", cClosedOn);
                                        intent.putExtra("passIfscNo",cIfscNo);
                                        context.startActivity(intent);

                                    }
                                });
                        builder.show();

                    }
                });














    }

    @Override
    public int getItemCount() {
        return bankNameArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bankNameTv;
        LinearLayout bankll;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bankNameTv = itemView.findViewById(R.id.dispBankNameTv);
            bankll = itemView.findViewById(R.id.BankMainlinearLayout);

        }
    }
}
