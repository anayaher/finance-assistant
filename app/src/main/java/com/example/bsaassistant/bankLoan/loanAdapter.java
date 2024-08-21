package com.example.bsaassistant.bankLoan;

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

public class loanAdapter extends RecyclerView.Adapter<loanAdapter.ViewHolder> {
    Context context;
    ArrayList<String>bankNameArr,dateArr,accNoArr;
    ArrayList<Integer>srNoArr,amountArr,tenureArr;
    ArrayList<Float>roiArr;
    ArrayList<Integer>emiArr;
    loanAdapter(Context context,ArrayList<Integer>emiArr,ArrayList<String>bankNameArr,ArrayList<String>dateArr,ArrayList<String>accNoArr,ArrayList<Integer>srNoArr,ArrayList<Integer>amountArr,ArrayList<Integer>tenureArr,ArrayList<Float>roiArr){


        this.context = context;
    this.bankNameArr = bankNameArr;
    this.dateArr = dateArr;
    this.accNoArr = accNoArr;
    this.srNoArr = srNoArr;
    this.amountArr = amountArr;
    this.tenureArr = tenureArr;
    this.roiArr = roiArr;
    this.emiArr = emiArr;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.loan_disp,parent, false);
       return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(bankNameArr.get(position));
        holder.amount.setText(String.valueOf(amountArr.get(position)));
        String Cname = bankNameArr.get(position);
        Integer srNo = srNoArr.get(position);
        Integer emi = emiArr.get(position);

        String cAccno = accNoArr.get(position);
        String cDate = dateArr.get(position);
        Integer cAmt = amountArr.get(position);
        Integer cTenure = tenureArr.get(position);
        Float cRoi = roiArr.get(position);
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete ? " +Cname)
                        .setMessage("Do You Want To delete Records for This Bank")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                loanDbHelper loanDbHelper = new loanDbHelper(context);
                                emiDbHelper emiDbHelper = new emiDbHelper(context);
                                emiDbHelper.deleteEmi(Cname);
                                loanDbHelper.deleteLoan(srNo);
                                srNoArr.remove(holder.getAdapterPosition());
                                bankNameArr.remove(holder.getAdapterPosition());
                                dateArr.remove(holder.getAdapterPosition());
                                amountArr.remove(holder.getAdapterPosition());
                                roiArr.remove(holder.getAdapterPosition());
                                tenureArr.remove(holder.getAdapterPosition());
                                accNoArr.remove(holder.getAdapterPosition());
                                emiArr.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();

                            }
                        }).setNegativeButton("No", null);
                builder.show();

            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(Cname)
                        .setMessage("What do you wish to do?")
                        .setPositiveButton("View", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context,LoanRecords.class);
                                intent.putExtra("passLoanName", Cname);
                                intent.putExtra("passLoanAmount",cAmt);
                                intent.putExtra("passLoanDate", cDate);
                                intent.putExtra("passLoanRoi", cRoi);
                                intent.putExtra("passLoanTenure", cTenure);
                                intent.putExtra("passLoanAccNo", cAccno);
                                intent.putExtra("passLoanEmi", emi);


                                intent.putExtra("passSaveLoan", "BACK");
                                intent.putExtra("passMainDetailLoan", "Details");

                                context.startActivity(intent);

                            }
                        }).setNeutralButton("View Emi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent = new Intent(context,LoanEmiRecords.class);
                                intent.putExtra("passEmiName",Cname);


                                context.startActivity(intent);


                            }
                        });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return srNoArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,amount;
        LinearLayout linearLayout;
        ImageView deleteImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dispGoldName);
            linearLayout = itemView.findViewById(R.id.dispGoldLl);
            amount = itemView.findViewById(R.id.dispGoldAmount);
            deleteImage = itemView.findViewById(R.id.loanDelImage);

        }
    }
}
