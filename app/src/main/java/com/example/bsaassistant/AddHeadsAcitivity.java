package com.example.bsaassistant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bsaassistant.databases.salaryDatabases.HeadDbHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddHeadsAcitivity extends AppCompatActivity {
    EditText headEdt;
    Button save;
    ListView recyclerView;
    ArrayAdapter<String> headAdapter ;

    List<String>dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_heads_acitivity);
        headEdt = findViewById(R.id.addHeadEditText);
        save = findViewById(R.id.addHeadBtn);
        recyclerView = findViewById(R.id.addHeadLv);

        dataList = new ArrayList<>();

        HeadDbHelper headDbHelper =new HeadDbHelper(AddHeadsAcitivity.this);
        //the finction to set the listView

       setView();

        //save function
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!headEdt.getText().toString().equals("")){
                    headDbHelper.addHead(headEdt.getText().toString());
                    Toast.makeText(AddHeadsAcitivity.this, "Saved " +headEdt.getText().toString() + " Successfully", Toast.LENGTH_SHORT).show();
                    headEdt.setText("");
                    dataList.clear();
                    setView();


                }else{
                    Toast.makeText(AddHeadsAcitivity.this, "Please Enter Head", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //delete by ckcicking on item
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) recyclerView.getItemAtPosition(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddHeadsAcitivity.this);
                builder.setTitle(" DELETE ?")
                        .setMessage("Do You Want To Delete " +   selectedItem)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                headDbHelper.deleteHead(selectedItem);
                                Toast.makeText(AddHeadsAcitivity.this, selectedItem + " Deleted SuccessFully", Toast.LENGTH_SHORT).show();
                                setView();
                                // Optional: Perform any action on positive button click
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // Optional: Perform any action on negative button click
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

                setView();


            }
        });

    }
    private void scrollToLastItem(ArrayAdapter<String> adapter) {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(adapter.getCount() - 1);
            }
        });
    }

    private void setView() {
        dataList.clear();
        HeadDbHelper headDbHelper = new HeadDbHelper(AddHeadsAcitivity.this);

        Cursor data= headDbHelper.getHeads();
        if (data.getCount() == 0){
            dataList.clear();

        } else {
            while (data.moveToNext()){
                dataList.add(data.getString(0));


            }

        }
        Collections.sort(dataList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_bsa, dataList);
        scrollToLastItem(adapter);
        recyclerView.setAdapter(adapter);



    }
}