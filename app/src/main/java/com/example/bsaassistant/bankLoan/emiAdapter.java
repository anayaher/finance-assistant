package com.example.bsaassistant.bankLoan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class emiAdapter extends RecyclerView.Adapter<emiAdapter.ViewHolder> {
    ArrayList<String> dateArr,bankNameArr;
    ArrayList<Integer>srNoArr,remainArr;
    Double roi;

    Context context;
    Integer premium;
    Integer loanAmt;


    public emiAdapter(Context context, Double roi, ArrayList<String> bankNameArr, ArrayList<String> dateArr, ArrayList<Integer> srNoArr, Integer premium, Integer loanAmt){
        this.bankNameArr = bankNameArr;
        this.dateArr = dateArr;
        this.srNoArr = srNoArr;
        this.premium = premium;
        this.loanAmt = loanAmt;

        this.context = context;
        this.roi = roi;

    }
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.disp_emi,parent, false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Integer Cpremium = premium;
        ArrayList<Integer> intrest;
        ArrayList<Integer> principle;
        ArrayList<Integer> balanceArr;

        ArrayList<Integer>newSr;
        newSr = new ArrayList<>();
        principle = new ArrayList<>();
        balanceArr = new ArrayList<>();
        intrest = new ArrayList<>();


        Double Croi =  roi;
        Double intrestPercentage =(100/roi) ;
        Double MainIntrest = (loanAmt/intrestPercentage)/12;
        int newl =(int) Math.round(MainIntrest);
        int princippleFirst = premium-newl;
        int bal = loanAmt-princippleFirst;
        intrest.add(newl);
        principle.add(princippleFirst);
        balanceArr.add(bal);

        for (int i =1;i<srNoArr.size()+1;i++){
            newSr.add(i);

        }




        holder.sr.setText(String.valueOf(newSr.get(position)));
        holder.premium.setText(String.valueOf(premium));



        for (int i =0; i<srNoArr.size();i++){
            int currenTbal = (int) balanceArr.get(0+i);
            int currentPrinciple = principle.get(0+i);
            int currentBal = balanceArr.get(0+i);

            Double NewMainIntrest = (currenTbal/intrestPercentage)/12;
            int Mainnewl =(int) Math.round(NewMainIntrest);
            int NewprincippleFirst = premium-Mainnewl;
            int NewBal = currenTbal-NewprincippleFirst;
            intrest.add(Mainnewl);
            principle.add(NewprincippleFirst);
            balanceArr.add(NewBal);



        }
        holder.intrest.setText(String.valueOf(intrest.get(position)));
        holder.date.setText(dateArr.get(position));

        holder.principle.setText(String.valueOf(principle.get(position)));
        holder.Balance.setText(String.valueOf(balanceArr.get(position)));










    }

    @Override
    public int getItemCount() {
        return srNoArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sr,date,premium,intrest,principle,Balance;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            sr  = itemView.findViewById(R.id.emiDispSr);
            date = itemView.findViewById(R.id.emiDispDate);
            intrest = itemView.findViewById(R.id.emiDispIntrest);
            principle = itemView.findViewById(R.id.emiDispPrinciple);
            Balance = itemView.findViewById(R.id.emiDispBalance);
            premium = itemView.findViewById(R.id.emiDispPremium);



        }
    }
}
