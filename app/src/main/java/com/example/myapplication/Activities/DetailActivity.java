package com.example.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class DetailActivity extends AppCompatActivity {

    private TextView item_name;
    private TextView item_quantity;
    private TextView item_date;
    private Button delete_button;
    private Button edit_button;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        item_name = (TextView) findViewById(R.id.itemNAME);
        item_quantity = (TextView) findViewById(R.id.quantityDETAIL);
        item_date = (TextView) findViewById(R.id.dateAddedDETAIL);
        delete_button = (Button) findViewById(R.id.deleteButtonDet);
        edit_button = (Button) findViewById(R.id.editButtonDet);

        //Set their values
        bundle = getIntent().getExtras();
        item_name.setText(bundle.getString("itemName"));
        item_quantity.setText(bundle.getString("itemQuantity"));
        item_date.setText(bundle.getString("itemDate"));





    }
}
