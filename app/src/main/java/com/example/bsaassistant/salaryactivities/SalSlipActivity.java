package com.example.bsaassistant.salaryactivities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bsaassistant.R;
import com.example.bsaassistant.databases.JamaKharachDbHelper;
import com.example.bsaassistant.databases.salaryDatabases.AllowanceDbHelper;
import com.example.bsaassistant.databases.salaryDatabases.DeductionDbHelper;
import com.example.bsaassistant.databases.salaryDatabases.SalaryDbHelper;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.GrooveBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class SalSlipActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_FILE_PICKER = 1;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private ProgressBar progressBar;

    TextView basicEdt,hraEdt,taEdt,daEdt,salOtherEdt,salTotalEdt,monthEdt,dateEdt;
    
    TextView payInHandEdt,incremetnEdt;
    private static final  int REQUEST_CODE_WRITE_PERMISSION= 1;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    TextView itEdt,ptEdt,gpfEdt,janseEdt,vidyaEdt,DedotherEdt,DedtotalEdt;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //allowNce and date initialization
        setContentView(R.layout.activity_sal_slip);
        basicEdt = findViewById(R.id.paysheetBasic);
        hraEdt = findViewById(R.id.paysheetHra);
        taEdt =findViewById(R.id.paysheetTa);
        daEdt= findViewById(R.id.paysheetDa);
        salOtherEdt = findViewById(R.id.paysheetOtherSal);
        salTotalEdt = findViewById(R.id.paysheetTotalSalHead);
        monthEdt = findViewById(R.id.paysheetMonthEdt);
        dateEdt = findViewById(R.id.psDateEdt);
        progressBar= findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        //save btn initialization
        save = findViewById(R.id.paysheetSaveBtn);

        payInHandEdt = findViewById(R.id.paysheetPayInHand);
        //DEDUCTION INITIALIZATION

        itEdt = findViewById(R.id.paysheetIt);
        ptEdt = findViewById(R.id.paysheetPt);
        gpfEdt = findViewById(R.id.paysheetGpf);
        janseEdt = findViewById(R.id.paysheetJanseva);
        vidyaEdt = findViewById(R.id.paysheetVidya);
        DedotherEdt = findViewById(R.id.paysheetOther1);
        DedtotalEdt = findViewById(R.id.paysheetTotalDedHead);
        setValues();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dateEdt.getText().toString().equals("")){
                    Toast.makeText(SalSlipActivity.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        saveSal();
                        createPdf();

                        addSalToJk();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


        });










        // increment btn functioning





    }public void addSalToJk(){

        //Check if Sal Already Exists and delete if
        boolean exists = false;
        int delSr = 0;
        int lastAmt = 0;
        int currBals = Integer.parseInt(payInHandEdt.getText().toString());


        String monthName = null;
        JamaKharachDbHelper jamaKharachDbHelperx= new JamaKharachDbHelper(this);
        Cursor f = jamaKharachDbHelperx.getAllJk();
        if (f.getCount() == 0){

        }else {
            if (f.moveToNext()){
               exists = Objects.equals(monthName, f.getString(6));


            }
            if (f.moveToLast()){
                delSr = f.getInt(0)-1;
                lastAmt = f.getInt(4);

            }
            if (exists){
            jamaKharachDbHelperx.deleteSalForMonth(monthEdt.getText().toString());
            jamaKharachDbHelperx.updateSuccessorTransactions(delSr,1,currBals);

            }
        }

        //init sr and bal,totalSal vars
        int sr = 0;
        int bal = 0;
        int it = 0;
        int pt = 0;
        int gpf = 0;
        int janseva = 0;
        int vidya = 0;
        int other = 0;

        int totalSal = Integer.parseInt(salTotalEdt.getText().toString());

        //Create instance of db
        JamaKharachDbHelper jamaKharachDbHelper = new JamaKharachDbHelper(this);
        //getting cursor for latest srNo
        Cursor data = jamaKharachDbHelper.getAllJk();
        if (data.getCount()==0){
            sr=1;
            bal = 0;


        }
        else {

            //getting last or latest sr and bal
            if (data.moveToLast()){


                    sr= data.getInt(0)+1;




            }
        }


        //Deductionss
        bal = jamaKharachDbHelperx.getCurrentBal();

       it = Integer.parseInt(itEdt.getText().toString());
       pt = Integer.parseInt(ptEdt.getText().toString());
       gpf = Integer.parseInt(gpfEdt.getText().toString());
       janseva = Integer.parseInt(janseEdt.getText().toString());
       vidya = Integer.parseInt(vidyaEdt.getText().toString());
       other = Integer.parseInt(DedotherEdt.getText().toString());
       int currBal = bal+totalSal;


       //Adding Salary Allowance As One
        jamaKharachDbHelper.insertJk(sr,dateEdt.getText().toString(),1,totalSal,currBal,"SALARY",monthEdt.getText().toString(),"SALARY ALLOWANCE OF MONTH " + monthEdt.getText().toString());

        sr++;
        currBal = currBal-it;

        //Enter it
        jamaKharachDbHelper.insertJk(sr,dateEdt.getText().toString(),0,it,currBal,"Income Tax",monthEdt.getText().toString(),"Income Tax OF MONTH " + monthEdt.getText().toString());

        sr++;
        currBal = currBal - pt;

        //Enter Pt
        jamaKharachDbHelper.insertJk(sr,dateEdt.getText().toString(),0,pt,currBal,"Professional Tax",monthEdt.getText().toString(),"Professional Tax OF MONTH " + monthEdt.getText().toString());

        sr++;
        currBal = currBal - gpf;

        //Enter GPF
        jamaKharachDbHelper.insertJk(sr,dateEdt.getText().toString(),0,gpf,currBal,"Gpf ",monthEdt.getText().toString(),"GPF OF MONTH " + monthEdt.getText().toString());

        sr++;
        currBal = currBal - janseva;

        //ENTER JANSEVA

        jamaKharachDbHelper.insertJk(sr,dateEdt.getText().toString(),0,janseva,currBal,"Janseva ",monthEdt.getText().toString(),"Janseva EMI OF MONTH " + monthEdt.getText().toString());

        sr++;
        currBal = currBal - vidya;

        //ENTER VIDYA

        jamaKharachDbHelper.insertJk(sr,dateEdt.getText().toString(),0,vidya,currBal,"Vidyasevak ",monthEdt.getText().toString(),"VidyaSevak OF MONTH " + monthEdt.getText().toString());

        sr++;
        currBal = currBal - other;

        //ENTER OTHER'
        jamaKharachDbHelper.insertJk(sr,dateEdt.getText().toString(),0,other,currBal,"Other ",monthEdt.getText().toString(),"Other Deduction OF MONTH " + monthEdt.getText().toString());






        Toast.makeText(this, "Salary of This month Added Succesfully", Toast.LENGTH_SHORT).show();
    }

    private void saveSal() {
        SalaryDbHelper salaryDbHelper = new SalaryDbHelper(this);
        String date = dateEdt.getText().toString();
        String month = monthEdt.getText().toString();
        int payInHand = Integer.parseInt(payInHandEdt.getText().toString());


        //initialize database
        AllowanceDbHelper allowanceDbHelper = new AllowanceDbHelper(this);
        Cursor allowanceCursor = allowanceDbHelper.getAllowances();

        DeductionDbHelper deductionDbHelper = new DeductionDbHelper(this);
        Cursor deductionCursor = deductionDbHelper.getDeductions();



        //Initialize the variables
         int basic = 0;
        int hra = 0;
        int da = 0;
        int ta = 0;
        int other = 0;
        int totalSal = 0;

        int it = 0;
        int pt = 0;
        int gpf = 0;
        int janse = 0;
        int vidya = 0;
        int Dedother = 0;
        int totalDed = 0;



        //when database is empty
        if (allowanceCursor.getCount() == 0) {

        }
        //When database is non empty

        else {

            while (allowanceCursor.moveToNext()) {
                basic = allowanceCursor.getInt(0);
                hra = allowanceCursor.getInt(1);
                da = allowanceCursor.getInt(2);
                ta = allowanceCursor.getInt(3);
                other = allowanceCursor.getInt(4);

            }
            totalSal = allowanceDbHelper.getTotalAllowance();
            totalDed = deductionDbHelper.getTotalDeduction();


            //initialize variables

            if (deductionCursor.getCount() == 0) {
                Toast.makeText(this, "No Deductions Data Available!", Toast.LENGTH_SHORT).show();
            } else {
                while (deductionCursor.moveToNext()) {
                    it = deductionCursor.getInt(0);
                    pt = deductionCursor.getInt(1);
                    gpf = deductionCursor.getInt(2);
                    janse = deductionCursor.getInt(3);
                    vidya = deductionCursor.getInt(4);
                    Dedother = deductionCursor.getInt(5);


                }
                salaryDbHelper.insertSalary(date,month,basic,da,hra,ta,other,totalSal,it,pt,gpf,janse,vidya,Dedother,totalDed,payInHand);
                Toast.makeText(this, "Salary Entered Successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void setValues() {

        //SetDates


        setDates();

        //Initialize both databases here


        AllowanceDbHelper allowanceDbHelper = new AllowanceDbHelper(this);
        Cursor allowanceCursor = allowanceDbHelper.getAllowances();

        DeductionDbHelper deductionDbHelper = new DeductionDbHelper(this);
        Cursor deductionCursor = deductionDbHelper.getDeductions();

        //to check if both allowance and deduction are available
        if (allowanceCursor.getCount() == 0 || deductionCursor.getCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SalSlipActivity.this);
            builder.setTitle("Select Action")
                    .setMessage("Choose an action to perform")
                    .setPositiveButton("Set Deductions", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Handle "Set Deductions" button click
                            // Perform actions related to setting deductions

                            Intent intent = new Intent(SalSlipActivity.this, SetDeductionsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Set Allowances", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Handle "Set Allowances" button click
                            // Perform actions related to setting allowances

                            Intent intent = new Intent(SalSlipActivity.this, SetSalaryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            // Close the activity when the dialog is dismissed
                            finish();
                        }
                    });

            // Show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {


            //set allowances Values----------------------------------------------


            //Initialize the variables
            int basic = 0;
            int hra = 0;
            int da = 0;
            int ta = 0;
            int other = 0;


            //when database is empty
            if (allowanceCursor.getCount() == 0) {

                basicEdt.setText("0");
                salOtherEdt.setText("0");
                daEdt.setText("0");
                hraEdt.setText("0");
                taEdt.setText("0");
                Toast.makeText(this, "No Allowance Data Available!", Toast.LENGTH_SHORT).show();


            }
            //When database is non empty

            else {

                while (allowanceCursor.moveToNext()) {
                    basic = allowanceCursor.getInt(0);
                    hra = allowanceCursor.getInt(1);
                    da = allowanceCursor.getInt(2);
                    ta = allowanceCursor.getInt(3);
                    other = allowanceCursor.getInt(4);

                }
                basicEdt.setText(String.valueOf(basic));
                hraEdt.setText(String.valueOf(basic/100*hra));
                daEdt.setText(String.valueOf(basic/100*da));
                taEdt.setText(String.valueOf(ta));
                salOtherEdt.setText(String.valueOf(other));


                //If Cursor is not moving to next

            }

            //------------------------------------------------------------
            //set Deduction Values

            //initialize variables
            int it = 0;
            int pt = 0;
            int gpf = 0;
            int janse = 0;
            int vidya = 0;
            int Dedother = 0;

            if (deductionCursor.getCount() == 0) {
                Toast.makeText(this, "No Deductions Data Available!", Toast.LENGTH_SHORT).show();
            } else {
                while (deductionCursor.moveToNext()) {
                    it = deductionCursor.getInt(0);
                    pt = deductionCursor.getInt(1);
                    gpf = deductionCursor.getInt(2);
                    janse = deductionCursor.getInt(3);
                    vidya = deductionCursor.getInt(4);
                    Dedother = deductionCursor.getInt(5);


                }

                itEdt.setText(String.valueOf(it));
                ptEdt.setText(String.valueOf(pt));
                gpfEdt.setText(String.valueOf(gpf));
                vidyaEdt.setText(String.valueOf(janse));
                janseEdt.setText(String.valueOf(vidya));
                DedotherEdt.setText(String.valueOf(Dedother));
            }


        }

        setTotals();

    }


    private void setDates() {

        //Current date and datePicker implementation;
        dateEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();


                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                //setting the dialog
                DatePickerDialog dialog = new DatePickerDialog(SalSlipActivity.this, android.R.style.Theme_Holo_Light_Dialog,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = year + "-" + month + "-" + day;

                SimpleDateFormat receivedDateFormat = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());
                Date parsedDate = null;
                try {
                    parsedDate = receivedDateFormat.parse(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                // Format the date as "yyyy-mm-dd"
                SimpleDateFormat desiredDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = desiredDateFormat.format(parsedDate);

                dateEdt.setText(formattedDate);
            }
        };

        DateFormat dateFormat = new SimpleDateFormat("MMM-YYYY");
        Date date = new Date();
        monthEdt.setText(dateFormat.format(date));
    }

    private void setTotals() {

        int totalAllowance,totalDedcution,payInHand;
        AllowanceDbHelper allowanceDbHelper = new AllowanceDbHelper( this);
        DeductionDbHelper deductionDbHelper = new DeductionDbHelper(this);

        totalAllowance = allowanceDbHelper.getTotalAllowance();
        totalDedcution = deductionDbHelper.getTotalDeduction();
        payInHand = totalAllowance-totalDedcution;

        salTotalEdt.setText(String.valueOf(totalAllowance));
        DedtotalEdt.setText(String.valueOf(totalDedcution));
        payInHandEdt.setText(String.valueOf(payInHand));

    }



    private void createPdf() throws FileNotFoundException {
        progressBar.setVisibility(View.VISIBLE);

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/BSA/SalSlip/";
        File file = new File(pdfPath);
        if (!file.exists()){
            file.mkdirs();
        }

        com.itextpdf.kernel.colors.Color myColor = new DeviceRgb(235, 212, 255);
        com.itextpdf.kernel.colors.Color black = new DeviceRgb(0, 0, 0);
        com.itextpdf.kernel.colors.Color orange = new DeviceRgb(208, 247, 229);
        com.itextpdf.kernel.colors.Color darkOrange = new DeviceRgb(252, 228, 210);
        com.itextpdf.kernel.colors.Color red = new DeviceRgb(196, 0, 23);
        com.itextpdf.kernel.colors.Color white = new DeviceRgb(255, 255, 255);
        String filePath = pdfPath + monthEdt.getText().toString() +".pdf";
        File file1 = new File(filePath);

        OutputStream outputStream = new FileOutputStream(file1);
        PdfWriter pdfWriter = new PdfWriter(file1);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        Paragraph paragraph = new Paragraph("Salary Slip");
        Paragraph paragraph1 = new Paragraph("Month " + monthEdt.getText().toString());
        paragraph.setPaddingBottom(20);
        paragraph.setFontSize(20);
        paragraph.setTextAlignment(TextAlignment.CENTER).setBold().setUnderline();
        float columnWidt[] = {125, 112, 125, 125};
        float columnWidt1[] = {450,450};


        Table table = new Table(columnWidt);

        float font = 18;
        Border border = new GrooveBorder(new DeviceRgb(255, 255, 255), 0);
        Border border1 = new GrooveBorder(new DeviceRgb(0,0,0),3);
        Table table1 = new Table(columnWidt1);


        //Row1

        table1.addCell(new Cell(1,1).add(new Paragraph("Name: B S AHER" ).setTextAlignment(TextAlignment.CENTER)).setBorder(border).setFontSize(18).setBold());
        table1.addCell(new Cell(1,1).add(new Paragraph("Month: " +monthEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border).setFontSize(18).setBold());

        //row2
        table.addCell(new Cell().add(new Paragraph("Allowances")).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph("Amount")).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph("Deductions")).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph("Amount")).setTextAlignment(TextAlignment.CENTER).setBorder(border1));

        //row 3
        table.addCell(new Cell().add(new Paragraph("BASIC")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(basicEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph("IT")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(itEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        //row 4

        table.addCell(new Cell().add(new Paragraph("DA")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(daEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph("PT")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(ptEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        //row 5

        table.addCell(new Cell().add(new Paragraph("HRA")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(hraEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph("GPF")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(gpfEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        //row 6

        table.addCell(new Cell().add(new Paragraph("TA")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(taEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph("JANSEVA")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(janseEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        //row 7

        table.addCell(new Cell().add(new Paragraph("OTHER")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(salOtherEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph("VidyaSevak")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(vidyaEdt.getText().toString())).setPaddingLeft(6).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        //row 8


        table.addCell(new Cell().add(new Paragraph()).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph()).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph("OTHER ")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(DedotherEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));

//row 13
        table.addCell(new Cell().add(new Paragraph("Total ")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(salTotalEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph("Total ")).setPaddingLeft(6).setBorder(border1));
        table.addCell(new Cell().add(new Paragraph(DedtotalEdt.getText().toString())).setTextAlignment(TextAlignment.CENTER).setBorder(border1));

        //row14
        table.addCell(new Cell(1,2).add(new Paragraph("Net Salary:-Rs. ")).setPaddingLeft(6).setBorder(border1).setFontSize(22));
        table.addCell(new Cell(1,2).add(new Paragraph(payInHandEdt.getText().toString())).setBorder(border1).setFontSize(22).setTextAlignment(TextAlignment.CENTER));

        Paragraph paragraph2 = new Paragraph("Date: - "  + dateEdt.getText().toString()).setBold().setFontSize(22).setMarginTop(25);



        table.setBorder(border1);




        document.add(paragraph);
        table.setFontSize(font);
        table.setBold();
        table1.setMarginBottom(20);

        document.add(table1);
        document.add(table);
        document.add(paragraph2);

        document.close();
        Toast.makeText(this, "pdf Created", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);

    }




}