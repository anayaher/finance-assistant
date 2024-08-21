package com.example.bsaassistant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.bsaassistant.bankLoan.LoanMain;
import com.example.bsaassistant.bankLoan.loanDbHelper;
import com.example.bsaassistant.carMaintainance.ChangePasswordActivity;
import com.example.bsaassistant.databases.JamaKharachDbHelper;
import com.example.bsaassistant.budget.BudgetActivity;
import com.example.bsaassistant.budget.budgetExpenseDbhelper;
import com.example.bsaassistant.budget.budgetIncomeDbhelper;
import com.example.bsaassistant.databases.salaryDatabases.AllowanceDbHelper;
import com.example.bsaassistant.databases.salaryDatabases.DeductionDbHelper;
import com.example.bsaassistant.databases.yenedeneDBhelper;
import com.example.bsaassistant.gold.GoldMain;
import com.example.bsaassistant.gold.goldDbHelper;
import com.example.bsaassistant.goldLoan.GoldLoanMain;
import com.example.bsaassistant.goldLoan.goldLoanDbHelper;
import com.example.bsaassistant.records.RecordMain;
import com.example.bsaassistant.reportactivities.ReportMainActivity;
import com.example.bsaassistant.salaryactivities.SalSlipActivity;
import com.example.bsaassistant.salaryactivities.SetDeductionsActivity;
import com.example.bsaassistant.salaryactivities.SetSalaryActivity;
import com.example.bsaassistant.share.ShareMainActivity;

