package com.example.myapplication.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
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

        for (Item I : itemList){

            Item item = new Item();
            item.setItemName(I.getItemName());
            item.setItemQuantity(I.getItemQuantity());
            item.setItemDateCreated(I.getItemDateCreated());

            list.add(item);

        }

        recyclerViewAdapter = new RecyclerViewAdapter(this,list);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();



    }

}
