package com.example.bsaassistant.document;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bsaassistant.R;
import com.ortiz.touchview.TouchImageView;

import java.util.ArrayList;

public class documentAdapter extends BaseAdapter {
    private final ArrayList<String>namesArr;
    private final ArrayList<byte[]>imageArr;
    Context context;

    public documentAdapter(Context context,ArrayList<String> namesArr,ArrayList<byte[]>imageArr) {
        this.context = context;
        this.imageArr = imageArr;
        this.namesArr = namesArr;
    }

    @Override
    public int getCount() {
        return namesArr.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public void removeItem(int position){
        imageArr.remove(position);
        namesArr.remove(position);
        notifyDataSetChanged();

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.document_disp, null);
        TouchImageView img = (TouchImageView) view1.findViewById(R.id.iconImage);
        TextView textView = (TextView) view1.findViewById(R.id.textData);
        LinearLayout linearLayout = (LinearLayout) view1.findViewById(R.id.gridLl);

        byte[] image = imageArr.get(i);
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(image, 0, image.length);
        img.setImageBitmap(bitmap2);
        textView.setText(namesArr.get(i));
        String namef = namesArr.get(i);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(namef)
                        .setPositiveButton("view", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context,DocumentMain.class);
                                intent.putExtra("passName", namef);
                                context.startActivity(intent);

                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                documentDbHelper documentDbHelper = new documentDbHelper(context);

                                documentDbHelper.deleteOne(namef);
                               imageArr.remove(image);
                               namesArr.remove(namef);
                               notifyDataSetChanged();




                            }
                        });
                builder.show();
            }
        });
        return view1;

    }
}
