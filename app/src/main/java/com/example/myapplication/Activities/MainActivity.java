package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
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
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DataBaseHandler(this);

        skipPopUp();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createPopupDialog();
            }
        });
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

        Toast.makeText(getApplicationContext(),"Item Saved",Toast.LENGTH_SHORT).show();
        alertDialog.dismiss();
        startActivity(new Intent(getApplicationContext(),ListActivity.class));
        finish();


    }

    private void skipPopUp(){
        if (db.getAllItemsCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }
}
