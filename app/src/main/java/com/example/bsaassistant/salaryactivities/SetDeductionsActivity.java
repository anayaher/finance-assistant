package com.example.bsaassistant.salaryactivities;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bsaassistant.R;
import com.example.bsaassistant.databases.salaryDatabases.DeductionDbHelper;

public class SetDeductionsActivity extends AppCompatActivity {

    EditText itEdt,ptEdt,gpfEdt,janseEdt,vidyaEdt,otherEdt,totalEdt;
    Button save,update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_deductions);

        //initialziing all Elements
        itEdt = findViewById(R.id.setDedItEdt);
        gpfEdt =findViewById(R.id.setDedGpfEdt);
        ptEdt =findViewById(R.id.setDedPtEdt);
        vidyaEdt =findViewById(R.id.setDedVidyaEdt);
        janseEdt =findViewById(R.id.setDedJansevaEdt);
        otherEdt =findViewById(R.id.setDedOtherEdt);
        totalEdt = findViewById(R.id.setDedTotalEdt);


        //BTNS
        save = findViewById(R.id.setDedSaveBtn);
        update = findViewById(R.id.setDedUpdateBtn);


        //start

        //setting values
        setValues();
        setTotal();


        //disable all texts and save button as default
        disableAllTexts();
        save.setEnabled(false);

        //update button to enable save and textss
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save.setEnabled(true);
                enableTexts();
            }
        });


        //save button functioning
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!checkForNull()){
                   saveValues();
               }else {
                   Toast.makeText(SetDeductionsActivity.this, "Please Enter All Fields!", Toast.LENGTH_SHORT).show();
               }

            }
        });






    }

    private void saveValues() {


        //initialize variables

        int it;
        int pt;
        int gpf;
        int janse;
        int vidya;
        int other;

        //initialize database

        DeductionDbHelper deductionDbHelper =new DeductionDbHelper(this);

        //add values from text to variables

        it = Integer.parseInt(itEdt.getText().toString());
        pt = Integer.parseInt(ptEdt.getText().toString());
        gpf = Integer.parseInt(gpfEdt.getText().toString());
        janse = Integer.parseInt(janseEdt.getText().toString());
        vidya = Integer.parseInt(vidyaEdt.getText().toString());
        other = Integer.parseInt(otherEdt.getText().toString());

        //save Values to database

        deductionDbHelper.insertDeduction(it,pt,gpf,janse,vidya,other);
        Toast.makeText(this, "Entries Added Successfully", Toast.LENGTH_SHORT).show();
        save.setEnabled(false);
        setTotal();

        disableAllTexts();

    }

    private void setTotal() {
        DeductionDbHelper deductionDbHelper = new DeductionDbHelper(this);

        int total = deductionDbHelper.getTotalDeduction();
        totalEdt.setText(String.valueOf(total));

    }
    private  boolean checkForNull(){
        return itEdt.getText().toString().equals("") || ptEdt.getText().toString().equals("") || gpfEdt.getText().toString().equals("") || janseEdt.getText().toString().equals("") || vidyaEdt.getText().toString().equals("") || otherEdt.getText().toString().equals("");

    }

    private void setValues() {


        //initialize variables
        int it = 0;
        int pt = 0;
        int gpf = 0;
        int janse = 0 ;
        int vidya = 0;
        int other = 0;
        DeductionDbHelper deductionDbHelper = new DeductionDbHelper(this);
        Cursor data = deductionDbHelper.getDeductions();
        if (data.getCount() == 0 ){
            Toast.makeText(this, "No Data Available!", Toast.LENGTH_SHORT).show();
        }else {
            while (data.moveToNext()){
                it = data.getInt(0);
                pt = data.getInt(1);
                gpf = data.getInt(2);
                janse = data.getInt(3);
                vidya = data.getInt(4);
                other = data.getInt(5);



            }

            itEdt.setText(String.valueOf(it));
            ptEdt.setText(String.valueOf(pt));
            gpfEdt.setText(String.valueOf(gpf));
            vidyaEdt.setText(String.valueOf(janse));
            janseEdt.setText(String.valueOf(vidya));
            otherEdt.setText(String.valueOf(other));
        }
    }

    private void enableTexts() {
        itEdt.setEnabled(true);
        ptEdt.setEnabled(true);
        gpfEdt.setEnabled(true);
        vidyaEdt.setEnabled(true);
        janseEdt.setEnabled(true);
        otherEdt.setEnabled(true);
    }

    private void disableAllTexts() {

        itEdt.setEnabled(false);
        ptEdt.setEnabled(false);
        gpfEdt.setEnabled(false);
        vidyaEdt.setEnabled(false);
        janseEdt.setEnabled(false);
        otherEdt.setEnabled(false);
    }
}