package com.example.bsaassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsaassistant.adapters.JkAdapter;
import com.example.bsaassistant.databases.JamaKharachDbHelper;
import com.example.bsaassistant.databases.PayeeDbHelper;
import com.example.bsaassistant.databases.salaryDatabases.HeadDbHelper;
import com.example.bsaassistant.datamodels.jkModel;
import com.example.bsaassistant.dialogues.JkDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JamaKharchaActivity extends AppCompatActivity implements JkAdapter.OnItemDeleteListener {
    Button add;
    RecyclerView recyclerView ;
    TextView balTv;
    ArrayAdapter<String> payeeAdapter,headAdapter;
    ArrayList<jkModel> jkModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jama_kharcha);
        add = findViewById(R.id.jkAddBtn);
        balTv = findViewById(R.id.jkBalTv)
                ;
        recyclerView = findViewById(R.id.jamaKharchaRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(JamaKharchaActivity.this));
        jkModels = new ArrayList<>();


        setView();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adapters
                payeeAdapter = new ArrayAdapter<>(JamaKharchaActivity.this, android.R.layout.simple_list_item_1);
                headAdapter = new ArrayAdapter<>(JamaKharchaActivity.this, android.R.layout.simple_list_item_1);


                //databases
                PayeeDbHelper payeeDbHelper = new PayeeDbHelper(JamaKharchaActivity.this);
                HeadDbHelper headDbHelper= new HeadDbHelper(JamaKharchaActivity.this);
                JamaKharachDbHelper jamaKharachDbHelper = new JamaKharachDbHelper(JamaKharchaActivity.this);


                //Cursors
                Cursor pData = payeeDbHelper.getPayee();
                Cursor hData = headDbHelper.getHeads();
                Cursor jData = jamaKharachDbHelper.getAllJk();
                int sr = 0;
                int bal = 0;



                //SET SR FROM DATABASES

                //sr no from jkDb
                if (jData.getCount() == 0){
                    sr = 1;


                }else {
                    //set last + 1 as sr No
                    if (jData.moveToLast()) {

                            sr = jData.getInt(0) + 1;
                            bal = jData.getInt(4);


                    }
                }

                //SET PAYEE FROM PAYEE DB

                //payee list on payeeEdt from db
                if (pData.getCount() == 0){

                }else {
                    List<String> payeeList = new ArrayList<>();

                    // Collect all the strings from the cursor
                    while (pData.moveToNext()) {
                        payeeList.add(pData.getString(0));
                    }

                    // Sort the list alphabetically
                    Collections.sort(payeeList);

                    // Add the sorted strings to the adapter
                    for (String payee : payeeList) {
                        payeeAdapter.add(payee);
                    }
                }

                //SET HEAD FROM HEAD DB


                //head list on headDb from Db
                if (hData.getCount() == 0)
                {

                }
                else{
                    List<String> headList = new ArrayList<>();

                    // Collect all the strings from the cursor
                    while (hData.moveToNext()) {
                        headList.add(hData.getString(0));
                    }

                    // Sort the list alphabetically
                    Collections.sort(headList);

                    // Add the sorted strings to the adapter
                    for (String heads : headList) {
                        headAdapter.add(heads);
                    }

                }
                jData.close();
                hData.close();
                pData.close();

                JkDialog jkDialog = new JkDialog(JamaKharchaActivity.this,payeeAdapter,headAdapter,sr,bal);
                jkDialog.show();
                jkDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        JamaKharachDbHelper jamaKharachDbHelper = new JamaKharachDbHelper(JamaKharchaActivity.this
                        );
                       jkModels.clear();
                       setView();

                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }public void deleteTransaction(int id){
        int type = 0;
        int amount = 0;


        JamaKharachDbHelper kharachDbHelper = new JamaKharachDbHelper(JamaKharchaActivity.this);

        //GET TYPE AND AMOUNT BEFORE DELETING
        type = kharachDbHelper.getType(id);
        amount = kharachDbHelper.getAmt(id);

        //DELETING THE TRANSACTION'
        kharachDbHelper.deleteTransaction(id);

        //UPDATE SUCCESSOR TRANSACTIONS

        kharachDbHelper.updateSuccessorTransactions(id,type,amount);

    }

    private void setView() {

        jkModels.clear();
        JamaKharachDbHelper jamaKharachDbHelper = new JamaKharachDbHelper(this);
       int bal  = jamaKharachDbHelper.getCurrentBal();
       balTv.setText(String.valueOf(bal)+"/-");

        jkModels = jamaKharachDbHelper.getJkModel();
        JkAdapter jkAdapter = new JkAdapter(this,jkModels);
        jkAdapter.notifyDataSetChanged();
        jkAdapter.setOnItemDeleteListener(this);
        recyclerView.setAdapter(jkAdapter);
        recyclerView.scrollToPosition(jkAdapter.getItemCount()-1);


    }

    @Override
    public void onItemDeleted(int position) {
        deleteTransaction(position);
        setView();

    }
}