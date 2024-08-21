package com.example.bsaassistant.Exam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class myFragmentAdapter extends FragmentStateAdapter {
    String anay;

    public myFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,String examName) {
        super(fragmentManager, lifecycle);
        this.anay = examName;


    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("passString",anay);
        Exam_1 exam_1 = new Exam_1();
        Exam_2 exam_2 = new Exam_2();
        Exam_3 exam_3 = new Exam_3();
        exam_2.setArguments(bundle);
        exam_3.setArguments(bundle);
        exam_1.setArguments(bundle);

        if(position == 0){

           return  exam_1;


        }
        else  if(position == 1){
            return exam_3;
        }
       else {
            return exam_2;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
