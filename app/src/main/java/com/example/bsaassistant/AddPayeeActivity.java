package com.example.bsaassistant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bsaassistant.databases.PayeeDbHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AddPayeeActivity extends AppCompatActivity {
    private boolean isUpdating = false;
    EditText payeeEdt;
    Button save;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payee);
        payeeEdt = findViewById(R.id.addPayeeEditText);
        save = findViewById(R.id.addPayeeBtn);
        listView = findViewById(R.id.addPayeeLv);
        dataList = new ArrayList<>();
        payeeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!isUpdating) {
                    String text = editable.toString();
                    String capitalizedText = capitalizeFirstLetter(text);
                    if (!capitalizedText.equals(text)) {
                        isUpdating = true;
                        payeeEdt.setText(capitalizedText);
                        payeeEdt.setSelection(capitalizedText.length());
                        isUpdating = false;
                    }
                }
            }
        });
        PayeeDbHelper payeeDbHelper  =new PayeeDbHelper(this);
        //setView Function

        setView();

        //save btn coding
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!payeeEdt.getText().toString().equals("")){
                    payeeDbHelper.addPayee(payeeEdt.getText().toString());
                    dataList.clear();
                    payeeEdt.setText("");
                    setView();

                }
                else {
                    Toast.makeText(AddPayeeActivity.this, "Enter Payee ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //delete coding
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) listView.getItemAtPosition(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddPayeeActivity.this);
                builder.setTitle(" DELETE ?")
                        .setMessage("Do You Want To Delete " +   selectedItem)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                payeeDbHelper.deletePayee(selectedItem);
                                Toast.makeText(AddPayeeActivity.this, selectedItem + " Deleted SuccessFully", Toast.LENGTH_SHORT).show();
                                setView();
                                // Optional: Perform any action on positive button click
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // Optional: Perform any action on negative button click
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

                setView();


            }
        });






    }
    private void scrollToLastItem(ArrayAdapter<String> adapter) {
        listView.post(new Runnable() {
            @Override
            public void run() {
                listView.smoothScrollToPosition(adapter.getCount() - 1);
            }
        });
    }
    private String capitalizeFirstLetter(String text) {
        if (text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }


    private void setView() {
        dataList.clear();
        PayeeDbHelper payeeDbHelper = new PayeeDbHelper(AddPayeeActivity.this);
        Cursor data = payeeDbHelper.getPayee();
        if (data.getCount() == 0){
            dataList.clear();
        }else {
            while (data.moveToNext()) {
                dataList.add(data.getString(0));


            }
            }
        Collections.sort(dataList);
        adapter = new ArrayAdapter<>(this,R.layout.list_item_bsa,dataList);
        scrollToLastItem(adapter);
        listView.setAdapter(adapter);
    }
}