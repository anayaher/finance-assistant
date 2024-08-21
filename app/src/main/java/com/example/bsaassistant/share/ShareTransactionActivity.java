package com.example.bsaassistant.share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class ShareTransactionActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView balTv;
    ArrayList<shareTransactionModel> shareTransactionModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_transaction);
        balTv = findViewById(R.id.ShareTranBal);
        recyclerView =findViewById(R.id.shareTranRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shareTransactionModels= new ArrayList<>();
        String shareName = getIntent().getStringExtra("passShareName");


        setView(shareName);


    }

    private void setView(String name) {
        shareTransactionModels.clear();
        ShareTransactionDbHelper shareTransactionDbHelper = new ShareTransactionDbHelper(this);
        shareTransactionModels = shareTransactionDbHelper.getTransactionsFor(name);
        ShareTransactionAdapter shareTransactionAdapter = new ShareTransactionAdapter(this,shareTransactionModels);
        int bal = 0;

        for (int i = 0;i<shareTransactionModels.size();i++){
          shareTransactionModel shareTransactionModel = shareTransactionModels.get(i);
          bal += shareTransactionModel.getAmt();
        }
        recyclerView.setAdapter(shareTransactionAdapter);
        balTv.setText(String.valueOf(bal));

    }
}