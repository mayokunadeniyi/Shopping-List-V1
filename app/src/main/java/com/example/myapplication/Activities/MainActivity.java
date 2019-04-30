package com.example.myapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

import Data.DataBaseHandler;
import Model.Item;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private EditText itemName;
    private EditText itemQuantity;
    private Button saveButton;
    private DataBaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DataBaseHandler(this);

        skipPopUp();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                createPopupDialog();
            }
        });
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

    public void createPopupDialog(){

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        itemName = (EditText) view.findViewById(R.id.itemNameID);
        itemQuantity = (EditText) view.findViewById(R.id.itemQuantityID);
        saveButton = (Button) view.findViewById(R.id.save_item_button);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        //On click listener or Save button;

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!itemName.getText().toString().isEmpty() && !itemQuantity.getText().toString().isEmpty()) {
                    saveItemToDB(v);
                }else if (itemName.getText().toString().isEmpty() && !itemQuantity.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(),"Item Name is Empty",Toast.LENGTH_SHORT).show();

                }else if (!itemName.getText().toString().isEmpty() && itemQuantity.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(),"Item Quantity is Empty",Toast.LENGTH_SHORT).show();

                }else if (itemName.getText().toString().isEmpty() && itemQuantity.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(),"Item Name and Quantity are Empty",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void saveItemToDB(View v) {

        Item item = new Item();
        item.setItemName(itemName.getText().toString());
        item.setItemQuantity(itemQuantity.getText().toString());

        db.addNewItem(item);
        Snackbar.make(v,"Item Saved!",Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               alertDialog.dismiss();
               startActivity(new Intent(MainActivity.this,ListActivity.class));
            }
        },1200);


    }

    private void skipPopUp(){
        if (db.getAllItemsCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }
}
