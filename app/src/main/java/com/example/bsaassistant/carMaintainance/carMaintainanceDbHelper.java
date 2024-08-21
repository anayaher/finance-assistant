package com.example.bsaassistant.carMaintainance;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class carMaintainanceDbHelper extends SQLiteOpenHelper {
    private  static  final String DB_NAME = "carMaintainanceDB";
    private static final Integer DB_VER = 2;
    private static final String COL_ID = "id";
    private static final String TABLE_NAME = "maintainanceTable";
    private static final String COL_TYPE = "maintainanceType";
    private static final String COL_DATE = "date";
    private static final String COL_COST = "cost";
    private static final String COL_WORKSHOP_NAME = "workshop";
    private static final String COL_DETAILS = "details";

    public carMaintainanceDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE  " + TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TYPE + " TEXT, " + COL_COST + " INTEGER, " + COL_DATE + " TEXT, "  + COL_WORKSHOP_NAME + " TEXT, " + COL_DETAILS + " TEXT " + " ) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void addMaintainance(String type,String date,Integer cost,String WorkshopName,String details){
    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COL_TYPE, type);
        contentValues.put(COL_DATE , date);
        contentValues.put(COL_COST, cost);
        contentValues.put(COL_WORKSHOP_NAME, WorkshopName);
        contentValues.put(COL_DETAILS, details);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }
    public Cursor fetchMaintainance(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);
        return data;
    }
    public void deleteOne(int ids){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(" delete from " + TABLE_NAME + " where id" + " = " + ids );
    }

    public static class carMaintainanceAdpater extends RecyclerView.Adapter<carMaintainanceAdpater.ViewHolder> {
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
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View view = LayoutInflater.from(context).inflate(R.layout.carmaitainance_disp, null);
           return new ViewHolder(view);


        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
}
