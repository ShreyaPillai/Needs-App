package com.example.needs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.needs.Data.DatabaseHandler;
import com.example.needs.model.Item;
import com.example.needs.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText reqitem;
    private EditText itemqty;
    private EditText itemcolor;
    private EditText itemsize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        recyclerView = findViewById(R.id.recyclerview);
        databaseHandler = new DatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();

        //get items from db

        itemList = databaseHandler.getallItem();

        for (Item item : itemList) {

            Log.d(TAG, "onCreate: " + item.getItemname());
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createpopupdialog();
            }
        });
    }

    private void createpopupdialog() {

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        reqitem = view.findViewById(R.id.reqItem);
        itemqty = view.findViewById(R.id.ItemQuantity);
        itemcolor = view.findViewById(R.id.ItemColor);
        itemsize = view.findViewById(R.id.ItemSize);
        saveButton = view.findViewById(R.id.SaveButton);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!reqitem.getText().toString().isEmpty()
                        && !itemcolor.getText().toString().isEmpty()
                        && !itemqty.getText().toString().isEmpty()
                        && !itemsize.getText().toString().isEmpty()) {
                    saveItem(v);
                } else {
                    Snackbar.make(v, "Empty field not allowed", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void saveItem(View view) {

        Item item = new Item();

        String newItem = reqitem.getText().toString().trim();
        String newColor = itemcolor.getText().toString().trim();
        int newQty = Integer.parseInt(itemqty.getText().toString().trim());
        String newSize = itemsize.getText().toString().trim();

        item.setItemname(newItem);
        item.setItemQty(newQty);
        item.setItemColor(newColor);
        item.setItemSize(newSize);

        databaseHandler.addItem(item);

        Snackbar.make(view, "Item saved", Snackbar.LENGTH_SHORT)
                .show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //code to be run
                alertDialog.dismiss();
                startActivity(new Intent(ListActivity.this,ListActivity.class));
                finish();

            }
        }, 1200);
    }
}