import com.example.bsaassistant.share.ShareTransactionDbHelper;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    Button tabExit,mainExit;

    private ActionBarDrawerToggle drawerToggle;

    //BUTTON DECLARATION
    CardView reportButton,yeneDeneCard,goldCard,loanCard,recordtv;


    //Declaring all Cards
    private CardView salSlipCard,jkCard,budgetCard;
    private  TextView incomeyeneTv,expenseYeneTv,balYeneTv;
    private TextView salCardAllowanceTv,salCardDedtv,salCardTotalTv;
    private  TextView budgetCardIncomeTv,budgetCardExpenseTv,budgetCardBalTv;

    private TextView jkCardIncomeTv,jkCardExpenseTv,jkCardBalanceTv;
    TextView bachatShares,bachatGoldv,bachatTotal;
    TextView banKLoanTv,goldLoanTv,totalLoanTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout);


        // Create ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Initialize NavigationView and set onClickListeneres
        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id==R.id.setSalaryItem){
                    Intent intent = new Intent(MainActivity.this, SetSalaryActivity.class);
                    startActivity(intent);

                }else if (id==R.id.setDeductionItem){
                    Intent intent = new Intent(MainActivity.this, SetDeductionsActivity.class);
                    startActivity(intent);
                }
                else if (id==R.id.addHeadItem){
                    Intent intent = new Intent(MainActivity.this, AddHeadsAcitivity.class);
                    startActivity(intent);

                }
                else if(id==R.id.addPayeeItem){
                    Intent intent = new Intent(MainActivity.this, AddPayeeActivity.class);
                    startActivity(intent);

                }
                else if(id==R.id.changePassItem){
                    Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);



                }else {

                }
                return false;
            }
        });
        tabExit = findViewById(R.id.tbExit);
        mainExit = findViewById(R.id.mainpageExit);
        mainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tabExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Main page Card Code
        Intent serviceIntent = new Intent(this, MyBackgroundService.class);
        startService(serviceIntent);

        // Schedule the job using JobScheduler
        MyScheduler.scheduleJob(this);

        // Set up the repeating alarm using AlarmManager
        MyAlarmManager.setRepeatingAlarm(this);



        //Initializing All Cards
        salSlipCard = findViewById(R.id.payment);
        salCardAllowanceTv = findViewById(R.id.mainsal);
        salCardDedtv = findViewById(R.id.mainTotalDed);
        salCardTotalTv = findViewById(R.id.maintotal);
        //jkCard
        jkCard = findViewById(R.id.InOut);
        jkCardIncomeTv = findViewById(R.id.jama);
        jkCardExpenseTv = findViewById(R.id.kharch);
        jkCardBalanceTv = findViewById(R.id.shillak);
        //BudgetCard
        budgetCard = findViewById(R.id.budgetCardView);
        budgetCardIncomeTv = findViewById(R.id.budIncome);
        budgetCardExpenseTv = findViewById(R.id.budExpense);
        budgetCardBalTv = findViewById(R.id.budBalance);
        //yeneDeneCard
        yeneDeneCard = findViewById(R.id.deneghene);
        incomeyeneTv = findViewById(R.id.MainLendtv);
        expenseYeneTv = findViewById(R.id.MainBorrow);
        balYeneTv = findViewById(R.id.MainTotalYD);
        //Bachat Card
        goldCard = findViewById(R.id.share);
        bachatShares = findViewById(R.id.sharesPatTv);
        bachatGoldv = findViewById(R.id.sharesBankTv);
        bachatTotal = findViewById(R.id.sharesTotalTv);

        //Loan Card
        loanCard = findViewById(R.id.loanCard);
        banKLoanTv = findViewById(R.id.bankLoanMainTotal);
        goldLoanTv = findViewById(R.id.MainGoldLaonTotal);
        totalLoanTv  = findViewById(R.id.MainTotalLoanAmtTv);

        //record tv
        recordtv = findViewById(R.id.record);
        recordtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordMain.class);
                startActivity(intent);
            }
        });











        reportButton = findViewById(R.id.report);


       //setView
        setView();

        //All Cards On Click Listeners

        yeneDeneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainYeneDeneActivity.class);
                startActivity(intent);


            }
        });



        salSlipCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SalSlipActivity.class);
                startActivity(intent);
            }
        });
        //jk on Click Listener
        jkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,JamaKharchaActivity.class);
                startActivity(intent);
            }
        });
        //budgetCard OnClickListener
        budgetCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BudgetActivity.class);
                startActivity(intent);

            }
        });

        //goldCard onClickListener
        goldCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Open");
                builder.setMessage("What Do You Want To Open?");
                builder.setNeutralButton("Gold", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, GoldMain.class);
                        startActivity(intent);
                    }
                });
                builder.setPositiveButton("Shares", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, ShareMainActivity.class);
                        startActivity(intent);

                    }
                });
                builder.show();



            }
        });
        //Loan Card onClickListener
        loanCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Open");
                builder.setMessage("What Do You Want To Open?");
                builder.setNeutralButton("Bank Loan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, LoanMain.class);
                        startActivity(intent);
                    }
                });
                builder.setPositiveButton("Gold Loan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, GoldLoanMain.class);
                        startActivity(intent);

                    }
                });
                builder.show();





            }
        });



        //report onClickListener
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReportMainActivity.class);
                startActivity(intent);

            }
        });



    }

    private void setView() {

        //set Salary Card

        //initalize variables
        int totalAllowance = 0;
        int totalDeduction = 0;
        int payInHand =0;

        int totalJkIncome = 0;
        int totalJkExpense = 0;
        int totalJkBal = 0;

        int budgetIncome =0;
        int budgetExpense =0;
        int budgetBal = 0;

        int yene =0;
        int dene =0;
        int ydBal =0;

        int shares =0;
        int gold =0;
        int totalBachat =0;

        int bankLoan =0;
        int goldLoan =0;
        int totalLoan =0;



        //first Initialize databases
        AllowanceDbHelper allowanceDbHelper = new AllowanceDbHelper(this);
        DeductionDbHelper deductionDbHelper = new DeductionDbHelper(this);
        JamaKharachDbHelper jamaKharachDbHelper = new JamaKharachDbHelper(this);
        budgetIncomeDbhelper budgetIncomeDbhelper = new budgetIncomeDbhelper(this);
        budgetExpenseDbhelper budgetExpenseDbhelper = new budgetExpenseDbhelper(this);
        yenedeneDBhelper yenedeneDBhelper = new yenedeneDBhelper(this);
        ShareTransactionDbHelper shareTransactionDbHelper = new ShareTransactionDbHelper(this);
        goldDbHelper goldDbHelper = new goldDbHelper(this);
        goldLoanDbHelper goldLoanDbHelper = new goldLoanDbHelper(this);
        loanDbHelper loanDbHelper = new loanDbHelper(this);







        //put values into vars from db
        totalAllowance = allowanceDbHelper.getTotalAllowance();
        totalDeduction = deductionDbHelper.getTotalDeduction();

        totalJkExpense = jamaKharachDbHelper.getAllExpense();
        totalJkIncome = jamaKharachDbHelper.getAllIncome();

        budgetIncome = budgetIncomeDbhelper.getTotalIncome();
        budgetExpense = budgetExpenseDbhelper.getTotalExpense();


        yene = yenedeneDBhelper.getTotalYene();
        dene = yenedeneDBhelper.getTotalDene();

        shares  = shareTransactionDbHelper.getAllTotal();
        gold =  goldDbHelper.getTotalAmt();

        goldLoan  = goldLoanDbHelper.getTotalLoan();
        bankLoan = loanDbHelper.getTotalLoans();



        payInHand = totalAllowance - totalDeduction;
        totalJkBal = totalJkIncome-totalJkExpense;
        budgetBal = budgetIncome- budgetExpense;
        ydBal = yene - dene;
        totalBachat = gold + shares;
        totalLoan = goldLoan + bankLoan;

        //setTexts
        salCardAllowanceTv.setText(String.valueOf(totalAllowance));
        salCardDedtv.setText(String.valueOf(totalDeduction));
        salCardTotalTv.setText(String.valueOf(payInHand));

        jkCardIncomeTv.setText(String.valueOf(totalJkIncome));
        jkCardExpenseTv.setText(String.valueOf(totalJkExpense));
        jkCardBalanceTv.setText(String.valueOf(totalJkBal));

        budgetCardIncomeTv.setText(String.valueOf(budgetIncome));
        budgetCardExpenseTv.setText(String.valueOf(budgetExpense));
        budgetCardBalTv.setText(String.valueOf(budgetBal));

        incomeyeneTv.setText(String.valueOf(yene));
        expenseYeneTv.setText(String.valueOf(dene));
        balYeneTv.setText(String.valueOf(ydBal));

        bachatShares.setText(String.valueOf(shares));
        bachatGoldv.setText(String.valueOf(gold));
        bachatTotal.setText(String.valueOf(totalBachat));

        banKLoanTv.setText(String.valueOf(bankLoan));
        goldLoanTv.setText(String.valueOf(goldLoan));
        totalLoanTv.setText(String.valueOf(totalLoan));

    }


    @Override
    protected void onResume() {
        super.onResume();
        setView();
    }
}
