package com.example.needs;

import android.content.Intent;
import android.os.Bundle;

import com.example.needs.Data.DatabaseHandler;
import com.example.needs.model.Item;
import com.example.needs.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText reqitem;
    private EditText itemqty;
    private EditText itemcolor;
    private EditText itemsize;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);

        BypassActivity();

        List<Item> items = databaseHandler.getallItem();

        for(Item item : items){

            Log.d("Main ", "onCreate: " + item.getDateitemadded());
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createPopupDialog();

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void createPopupDialog() {

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        reqitem = view.findViewById(R.id.reqItem);
        itemqty = view.findViewById(R.id.ItemQuantity);
        itemcolor = view.findViewById(R.id.ItemColor);
        itemsize = view.findViewById(R.id.ItemSize);
        saveButton = view.findViewById(R.id.SaveButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!reqitem.getText().toString().isEmpty()
                && !itemcolor.getText().toString().isEmpty()
                && !itemqty.getText().toString().isEmpty()
                && !itemsize.getText().toString().isEmpty())
                {
                    saveItem(v);
                }
                else
                {
                    Snackbar.make(v,"Empty field not allowed",Snackbar.LENGTH_SHORT).show();
                }

            }
        });


        builder.setView(view);
        dialog = builder.create();  //creating our dialog objet
        dialog.show(); //important

    }

    private void BypassActivity(){

        if(databaseHandler.getItemCount()>0){

            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }
    }
    private void saveItem(View view) {


        //Todo:save each item to db

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

        Snackbar.make(view,"Item saved",Snackbar.LENGTH_SHORT)
                .show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //code to be run
                dialog.dismiss();
                //Todo:move to next screen - details screen
                startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        },1200);//1sec

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
