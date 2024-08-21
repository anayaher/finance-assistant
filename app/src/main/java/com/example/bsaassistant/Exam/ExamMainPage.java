package com.example.bsaassistant.Exam;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.bsaassistant.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class
ExamMainPage extends AppCompatActivity implements ExamDialog.ExamDialogListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar2;
    Button addExamBtn;
    ArrayList<String>examarr;
    RecyclerView examRv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_main_page);
        drawerLayout = findViewById(R.id.drawerlayoutExams);
        navigationView = findViewById(R.id.navigationview2);
        toolbar2 = findViewById(R.id.toolbar2);
        addExamBtn = findViewById(R.id.createExamBtn);
        examarr = new ArrayList<>();
        examRv = findViewById(R.id.recylerviewExamMain);
        examRv.setLayoutManager(new LinearLayoutManager(ExamMainPage.this));
        examMainAdapter examMainAdapter = new examMainAdapter(ExamMainPage.this,examarr);
        examRv.setAdapter(examMainAdapter);



        setSupportActionBar(toolbar2);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar2, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        examMainDbhelper examMainDbhelper = new examMainDbhelper(ExamMainPage.this);
        Cursor examCursor = examMainDbhelper.fetchExams();
        if(examCursor.getCount() == 0){

        }else{
            while (examCursor.moveToNext()){
                examarr.add(examCursor.getString(0));
            }
        }


        addExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExamDialog examDialog = new ExamDialog();
                examDialog.show(getSupportFragmentManager(), "Exam Dialog");
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.itemSetSubject){
                    Intent intent = new Intent(getBaseContext(),SubjectActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }



                else if (id == R.id.itemSetRates){

                    Intent intent = new Intent(getBaseContext(),setRates.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
                return false;
            }
        });



    }

    @Override
    public void refresh(String newExam) {
        examarr.add(newExam);
        examarr.clear();
        examMainDbhelper examMainDbhelper = new examMainDbhelper(ExamMainPage.this);
        Cursor examCursor = examMainDbhelper.fetchExams();
        if(examCursor.getCount() == 0){

        }else{
            while (examCursor.moveToNext()){
                examarr.add(examCursor.getString(0));
            }
        }
        examRv.scrollToPosition(examarr.size()-1);

    }public void onResume() {
        super.onResume();
        examarr.clear();
        examMainDbhelper examMainDbhelper = new examMainDbhelper(ExamMainPage.this);
        Cursor examCursor = examMainDbhelper.fetchExams();
        if(examCursor.getCount() == 0){

        }else{
            while (examCursor.moveToNext()){
                examarr.add(examCursor.getString(0));
            }
        }
        examMainAdapter examMainAdapter = new examMainAdapter(ExamMainPage.this,examarr);
        examRv.setAdapter(examMainAdapter);


    }

}