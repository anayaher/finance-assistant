package com.example.bsaassistant.document;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.bsaassistant.R;

import java.util.ArrayList;

public class DocumentActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<String> namesArr;
    ArrayList<byte[]>imageArr;
    Button addnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        gridView =findViewById( R.id.DocGridView);
        addnew = findViewById(R.id.addNewDocBtn);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(DocumentActivity.this, DocumentMain.class);
                startActivity(intent);
            }
        });
        namesArr = new ArrayList<>();
        imageArr = new ArrayList<>();
        documentDbHelper documentDbHelper = new documentDbHelper(DocumentActivity.this);
        Cursor data = documentDbHelper.fetchDoc();
        if(data.getCount() ==  0){

        }else {
            byte[] image = new byte[0];
            while (data.moveToNext()) {
                namesArr.add(data.getString(0));
                image = data.getBlob(1);
                imageArr.add(image);


            }

            documentAdapter documentAdapter = new documentAdapter(DocumentActivity.this,namesArr,imageArr);

            gridView.setAdapter(documentAdapter);



    }
}

    @Override
    protected void onResume() {
        super.onResume();
        imageArr.clear();
        namesArr.clear();
        documentDbHelper documentDbHelper = new documentDbHelper(DocumentActivity.this);
        Cursor data = documentDbHelper.fetchDoc();
        if(data.getCount() ==  0){

        }else {
            byte[] image = new byte[0];
            while (data.moveToNext()) {
                namesArr.add(data.getString(0));
                image = data.getBlob(1);
                imageArr.add(image);


            }

            documentAdapter documentAdapter = new documentAdapter(DocumentActivity.this,namesArr,imageArr);
            gridView.setAdapter(documentAdapter);

    }
}
}
