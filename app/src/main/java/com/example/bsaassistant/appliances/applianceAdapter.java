package com.example.bsaassistant.appliances;

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

public class applianceAdapter extends RecyclerView.Adapter<applianceAdapter.ViewHolder> {
    ArrayList<Integer>srnoArr;
    ArrayList<String >itemNameArrm,priceArr,ShopArr,dateBoughtArr,warrantyDurationArr,expiryDateArr;
    Context context;

    applianceAdapter(Context context,ArrayList<Integer>srnoArr, ArrayList<String>itemNameArrm, ArrayList<String >priceArr, ArrayList<String >shopArr,
                     ArrayList<String >dateBoughtArr, ArrayList<String >warrantyDurationArr, ArrayList<String >expiryDateArr)
    {
        this.dateBoughtArr =dateBoughtArr;
        this.expiryDateArr =expiryDateArr;
        this.itemNameArrm = itemNameArrm;
        this.priceArr =priceArr;
        this.srnoArr = srnoArr;
        this.ShopArr =shopArr;
        this.warrantyDurationArr =warrantyDurationArr;
        this.context = context;
    }
    @NonNull
    @Override
    public applianceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.appliance_disp, null);
        return  new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull applianceAdapter.ViewHolder holder, int position) {
        holder.itemName.setText(itemNameArrm.get(position));
        holder.itemPrice.setText(priceArr.get(position));
        int id = srnoArr.get(position);

        String  finalItemName = itemNameArrm.get(position);
        String finalPrice = priceArr.get(position);
        String finalShopName = ShopArr.get(position);
        String finalExpiry = expiryDateArr.get(position);
        String finalWarrantyDuration = warrantyDurationArr.get(position);
        String finalDateBought = dateBoughtArr.get(position);


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(finalItemName)
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                appliancesDbHelper appliancesDbHelper = new appliancesDbHelper(context);
                                appliancesDbHelper.deleteOne(id);
                                srnoArr.remove(holder.getAdapterPosition());
                                itemNameArrm.remove(holder.getAdapterPosition());
                                priceArr.remove(holder.getAdapterPosition());
                                ShopArr.remove(holder.getAdapterPosition());
                                expiryDateArr.remove(holder.getAdapterPosition());
                                warrantyDurationArr.remove(holder.getAdapterPosition());
                                dateBoughtArr.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());

                            }
                        })
                        .setPositiveButton("View", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context,AppliancesRecord.class);
                                intent.putExtra("passItemName",finalItemName);
                                intent.putExtra("passShopName",finalShopName);
                                intent.putExtra("passExpiryDate", finalExpiry);
                                intent.putExtra("passDateBought", finalDateBought);
                                intent.putExtra("passWarrantyDuration", finalWarrantyDuration);
                                intent.putExtra("passPrice", finalPrice);
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
        TextView itemName,itemPrice;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.dispApplianceNameTv);
            itemPrice = itemView.findViewById(R.id.dispAppliancePriceTv);
            linearLayout = itemView.findViewById(R.id.ApplianceMainlinearLayoutTop);
        }
    }
}
