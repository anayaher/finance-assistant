package com.example.bsaassistant.Exam;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bsaassistant.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ExamDialog  extends AppCompatDialogFragment
{
    TextInputEditText createExamTv;
    RecyclerView recyclerView;
    private  ExamDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.exam_dialog,null);
        createExamTv = view.findViewById(R.id.createExamTv);
        builder.setView(view);
        builder.setTitle("Create Exam ")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        examMainDbhelper examMainDbhelper = new examMainDbhelper(getActivity());
                        if (createExamTv.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Pls Enter Name", Toast.LENGTH_SHORT).show();
                            createExamTv.requestFocus();
                        }
                        else {
                            examMainDbhelper.createExam(createExamTv.getText().toString(),0);
                            String newExamName = createExamTv.getText().toString();
                            listener.refresh(newExamName);


                            Toast.makeText(getActivity(), "Exam " + createExamTv.getText().toString() + " Created successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null);

        return  builder.create();



    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {

        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"MuSt implement method");
        }
        listener = (ExamDialogListener)context;
    }

    public interface  ExamDialogListener{
        void refresh(String newExam);


    }
}
