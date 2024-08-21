package com.example.bsaassistant.document;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bsaassistant.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.ortiz.touchview.TouchImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DocumentMain extends AppCompatActivity {
    Button add,back;
    TouchImageView imageView;
    Bitmap bitmap;
    TextInputEditText name;
    ArrayList<byte[]> imageArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_main);
        add = findViewById(R.id.addDocumentBtn);
        name = findViewById(R.id.DocumentNameTv);
        imageArr = new ArrayList<>();
        back = findViewById(R.id.DocumentSavemain);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentMain.this.finish();

            }
        });

        imageView = findViewById(R.id.DocumentImageVw);

            setView();


        documentDbHelper documentDbHelper = new documentDbHelper(DocumentMain.this);
        Cursor data = documentDbHelper.fetchDoc();
        if(data.getCount() ==  0){

        }else {
            byte[] image = new byte[0];
            while (data.moveToNext()) {
                image = data.getBlob(1);


            }
            imageArr.add(image);
            ;

        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")){
                    Toast.makeText(DocumentMain.this, "Enter Document Name First", Toast.LENGTH_SHORT).show();
                }
                else {
                    ImagePicker.with(DocumentMain.this)
                            .crop()

                            .maxResultSize(1080, 1080)
                            .start();
                }




            }
        });

    }
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();


        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImageURI(uri);
        documentDbHelper documentDbHelper = new documentDbHelper(DocumentMain.this);
        documentDbHelper.addEntry(name.getText().toString(), getBytes(bitmap));




    }public void setView(){
        documentDbHelper documentDbHelper = new documentDbHelper(DocumentMain.this);


        String cName = getIntent().getStringExtra("passName");

        documentDbHelper documentDbHelper2 = new documentDbHelper(DocumentMain.this);
        Cursor data =  documentDbHelper.findDoc(cName);
        if(data.getCount() ==  0){

        }else {
            byte[] image = new byte[0];
            while (data.moveToNext()) {
                image = data.getBlob(1);
                Bitmap cbitmap =  BitmapFactory.decodeByteArray(image, 0, image.length);
                imageView.setImageBitmap(cbitmap);



            }
            name.setText(cName);







    }

}
}
