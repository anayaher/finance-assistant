package com.example.bsaassistant.Exam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

import com.example.bsaassistant.R;

import java.util.ArrayList;
import java.util.Collections;

public class SubjectActivity extends AppCompatActivity {
    EditText subText;
    Button save,clr;
    ListView recyclerView;
    ArrayList<String> subJectlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        subText = findViewById(R.id.idSubjectTv);
        subJectlist = new ArrayList<>();
        recyclerView = findViewById(R.id.recylerviewSubject);
        save = findViewById(R.id.saveSubjectBtn);



         SubjectDbhelper subjectDbhelper = new SubjectDbhelper(SubjectActivity.this);
        Cursor data = subjectDbhelper.fetchSubject();
        if(data.getCount() == 0){
            Toast.makeText(this, "No Subjects's available", Toast.LENGTH_SHORT).show();
        }else {
            while (data.moveToNext()){
                subJectlist.add(data.getString(0));

            }

            Collections.sort(subJectlist);





            ArrayAdapter<String> adapter = new ArrayAdapter<>(SubjectActivity.this, R.layout.list_item_bsa,subJectlist);
            recyclerView.setAdapter(adapter);

        } save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subText.getText().toString().equals("")){
                    Toast.makeText(SubjectActivity.this, "Enter Subject", Toast.LENGTH_SHORT).show();
                }else {
                    SubjectDbhelper subjectDbhelper1 = new SubjectDbhelper(SubjectActivity.this);
                    subjectDbhelper1.addSubject(subText.getText().toString());

                    subText.setText("");
                    subText.requestFocus();
                    Cursor data2 = subjectDbhelper.fetchSubject();
                    if (data2.moveToLast()){
                        subJectlist.add(data2.getString(0));
                    }
                    Collections.sort(subJectlist);

                    ArrayAdapter<String> adapters = new ArrayAdapter<>(SubjectActivity.this, R.layout.list_item_bsa,subJectlist);
                    recyclerView.setAdapter(adapters);






                }
            }
        });recyclerView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFromList =(recyclerView.getItemAtPosition(i).toString());
                new AlertDialog.Builder(SubjectActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are You Sure")
                        .setMessage("Do you Want to delete This Subject: " + selectedFromList)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteHead(selectedFromList);

                            }
                        }).setNegativeButton("no", null)
                        .show();


                return false;
            }
        });




    } public void onBackPressed(){
        super.onBackPressed();
    }
    public void deleteHead(String unwanted){
        SubjectDbhelper subjectDbhelper = new SubjectDbhelper(SubjectActivity.this);
        subjectDbhelper.deleteHead(unwanted);
        subJectlist.remove(unwanted);
        recyclerView.invalidateViews();



    }


}

