package com.example.bsaassistant.Exam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bsaassistant.R;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExamBill extends AppCompatActivity implements myCustomAlertDialog.myinterface {
   TabLayout tabLayout;
    ViewPager2 viewPager2;
    myFragmentAdapter adapter;
    Button save;
    TextView exam;
    TextView totalTv;
    ArrayList<Integer>ratesPC,ratesS,ratesPS;
    ArrayList<Integer>nosPC,nosS,nosPS;
    String examName;
    TextView refresh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_bill);
        tabLayout = findViewById(R.id.tabLayoutExam);
        viewPager2 = findViewById(R.id.viewPagerExam);
        exam = findViewById(R.id.examNameExambill);
        String ename = getIntent().getStringExtra("passExamName");
        this.examName = ename;
        exam.setText(getIntent().getStringExtra("passExamName"));
        totalTv = findViewById(R.id.mainexamTotalTv);
        refresh = findViewById(R.id.refreshBtn);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               refreshBtn();
            }
        });


        tabLayout.addTab(tabLayout.newTab().setText("Setting"));


        tabLayout.addTab(tabLayout.newTab().setText("SuperVision"));
        tabLayout.addTab(tabLayout.newTab().setText("Checking"));

        FragmentManager fragmentManager = getSupportFragmentManager();

        adapter = new myFragmentAdapter(fragmentManager,getLifecycle(),ename);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(2);
        ratesPC = new ArrayList<>();
        ratesS = new ArrayList<>();
        ratesPS = new ArrayList<>();
        nosPC = new ArrayList<>();
        nosS = new ArrayList<>();
        nosPS = new ArrayList<>();
        getExamTotal();







        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if(position == 0){

                }else  if(position == 1){


                }else {

                }
                tabLayout.selectTab(tabLayout.getTabAt(position));

            }
        });





       /**  tabItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTab(1);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container, Exam_1.class,null)
                        .commit();

            }
        });
        tabItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTab(2);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container, Exam_2.class,null)
                        .commit();

            }
        });
        tabItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTab(3);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container, Exam_3.class,null)
                        .commit();

            }
        });
    }
    private void selectTab(int tabnumber){
        TextView selectedTextView;
               TextView nonSelectedTextView1,nonSelectedTextView2;

        if(tabnumber == 1){
            selectedTextView = tabItem1;
            nonSelectedTextView1 = tabItem2;
            nonSelectedTextView2  = tabItem3;


        }
        else if(tabnumber == 2){
            selectedTextView = tabItem2;
            nonSelectedTextView1 = tabItem1;
            nonSelectedTextView2  = tabItem3;



        }else {
            selectedTextView = tabItem3;
            nonSelectedTextView1 = tabItem1;
            nonSelectedTextView2 = tabItem2;
        }


        float slideTo = (tabnumber - selectedItem)* selectedTextView.getWidth();
        TranslateAnimation translateAnimation = new TranslateAnimation(0,slideTo,0,0);
        translateAnimation.setDuration(100);

        if(selectedItem == 1){
            tabItem1.startAnimation(translateAnimation);


        }else  if(selectedItem == 2){
            tabItem2.startAnimation(translateAnimation);

        }
        else  if(selectedItem == 3){
            tabItem3.startAnimation(translateAnimation);


        }
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                selectedTextView.setBackgroundResource(R.drawable.white_100);
                selectedTextView.setTypeface(null, Typeface.BOLD);
                selectedTextView.setTextColor(Color.BLACK);


                nonSelectedTextView1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                nonSelectedTextView1.setTextColor(Color.parseColor("#80FFFFFF"));
                nonSelectedTextView1.setTypeface(null,Typeface.NORMAL);


                nonSelectedTextView2.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                nonSelectedTextView2.setTextColor(Color.parseColor("#80FFFFFF"));
                nonSelectedTextView2.setTypeface(null,Typeface.NORMAL);






            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        selectedItem = tabnumber;*/


        }
        public void refreshBtn(){
        nosPS.clear();
        nosS.clear();
        nosPC.clear();
        ratesPS.clear();
        ratesS.clear();
        ratesPC.clear();

            paperCheckingDbHelper paperCheckingDbHelper = new paperCheckingDbHelper(getApplicationContext());
            supervisionDbHelper supervisionDbHelper = new supervisionDbHelper(getApplicationContext());
            paperSettingDbHelper paperSettingDbHelper = new paperSettingDbHelper(getApplicationContext());
            ratesDbHelper ratesDbHelper = new ratesDbHelper(getApplicationContext());
            Cursor dataPC = paperCheckingDbHelper.fetchPaperChecking(examName);
            Cursor dataS =supervisionDbHelper.fetchPaperSetting(examName);
            Cursor dataPS =paperSettingDbHelper.fetchPaperSetting(examName);
            Cursor ratesC = ratesDbHelper.fetchRates();
            int mainTotalPaperCheckeds = 0;
            int mainTotalSupervisions = 0;
            int mainTotalPaperSetteds = 0;
            int totalPc = 0;
            int totalS = 0;
            int totalPs= 0;
            int ratesPc = 0;
            int ratesS = 0;
            int ratesPs = 0;

            if(ratesC.getCount()==0){

            }
            else {
                while (ratesC.moveToNext()){
                    ratesPc = ratesC.getInt(0);
                    ratesS = ratesC.getInt(1);
                    ratesPs = ratesC.getInt(2);
                }
            }
            if(dataPC.getCount()==0){

            }else {
                while (dataPC.moveToNext()){
                    nosPC.add(dataPC.getInt(5));

                }
                for (int i = 0;i<nosPC.size();i++){
                    totalPc += nosPC.get(i);

                }



            }
            ////
            //
            if (dataS.getCount()==0) {


            }
            else {
                while (dataS.moveToNext()){
                    nosS.add(dataS.getInt(4));

                }
                for (int i = 0;i<nosS.size();i++){
                    totalS += nosS.get(i);

                }



            }
            if (dataPS.getCount() == 0){


            }else {

                while (dataPS.moveToNext()){
                    nosPS.add(dataPS.getInt(5));

                }
                for (int i = 0;i<nosPS.size();i++){
                    totalPs += nosPS.get(i);

                }




            }
            mainTotalPaperCheckeds = totalPc*ratesPc;
            mainTotalSupervisions = totalS*ratesS;
            mainTotalPaperSetteds = totalPs*ratesPs;
            totalTv.setText(String.valueOf(mainTotalPaperCheckeds+mainTotalSupervisions+mainTotalPaperSetteds));
        }
        public void getExamTotal(){
        paperCheckingDbHelper paperCheckingDbHelper = new paperCheckingDbHelper(getApplicationContext());
        supervisionDbHelper supervisionDbHelper = new supervisionDbHelper(getApplicationContext());
        paperSettingDbHelper paperSettingDbHelper = new paperSettingDbHelper(getApplicationContext());
        ratesDbHelper ratesDbHelper = new ratesDbHelper(getApplicationContext());
        Cursor dataPC = paperCheckingDbHelper.fetchPaperChecking(examName);
        Cursor dataS =supervisionDbHelper.fetchPaperSetting(examName);
        Cursor dataPS =paperSettingDbHelper.fetchPaperSetting(examName);
        Cursor ratesC = ratesDbHelper.fetchRates();
        int mainTotalPaperCheckeds = 0;
        int mainTotalSupervisions = 0;
        int mainTotalPaperSetteds = 0;
        int totalPc = 0;
        int totalS = 0;
        int totalPs= 0;
        int ratesPc = 0;
        int ratesS = 0;
        int ratesPs = 0;

        if(ratesC.getCount()==0){

        }
        else {
            while (ratesC.moveToNext()){
                ratesPc = ratesC.getInt(0);
                ratesS = ratesC.getInt(1);
                ratesPs = ratesC.getInt(2);
            }
        }
        if(dataPC.getCount()==0){

        }else {
            while (dataPC.moveToNext()){
                nosPC.add(dataPC.getInt(5));

            }
            for (int i = 0;i<nosPC.size();i++){
                totalPc += nosPC.get(i);

            }



        }
        ////
            //
             if (dataS.getCount()==0) {


             }
             else {
                 while (dataS.moveToNext()){
                     nosS.add(dataS.getInt(4));

                 }
                 for (int i = 0;i<nosS.size();i++){
                     totalS += nosS.get(i);

                 }



            }
             if (dataPS.getCount() == 0){


             }else {

                 while (dataPS.moveToNext()){
                     nosPS.add(dataPS.getInt(5));

                 }
                 for (int i = 0;i<nosPS.size();i++){
                     totalPs += nosPS.get(i);

                 }




             }
             mainTotalPaperCheckeds = totalPc*ratesPc;
             mainTotalSupervisions = totalS*ratesS;
             mainTotalPaperSetteds = totalPs*ratesPs;
             totalTv.setText(String.valueOf(mainTotalPaperCheckeds+mainTotalSupervisions+mainTotalPaperSetteds));


        }

    @Override
    public void refresh() {


    }
    public void onResume() {

        super.onResume();
    }
}
