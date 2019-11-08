package com.example.myapplication.Activities

import android.content.Intent
import android.os.Bundle

import com.google.android.material.floatingactionbutton.FloatingActionButton

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.myapplication.R

import Data.DataBaseHandler
import Model.Item

class MainActivity : AppCompatActivity() {

    private var builder: AlertDialog.Builder? = null
    private var alertDialog: AlertDialog? = null
    private var itemName: EditText? = null
    private var itemQuantity: EditText? = null
    private var saveButton: Button? = null
    private var db: DataBaseHandler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        db = DataBaseHandler(this)

        skipPopUp()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { createPopupDialog() }
    }


    fun createPopupDialog() {

        builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.popup, null)
        itemName = view.findViewById<View>(R.id.itemNameID) as EditText
        itemQuantity = view.findViewById<View>(R.id.itemQuantityID) as EditText
        saveButton = view.findViewById<View>(R.id.save_item_button) as Button
        builder!!.setView(view)
        alertDialog = builder!!.create()
        alertDialog!!.show()

        //On click listener or Save button;

        saveButton!!.setOnClickListener { v ->
            if (!itemName!!.text.toString().isEmpty() && !itemQuantity!!.text.toString().isEmpty()) {
                saveItemToDB(v)
            } else if (itemName!!.text.toString().isEmpty() && !itemQuantity!!.text.toString().isEmpty()) {

                Toast.makeText(applicationContext, "Item Name is Empty", Toast.LENGTH_SHORT).show()

            } else if (!itemName!!.text.toString().isEmpty() && itemQuantity!!.text.toString().isEmpty()) {

                Toast.makeText(applicationContext, "Item Quantity is Empty", Toast.LENGTH_SHORT).show()

            } else if (itemName!!.text.toString().isEmpty() && itemQuantity!!.text.toString().isEmpty()) {

                Toast.makeText(applicationContext, "Item Name and Quantity are Empty", Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun saveItemToDB(v: View) {

        val item = Item()
        item.setItemName(itemName!!.text.toString())
        item.setItemQuantity(itemQuantity!!.text.toString())

        db!!.addNewItem(item)

        Toast.makeText(applicationContext, "Item Saved", Toast.LENGTH_SHORT).show()
        alertDialog!!.dismiss()
        startActivity(Intent(applicationContext, ListActivity::class.java))
        finish()


    }

    private fun skipPopUp() {
        if (db!!.allItemsCount > 0) {
            startActivity(Intent(this@MainActivity, ListActivity::class.java))
            finish()
        }
    }
}
