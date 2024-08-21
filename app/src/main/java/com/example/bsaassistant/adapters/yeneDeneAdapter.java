package com.example.bsaassistant.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;
import com.example.bsaassistant.databases.yenedeneDBhelper;

import java.util.ArrayList;

public class yeneDeneAdapter  extends RecyclerView.Adapter<yeneDeneAdapter.ViewHolder> {

    Context context;
    ArrayList<String> YDdatearr;
    ArrayList<String> YDheadarr;
    ArrayList<Integer> YDinArr;
    ArrayList<Integer> YDoutArr;
    ArrayList<Integer>sr;
    TextView bal,yenetv,denetv;

    public yeneDeneAdapter(Context context, ArrayList<String> YDdatearr, ArrayList<String> YDheadarr, ArrayList<Integer> YDinArr, ArrayList<Integer> YDoutArr, ArrayList<Integer> srno){
        this.context = context;
        this.YDdatearr = YDdatearr;
        this.YDheadarr = YDheadarr;
        this.YDinArr = YDinArr;
        this.YDoutArr = YDoutArr;
        this.sr = srno;
        bal = ((Activity) context).findViewById(R.id.MainBalancetvYD);
        yenetv = ((Activity) context).findViewById(R.id.YDtotalYenetv);
        denetv = ((Activity) context).findViewById(R.id.YDtotalDenetv);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.disp_yene_dene,parent,false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dispDate.setText(YDdatearr.get(position));

        holder.dispYene.setText(String.valueOf(YDinArr.get(position)));
        holder.dispDene.setText(String.valueOf(YDoutArr.get(position)));
        holder.dispHead.setText(YDheadarr.get(position));
        yenedeneDBhelper yenedeneDBhelper = new yenedeneDBhelper(context);
        Cursor data = yenedeneDBhelper.fetchYeneDene();
        if(data.moveToPosition(position)){
            int id = data.getInt(0);
            String name = data.getString(1);
            int yene, dene;
            yene = data.getInt(2);
            dene = data.getInt(3);

            holder.itemView.setTag(id);

            holder.ydLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle("Delete Transaction")
                            .setMessage("Do you really want to delete "  + name + " ?")


                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Integer cyene = Integer.parseInt(yenetv.getText().toString());
                                    Integer cdene = Integer.parseInt(denetv.getText().toString());




                                    yenetv.setText(String.valueOf(cyene - yene));
                                    denetv.setText(String.valueOf(cdene - dene));
                                    yenedeneDBhelper.deleteOne(id);
                                    YDdatearr.remove(holder.getAdapterPosition());
                                    YDinArr.remove(holder.getAdapterPosition());
                                    YDheadarr.remove(holder.getAdapterPosition());
                                    YDoutArr.remove(holder.getAdapterPosition());
                                    Integer cbi = Integer.parseInt( bal.getText().toString());
                                    bal.setText(String.valueOf(cbi+dene-yene));












                                    notifyItemRemoved(holder.getAdapterPosition());




                                }
                            })
                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.show();

                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return YDdatearr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dispDate,dispHead,dispYene,dispDene;
        LinearLayout ydLl;
        TextView balance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dispDate = itemView.findViewById(R.id.YDdispDate);
            ydLl = itemView.findViewById(R.id.yenedeneLinearLayout);

            dispHead = itemView.findViewById(R.id.YDdispHead);
            dispYene = itemView.findViewById(R.id.YDdispYene);
            dispDene = itemView.findViewById(R.id.YDdispDene);
        }
    }
}