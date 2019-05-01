package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Data.DataBaseHandler;
import Model.Item;
import UI.RecyclerViewAdapter;

public class ListActivity extends AppCompatActivity {

    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private DataBaseHandler db;
    private List<Item> itemList;
    private List<Item> list;
    private AlertDialog.Builder alertDialogueBuilder;
    private AlertDialog dialog;
    private EditText itemName;
    private EditText itemQuantity;
    private Button saveItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createNewItem();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerViewID);
        db = new DataBaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        list = new ArrayList<>();

        //Get all items from DataBase
        itemList = db.getAllItem();

        for (Item I : itemList) {

            Item item = new Item();
            item.setItemName(I.getItemName());
            item.setItemQuantity(I.getItemQuantity());
            item.setItemDateCreated(I.getItemDateCreated());
            item.setId(I.getId());

            list.add(item);

        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, list);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();


    }

    private void createNewItem() {
        alertDialogueBuilder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.popup, null);

        final Item item = new Item();


        itemName = (EditText) view.findViewById(R.id.itemNameID);
        itemQuantity = (EditText) view.findViewById(R.id.itemQuantityID);
        saveItemButton = (Button) view.findViewById(R.id.save_item_button);

        alertDialogueBuilder.setView(view);
        dialog = alertDialogueBuilder.create();
        dialog.show();

        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemName.getText().toString().isEmpty() && !itemQuantity.getText().toString().isEmpty()) {


                    saveItemTODB(v);

                } else if (itemName.getText().toString().isEmpty() && !itemQuantity.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Item Name is Empty", Toast.LENGTH_SHORT).show();

                } else if (!itemName.getText().toString().isEmpty() && itemQuantity.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Item Quantity is Empty", Toast.LENGTH_SHORT).show();

                } else if (itemName.getText().toString().isEmpty() && itemQuantity.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Item Name and Quantity are Empty", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void saveItemTODB(View v) {
        Item item = new Item();
        item.setItemName(itemName.getText().toString());
        item.setItemQuantity(itemQuantity.getText().toString());

        db.addNewItem(item);
        Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(getIntent());
            }
        }, 1);
    }

}
