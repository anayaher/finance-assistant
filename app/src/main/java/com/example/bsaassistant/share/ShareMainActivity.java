package com.example.bsaassistant.share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class ShareMainActivity extends AppCompatActivity  implements  ShareMainAdapter.onShareDeleteListener{
    RecyclerView recyclerView;
    ArrayList<shareDataModel> shareDataModels;

    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_main);
        add  = findViewById(R.id.shareMainAdd);
        shareDataModels = new ArrayList<>();
        recyclerView = findViewById(R.id.shareMainRv);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setView();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddShareDialog addShareDialog = new AddShareDialog(ShareMainActivity.this);
                addShareDialog.show();
                addShareDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        setView();

                    }
                });
            }
        });
    }

    private void setView() {
        shareDataModels.clear();
        ShareDbHelper shareDbHelper = new ShareDbHelper(this);
        shareDataModels = shareDbHelper.getShares();
        ShareMainAdapter shareMainAdapter  = new ShareMainAdapter(ShareMainActivity.this,shareDataModels);
        shareMainAdapter.setOnShareDeleteListener(this);
        recyclerView.setAdapter(shareMainAdapter);


    }

    @Override
    public void onItemDeleted() {
        setView();

    }
}