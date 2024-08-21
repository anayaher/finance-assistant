package com.example.bsaassistant.salaryactivities;

import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bsaassistant.R;
import com.example.bsaassistant.databases.salaryDatabases.AllowanceDbHelper;

public class SetSalaryActivity extends AppCompatActivity {
    EditText basicEdt,hraEdt,taEdt,daEdt,otherEdt,totalEdt;
    TextView incrementTv;
    Button save,update,incrementBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing components
        setContentView(R.layout.activity_set_salary);
        basicEdt = findViewById(R.id.setSalBasicEdt);
        hraEdt = findViewById(R.id.setSalHraEdt);
        taEdt = findViewById(R.id.setSalTaEdt);
        daEdt = findViewById(R.id.setSalDaEdt);
        otherEdt = findViewById(R.id.setSalOtherEdt);
        save = findViewById(R.id.setSalSaveBtn);
        update = findViewById(R.id.setSalUpdateBtn);
        totalEdt = findViewById(R.id.setSalTotalEdt);
        incrementTv = findViewById(R.id.salSlipIncTv);
        incrementBtn = findViewById(R.id.salSlipIncBtn);


        setInputFilters();



        disableEditTexts();

        setValues();
        setTotal();
        //setting save button to disable as default
        save.setEnabled(false);

        //setting values to all textViews


        //update Button Functioning
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save.setEnabled(true);
                enableEditTexts();

            }
        });

        //incrementBtn
        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curBasic = Integer.parseInt(basicEdt.getText().toString());
                int baInc = (curBasic*3)/100;
                incrementTv.setText(String.valueOf(baInc));
            }
        });



        //save Button Functioning
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkForNull()){
                    saveValues();
                    setTotal();

                }else {
                    Toast.makeText(SetSalaryActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }




            }
        });




    }
    private  boolean checkForNull(){
        return basicEdt.getText().toString().equals("") || hraEdt.getText().toString().equals("") || daEdt.getText().toString().equals("") || taEdt.getText().toString().equals("") || otherEdt.getText().toString().equals("");

    }

    private void setInputFilters() {

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        basicEdt.setFilters(new InputFilter[]{filter});
        hraEdt.setFilters(new InputFilter[]{filter});
        taEdt.setFilters(new InputFilter[]{filter});
        otherEdt.setFilters(new InputFilter[]{filter});
        daEdt.setFilters(new InputFilter[]{filter});
    }

    private void enableEditTexts() {
        basicEdt.setEnabled(true);
        hraEdt.setEnabled(true);
        taEdt.setEnabled(true);
        otherEdt.setEnabled(true);
        daEdt.setEnabled(true);

    }

    private void disableEditTexts() {

        basicEdt.setEnabled(false);
        hraEdt.setEnabled(false);
        taEdt.setEnabled(false);
        otherEdt.setEnabled(false);
        daEdt.setEnabled(false);

    }
    private void setTotal() {
        AllowanceDbHelper allowanceDbHelper= new AllowanceDbHelper(this);

        int total = allowanceDbHelper.getTotalAllowance();
        totalEdt.setText(String.valueOf(total));

    }


    private void saveValues(){
        //Initialize the variables
        Integer basic = 0;
        Integer hra = 0;
        Integer da = 0;
        Integer ta = 0;
        Integer other =0;


        //Initialize the database
        AllowanceDbHelper allowanceDbHelper = new AllowanceDbHelper(SetSalaryActivity.this);

        //Enter Values from Edittext to integer variables

       basic = Integer.parseInt(basicEdt.getText().toString());
        hra = Integer.parseInt(hraEdt.getText().toString());
        da = Integer.parseInt(daEdt.getText().toString());
        ta = Integer.parseInt(taEdt.getText().toString());
        other = Integer.parseInt(otherEdt.getText().toString());

        //The function to enter the variables into the database
        allowanceDbHelper.insertAllowances(basic,hra,da,ta,other);


        Toast.makeText(SetSalaryActivity.this, "Entries Saved Successfully", Toast.LENGTH_SHORT).show();
        disableEditTexts();
        save.setEnabled(false);







    }

    private void setValues() {
        //Initialize the variables
        int basic = 0;
        int hra = 0;
        int da = 0;
        int ta = 0;
        int other =0;


        //Initialize the database and Cursor
        AllowanceDbHelper allowanceDbHelper = new AllowanceDbHelper(this);
        Cursor cursor = allowanceDbHelper.getAllowances();

        //when database is empty
        if (cursor.getCount() == 0)
        {

            basicEdt.setText("0");
            otherEdt.setText("0");
            daEdt.setText("0");
            hraEdt.setText("0");
            taEdt.setText("0");
            Toast.makeText(this, "No Data Available!", Toast.LENGTH_SHORT).show();


        }
        //When database is non empty

        else {

            while (cursor.moveToNext()){
                basic = cursor.getInt(0);
                hra = cursor.getInt(1);
                da = cursor.getInt(2);
                ta = cursor.getInt(3);
                other = cursor.getInt(4);

            }
            basicEdt.setText(String.valueOf(basic));
            hraEdt.setText(String.valueOf(hra));
            daEdt.setText(String.valueOf(da));
            taEdt.setText(String.valueOf(ta));
            otherEdt.setText(String.valueOf(other));



            //If Cursor is not moving to next

        }


    }
}