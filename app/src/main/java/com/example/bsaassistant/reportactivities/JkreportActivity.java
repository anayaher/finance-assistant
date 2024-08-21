package com.example.bsaassistant.reportactivities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.example.bsaassistant.adapters.JkRepAdapter;
import com.example.bsaassistant.databases.JamaKharachDbHelper;
import com.example.bsaassistant.datamodels.jkModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class JkreportActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    TextView totalIncomeTv, totalExpenseTv;
    Button createPdfBtn;
   ArrayList<jkModel>jkModels;

    String fromDate = "2010-01-01";
    String toDate = "2040-01-01";
    String payeeName = "ALL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jk_report);
        recyclerView = findViewById(R.id.repJkRecyclerView);
        totalIncomeTv = findViewById(R.id.totalIncomeJkRep);
        totalExpenseTv = findViewById(R.id.totalExpenseJkRep);
        createPdfBtn = findViewById(R.id.repJkCreatePdfBtn);

        recyclerView.setLayoutManager(new LinearLayoutManager(JkreportActivity.this));
        fromDate = getIntent().getStringExtra("passFromDateJk");
        toDate = getIntent().getStringExtra("passToDateJk");


        JamaKharachDbHelper newIncomeExpDbHelper = new JamaKharachDbHelper(JkreportActivity.this);
        payeeName = getIntent().getStringExtra("passPayeeJk");

        if (payeeName.equals("ALL") && fromDate.equals("START") && toDate.equals("END")) {
            jkModels = newIncomeExpDbHelper.selectBetweenDates("2000-01-01","2050-01-01");
            JkRepAdapter jkRepAdapter = new JkRepAdapter(JkreportActivity.this,jkModels);
            recyclerView.setAdapter(jkRepAdapter);



                getTotals();



        } else if (payeeName.equals("ALL") && !fromDate.equals("START")) {
            jkModels = newIncomeExpDbHelper.selectBetweenDates(fromDate,toDate);
            JkRepAdapter jkRepAdapter = new JkRepAdapter(JkreportActivity.this,jkModels);
            recyclerView.setAdapter(jkRepAdapter);





            getTotals();



        } else {
            jkModels = newIncomeExpDbHelper.selectBetweenDates(fromDate,toDate,payeeName);
            JkRepAdapter jkRepAdapter = new JkRepAdapter(JkreportActivity.this,jkModels);
            recyclerView.setAdapter(jkRepAdapter);





            getTotals();


            }



        createPdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (payeeName.equals("ALL")) {
                        createAllPdf();
                    } else {
                        createPdf();
                    }
                } catch (FileNotFoundException e) {
                    Toast.makeText(JkreportActivity.this, "Cant Print", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });

    }


    private void createPdf() throws FileNotFoundException {

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/BSA/JamaKharcha/";
        File file = new File(pdfPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        DateTimeFormatter formatter = null;
        DateTimeFormatter formatter2 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (fromDate.equals("START")){
                fromDate = "1/1/2010";
                toDate = "1/1/2040";
            }
            String StartDate = LocalDate.parse(fromDate, formatter).format(formatter2);
            String EndDate = LocalDate.parse(toDate, formatter).format(formatter2);


            String filePath = pdfPath + "/" + payeeName + " " + StartDate + " " + EndDate + "-" + ".pdf";
            File file1 = new File(filePath);
            OutputStream outputStream = new FileOutputStream(file1);
            PdfWriter pdfWriter = new PdfWriter(file1);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            Paragraph paragraph = new Paragraph("JAMA KHARCHA REPORT");


            paragraph.setPaddingBottom(20);
            paragraph.setFontSize(20);
            paragraph.setTextAlignment(TextAlignment.CENTER).setBold().setUnderline();
            com.itextpdf.kernel.colors.Color myColor = new DeviceRgb(235, 212, 255);
            Color black = new DeviceRgb(0, 0, 0);
            Color orange = new DeviceRgb(208, 247, 229);
            Color darkOrange = new DeviceRgb(252, 228, 210);
            Color red = new DeviceRgb(196, 0, 23);
            Color white = new DeviceRgb(255, 255, 255);


            float columnWidt[] = {40, 100,40, 70, 70, 112, 141};
            Table table = new Table(columnWidt);
            Border border = new GrooveBorder(new DeviceRgb(0, 0, 0), 3);


            //row 1


            Paragraph paragraph1 = new Paragraph("Payee: " + payeeName).setTextAlignment(TextAlignment.LEFT).setBold();
            Paragraph paragraph2 = new Paragraph("Date : " + fromDate + " To " + toDate).setTextAlignment(TextAlignment.LEFT).setBold();

            table.addCell(new Cell(1, 7).add(paragraph1).add(paragraph2));
            //table.addCell(new Cell().add(new Paragraph()));
            // table.addCell(new Cell().add(new Paragraph()));
            //table.addCell(new Cell().add(new Paragraph()));
            //table.addCell(new Cell().add(new Paragraph()));


            //  table.addCell(new Cell().add(new Paragraph()));


            table.addCell(new Cell().add(new Paragraph("SR")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Date")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Type")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Amount")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Payee")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Head")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Details")).setBackgroundColor(myColor).setBorder(border));


            for (int i = 0; i < jkModels.size(); i++) {
                if (i % 2 == 0) {
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getSr()))).setBorder(border).setBackgroundColor(orange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getDate()))).setBorder(border).setBackgroundColor(orange));

                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getType())).setBorder(border).setBackgroundColor(orange)));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getAmt()))).setBorder(border).setBackgroundColor(orange));
                    table.addCell(new Cell().add(new Paragraph(jkModels.get(i).getPayee()).setBorder(border).setBackgroundColor(orange)));
                    table.addCell(new Cell().add(new Paragraph(jkModels.get(i).getHead()).setBorder(border).setBackgroundColor(orange)));
                    table.addCell(new Cell().add(new Paragraph(jkModels.get(i).getDetails()).setBorder(border).setBackgroundColor(orange)));


                } else {
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getSr()))).setBorder(border).setBackgroundColor(orange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getDate()))).setBorder(border).setBackgroundColor(orange));

                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getType())).setBorder(border).setBackgroundColor(orange)));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getAmt()))).setBorder(border).setBackgroundColor(orange));
                    table.addCell(new Cell().add(new Paragraph(jkModels.get(i).getPayee()).setBorder(border).setBackgroundColor(orange)));
                    table.addCell(new Cell().add(new Paragraph(jkModels.get(i).getHead()).setBorder(border).setBackgroundColor(orange)));
                    table.addCell(new Cell().add(new Paragraph(jkModels.get(i).getDetails()).setBorder(border).setBackgroundColor(orange)));


                }


            }


            table.addCell(new Cell(1, 2).add(new Paragraph("Total Amount")).setBold().setFontSize(15).setBackgroundColor(red).setBorder(border).setFontColor(white));
            //  table.addCell(new Cell().add(new Paragraph()));
            //table.addCell(new Cell().add(new Paragraph()));
            int sum = 0;
            int sumE = 0;


            for (int i = 0; i < jkModels.size(); i++) {
                if (jkModels.get(i).getType() == 1)
                {
                    sum += jkModels.get(i).getAmt();
                }
                else
                {
                    sumE += jkModels.get(i).getAmt();
                }

                }




            String balance = String.valueOf(sum - sumE);
            table.addCell(new Cell().add(new Paragraph(String.valueOf(sum))).setBold().setFontSize(15).setBackgroundColor(red).setBorder(border).setFontColor(white));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(sumE))).setBold().setFontSize(15).setBackgroundColor(red).setBorder(border).setFontColor(white));
            table.addCell(new Cell().add(new Paragraph(balance)).setBackgroundColor(red).setBold().setFontSize(15).setBorder(border).setFontColor(white));
            table.addCell(new Cell().add(new Paragraph()).setBackgroundColor(red).setBorder(border));


            document.add(paragraph);
            document.add(table);
            document.close();
            Toast.makeText(this, "pdf Created", Toast.LENGTH_SHORT).show();


        }
    }

    private void createAllPdf() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/BSA/JamaKharcha/";
        File file = new File(pdfPath);

        if (!file.exists()) {
            file.mkdirs();
        }
        DateTimeFormatter formatter = null;
        DateTimeFormatter formatter2 = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (fromDate.equals("START")){
                fromDate = "1/1/2010";
                toDate = "1/1/2040";
            }
            String StartDate = LocalDate.parse(fromDate, formatter).format(formatter2);
            String EndDate = LocalDate.parse(toDate, formatter).format(formatter2);


            String filePath = pdfPath + "/" + payeeName + " " + StartDate + " " + EndDate + " " + "-" + ".pdf";
            File file1 = new File(filePath);
            OutputStream outputStream = new FileOutputStream(file1);
            PdfWriter pdfWriter = new PdfWriter(file1);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            Paragraph paragraph = new Paragraph("JAMA KHARCHA REPORT");


            paragraph.setPaddingBottom(20);
            paragraph.setFontSize(20);
            paragraph.setTextAlignment(TextAlignment.CENTER).setBold().setUnderline();
            com.itextpdf.kernel.colors.Color myColor = new DeviceRgb(235, 212, 255);
            Color black = new DeviceRgb(0, 0, 0);
            Color orange = new DeviceRgb(208, 247, 229);
            Color darkOrange = new DeviceRgb(252, 228, 210);
            Color red = new DeviceRgb(196, 0, 23);
            Color white = new DeviceRgb(255, 255, 255);


            float[] columnWidth = {40, 80, 60, 40, 40, 112, 141};
            Table table = new Table(columnWidth);
            Border border = new GrooveBorder(new DeviceRgb(0, 0, 0), 3);


            //row 1


            Paragraph paragraph1 = new Paragraph("Payee: " + payeeName).setTextAlignment(TextAlignment.LEFT).setBold();
            Paragraph paragraph2 = new Paragraph("Date : " + fromDate + " To " + toDate).setTextAlignment(TextAlignment.LEFT).setBold();

            table.addCell(new Cell(1, 7).add(paragraph1).add(paragraph2));
            //table.addCell(new Cell().add(new Paragraph()));
            // table.addCell(new Cell().add(new Paragraph()));
            //table.addCell(new Cell().add(new Paragraph()));
            //table.addCell(new Cell().add(new Paragraph()));


            //  table.addCell(new Cell().add(new Paragraph()));

            table.addCell(new Cell().add(new Paragraph("SR")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Date")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Income")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Expense")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Payee")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Head")).setBackgroundColor(myColor).setBorder(border));
            table.addCell(new Cell().add(new Paragraph("Details")).setBackgroundColor(myColor).setBorder(border));



            for (int i = 0; i < jkModels.size(); i++) {


                if (i % 2 == 0) {
                    String income = null;
                    String expense = null;
                    if (jkModels.get(i).getType()==1){
                        income= String.valueOf(jkModels.get(i).getAmt());
                        expense = "0";
                    }
                    else {
                        income = "0";
                        expense = String.valueOf(jkModels.get(i).getAmt());
                    }

                    String date = jkModels.get(i).getDate();

                    DateTimeFormatter recievedDateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter desiredFormat = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
                    LocalDate date1 = LocalDate.parse(date,recievedDateFormat);
                    String output = date1.format(desiredFormat);



                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getSr()))).setBorder(border).setBackgroundColor(orange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(output))).setBorder(border).setBackgroundColor(orange));

                    table.addCell(new Cell().add(new Paragraph(String.valueOf(income))).setBorder(border).setBackgroundColor(orange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(expense))).setBorder(border).setBackgroundColor(orange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getPayee()))).setBorder(border).setBackgroundColor(orange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getHead()))).setBorder(border).setBackgroundColor(orange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getDetails()))).setBorder(border).setBackgroundColor(orange));


                } else {
                    String income = null;
                    String expense = null;
                    if (jkModels.get(i).getType()==1){
                        income= String.valueOf(jkModels.get(i).getAmt());
                        expense = "0";
                    }
                    else {
                        income = "0";
                        expense = String.valueOf(jkModels.get(i).getAmt());
                    }

                    String date = jkModels.get(i).getDate();

                    DateTimeFormatter recievedDateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter desiredFormat = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
                    LocalDate date1 = LocalDate.parse(date,recievedDateFormat);
                    String output = date1.format(desiredFormat);



                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getSr()))).setBorder(border).setBackgroundColor(darkOrange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(output))).setBorder(border).setBackgroundColor(darkOrange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(income))).setBorder(border).setBackgroundColor(darkOrange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(expense))).setBorder(border).setBackgroundColor(darkOrange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getPayee()))).setBorder(border).setBackgroundColor(darkOrange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getHead()))).setBorder(border).setBackgroundColor(darkOrange));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(jkModels.get(i).getDetails()))).setBorder(border).setBackgroundColor(darkOrange));


                }


            }


            table.addCell(new Cell(1, 2).add(new Paragraph("Total Amount")).setBold().setFontSize(15).setBackgroundColor(red).setBorder(border).setFontColor(white));
            //  table.addCell(new Cell().add(new Paragraph()));
            //table.addCell(new Cell().add(new Paragraph()));
            int sum = 0;
            int sumE = 0;


            for (int i = 0; i < jkModels.size(); i++) {
                if (jkModels.get(i).getType() == 1)
                {
                    sum += jkModels.get(i).getAmt();
                }
                else
                {
                    sumE += jkModels.get(i).getAmt();
                }

            }
            String balance = String.valueOf(sum - sumE);

            table.addCell(new Cell().add(new Paragraph(String.valueOf(sum))).setBold().setFontSize(15).setBackgroundColor(red).setBorder(border).setFontColor(white));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(sumE))).setBold().setFontSize(15).setBackgroundColor(red).setBorder(border).setFontColor(white));
            table.addCell(new Cell().add(new Paragraph(balance)).setBold().setFontSize(15).setBackgroundColor(red).setBorder(border).setFontColor(white));
            table.addCell(new Cell().add(new Paragraph()).setBackgroundColor(red).setBorder(border));


            document.add(paragraph);
            document.add(table);
            document.close();

            Toast.makeText(this, "pdf Created", Toast.LENGTH_SHORT).show();


        }



    }
    public void getTotals () {
        int sum = 0;
        int sumE = 0;

        for (int i = 0; i < jkModels.size(); i++) {
            if (jkModels.get(i).getType() == 1)
            {
                sum += jkModels.get(i).getAmt();
            }
            else
            {
                sumE += jkModels.get(i).getAmt();
            }

        }
        totalIncomeTv.setText(String.valueOf(sum));
        totalExpenseTv.setText(String.valueOf(sumE));

    }

}