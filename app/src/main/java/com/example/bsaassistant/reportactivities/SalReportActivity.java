package com.example.bsaassistant.reportactivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.example.bsaassistant.adapters.salSlipAdapter;
import com.example.bsaassistant.databases.salaryDatabases.SalaryDbHelper;
import com.example.bsaassistant.datamodels.salaryModel;

import java.util.ArrayList;

public class SalReportActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView basicTv,daTv,hraTv,taTv,salOtherTv,salTotalTv;
    TextView itTv,ptTv,gpfTv,janseTv,vidyaTv,dedOtherTv,dedTotalTv,payInHandTv;
    ArrayList<salaryModel> salaryModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sal_report);
        //Initializing recycler view
        recyclerView = findViewById(R.id.salReportRv);

        //initializing textview for showing total

        basicTv = findViewById(R.id.salRepBasicTv);
        daTv = findViewById(R.id.salRepDaTv);
        hraTv = findViewById(R.id.salRepHratv);
        taTv =findViewById(R.id.salRepTaTv);
        salOtherTv = findViewById(R.id.salRepOtherTv);
        salTotalTv = findViewById(R.id.salRepSalTotalTv);

        itTv = findViewById(R.id.salRepItTv);
        ptTv =findViewById(R.id.salRepPtTv);
        gpfTv =findViewById(R.id.salRepGpfTv);
        janseTv = findViewById(R.id.salRepJanseTv);
        vidyaTv = findViewById(R.id.salRepVidyaTv);
        dedOtherTv = findViewById(R.id.salRepDedOtherTv);
        dedTotalTv = findViewById(R.id.salRepDedTotalTv);
        payInHandTv = findViewById(R.id.salRepPaytv);

        //init arraylist
        salaryModels = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //getDates to get Salary
        String fromDate = null;
        String toDate = null;
        fromDate = getIntent().getStringExtra("passSalFromDate");
        toDate = getIntent().getStringExtra("passSalToDate");

        if (fromDate == null || toDate == null){
            Toast.makeText(this, "Cant Show Sal", Toast.LENGTH_SHORT).show();
        }else {
            //get salary model to pass to adapter
            SalaryDbHelper salaryDbHelper = new SalaryDbHelper(this);
            salaryModels = salaryDbHelper.fetchSalBetween(fromDate,toDate);
            salSlipAdapter salSlipAdapter = new salSlipAdapter(this,salaryModels );

            recyclerView.setAdapter(salSlipAdapter);
            //set Totals
            int basicTotal = 0;int hraTotal =0;int daTotal= 0;int TaTotal = 0;int otherTotal = 0;int salTotal=0;int dedTotal =0;
            int itTotal = 0;int ptTotal = 0;int gpfTotal =0;int janseTotal =0;int vidyaTotal =0;int dedOtherTotal =0;
            int payTotal = 0;


            //da ta
            int daVal = 0;

            for (int i =0;i<salaryModels.size();i++){
                basicTotal += salaryModels.get(i).getBasic();
                hraTotal += (int)(salaryModels.get(i).getBasic()*salaryModels.get(i).getHra()/100);
                daTotal +=  (int)(salaryModels.get(i).getBasic()*salaryModels.get(i).getDa()/100);


                TaTotal += salaryModels.get(i).getTa();
                otherTotal += salaryModels.get(i).getOther();
                salTotal +=salaryModels.get(i).getSalHeadTotal();
                dedTotal += salaryModels.get(i).getDedHeadTotal();
                itTotal += salaryModels.get(i).getIt();
                ptTotal += salaryModels.get(i).getPt();
                gpfTotal += salaryModels.get(i).getGpf();
                janseTotal += salaryModels.get(i).getJanse();
                vidyaTotal += salaryModels.get(i).getVidya();
                dedOtherTotal += salaryModels.get(i).getDedOther();
                payTotal += salaryModels.get(i).getPayInHand();
            }
            basicTv.setText(String.valueOf(basicTotal));
            hraTv.setText(String.valueOf(hraTotal));


            daTv.setText(String.valueOf(daTotal));
            taTv.setText(String.valueOf(TaTotal));
            salOtherTv.setText(String.valueOf(otherTotal));
            salTotalTv.setText(String.valueOf(salTotal));
            dedTotalTv.setText(String.valueOf(dedTotal));
            itTv.setText(String.valueOf(itTotal));
            ptTv.setText(String.valueOf(ptTotal));
            gpfTv.setText(String.valueOf(gpfTotal));
            janseTv.setText(String.valueOf(janseTotal));
            vidyaTv.setText(String.valueOf(vidyaTotal));
            dedOtherTv.setText(String.valueOf(dedOtherTotal));
            payInHandTv.setText(String.valueOf(payTotal));


        }








    }
}