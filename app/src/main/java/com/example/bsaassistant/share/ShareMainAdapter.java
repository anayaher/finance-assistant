package com.example.bsaassistant.share;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.tech.NfcA;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;
import com.example.bsaassistant.adapters.JkAdapter;

import java.util.ArrayList;

public class ShareMainAdapter extends RecyclerView.Adapter<ShareMainAdapter.ViewHolder> {
    Context context;
    ArrayList<shareDataModel> shareDataModels;
    private onShareDeleteListener onShareDeleteListener;
    public interface onShareDeleteListener {
        void onItemDeleted();
    }

    public ShareMainAdapter(Context context , ArrayList<shareDataModel>  shareDataModels){
        this.shareDataModels = shareDataModels;
        this.context = context;
    }
    @NonNull
    @Override
    public ShareMainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(context).inflate(R.layout.disp_share_main,parent,false);
      return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShareMainAdapter.ViewHolder holder, int position) {
       shareDataModel  shareDataModel = shareDataModels.get(position);
       String name = shareDataModel.getPatPedhiName();
       ShareTransactionDbHelper shareTransactionDbHelper = new ShareTransactionDbHelper(context);
       int totalAmt = shareTransactionDbHelper.getShareTotal(name);

       holder.name.setText(shareDataModel.getPatPedhiName());
       holder.amt.setText(String.valueOf(totalAmt));
       holder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setTitle("Delete");
               builder.setMessage("Do You Wish To Delete " + name  + " ? ");
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       ShareDbHelper shareDbHelper = new ShareDbHelper(context);
                       shareDbHelper.deleteShare(name);
                       ShareTransactionDbHelper shareTransactionDbHelper = new ShareTransactionDbHelper(context);
                       shareTransactionDbHelper.deleteShare(name);
                       onShareDeleteListener.onItemDeleted();


                   }
               });
               builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.dismiss();

                   }
               });
               builder.show();
           }
       });
       holder.share.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(context, ShareTransactionActivity.class);
               intent.putExtra("passShareName",name);
               context.startActivity(intent);
           }
       });
    }
    public void setOnShareDeleteListener (onShareDeleteListener listener){
        this.onShareDeleteListener = listener;
    }

    @Override
    public int getItemCount() {
        return shareDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amt,name;
        ImageView delete;
        CardView share;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            amt = itemView.findViewById(R.id.dispShareMainAmt);
            name = itemView.findViewById(R.id.dispShareMainName);
            delete = itemView.findViewById(R.id.dispShareMainDel);

            share = itemView.findViewById(R.id.dispShareMainCard);

        }
    }
}
