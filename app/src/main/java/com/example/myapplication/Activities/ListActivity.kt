package com.example.myapplication.Activities

import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.myapplication.R

import java.util.ArrayList

import Data.DataBaseHandler
import Model.Item
import UI.RecyclerViewAdapter

class ListActivity : AppCompatActivity() {

    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var db: DataBaseHandler? = null
    private var itemList: List<Item>? = null
    private var list: MutableList<Item>? = null
    private var alertDialogueBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null
    private var itemName: EditText? = null
    private var itemQuantity: EditText? = null
    private var saveItemButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { createNewItem() }

        recyclerView = findViewById<View>(R.id.RecyclerViewID) as RecyclerView
        db = DataBaseHandler(this)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        itemList = ArrayList()
        list = ArrayList()

        //Get all items from DataBase
        itemList = db!!.allItem

        for ((id, itemName1, itemQuantity1, itemDateCreated) in itemList!!) {

            val item = Item()
            item.setItemName(itemName1)
            item.setItemQuantity(itemQuantity1)
            item.setItemDateCreated(itemDateCreated)
            item.setId(id)

            list!!.add(item)

        }

        recyclerViewAdapter = RecyclerViewAdapter(this, list)
        recyclerView!!.adapter = recyclerViewAdapter
        recyclerViewAdapter!!.notifyDataSetChanged()


    }

    private fun createNewItem() {
        alertDialogueBuilder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.popup, null)

        val (id, itemName1, itemQuantity1, itemDateCreated) = Item()


        itemName = view.findViewById<View>(R.id.itemNameID) as EditText
        itemQuantity = view.findViewById<View>(R.id.itemQuantityID) as EditText
        saveItemButton = view.findViewById<View>(R.id.save_item_button) as Button

        alertDialogueBuilder!!.setView(view)
        dialog = alertDialogueBuilder!!.create()
        dialog!!.show()

        saveItemButton!!.setOnClickListener { v ->
            if (!itemName!!.text.toString().isEmpty() && !itemQuantity!!.text.toString().isEmpty()) {


                saveItemTODB(v)

            } else if (itemName!!.text.toString().isEmpty() && !itemQuantity!!.text.toString().isEmpty()) {

                Toast.makeText(applicationContext, "Item Name is Empty", Toast.LENGTH_SHORT).show()

            } else if (!itemName!!.text.toString().isEmpty() && itemQuantity!!.text.toString().isEmpty()) {

                Toast.makeText(applicationContext, "Item Quantity is Empty", Toast.LENGTH_SHORT).show()

            } else if (itemName!!.text.toString().isEmpty() && itemQuantity!!.text.toString().isEmpty()) {

                Toast.makeText(applicationContext, "Item Name and Quantity are Empty", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun saveItemTODB(v: View) {
        val item = Item()
        item.setItemName(itemName!!.text.toString())
        item.setItemQuantity(itemQuantity!!.text.toString())

        db!!.addNewItem(item)
        Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show()

        Handler().postDelayed({
            dialog!!.dismiss()
            startActivity(intent)
        }, 1)
    }

}
