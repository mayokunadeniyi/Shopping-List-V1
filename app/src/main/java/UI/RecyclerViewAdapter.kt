package UI

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.example.myapplication.Activities.MainActivity
import com.example.myapplication.R

import Data.DataBaseHandler
import Model.Item

class RecyclerViewAdapter(private val context: Context, private val itemList: MutableList<Item>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var alertDialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null
    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_row, viewGroup, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(viewHolder: RecyclerViewAdapter.ViewHolder, i: Int) {

        val (_, itemName, itemQuantity, itemDateCreated) = itemList[i]
        viewHolder.itemName.text = itemName
        viewHolder.itemQuantity.text = itemQuantity
        viewHolder.dateCreated.text = itemDateCreated


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View, ctx: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var itemName: TextView
        var itemQuantity: TextView
        var dateCreated: TextView
        var deleteItem: Button
        var editItem: Button
        var id: Int = 0

        init {
            var ctx = ctx
            ctx = context


            itemName = itemView.findViewById<View>(R.id.name) as TextView
            itemQuantity = itemView.findViewById<View>(R.id.quantity) as TextView
            dateCreated = itemView.findViewById<View>(R.id.dataAdded) as TextView
            deleteItem = itemView.findViewById<View>(R.id.deleteBtn) as Button
            editItem = itemView.findViewById<View>(R.id.editBtn) as Button

            //Set the onclick listeners
            deleteItem.setOnClickListener(this)
            editItem.setOnClickListener(this)


        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.deleteBtn -> {
                    val position = adapterPosition
                    val (id1) = itemList[position]
                    deleteItem(id1)
                }


                R.id.editBtn -> {
                    val position1 = adapterPosition
                    val item1 = itemList[position1]
                    editItem(item1)
                }
            }

        }

        //Delete an Item method
        private fun deleteItem(id: Int) {

            //create alert dialogue

            alertDialogBuilder = AlertDialog.Builder(context)
            inflater = LayoutInflater.from(context)
            val view = inflater!!.inflate(R.layout.confirmation_dialogue, null)

            val yesButton = view.findViewById<View>(R.id.yes_button) as Button
            val noButton = view.findViewById<View>(R.id.no_button) as Button

            alertDialogBuilder!!.setView(view)
            dialog = alertDialogBuilder!!.create()
            dialog!!.show()

            //On click listeners for the buttons
            yesButton.setOnClickListener { v ->
                val db = DataBaseHandler(v.context)
                db.deleteItem(id)
                itemList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition, itemList.size)
                dialog!!.dismiss()

                //Check if Database is empty
                if (db.allItemsCount == 0) {
                    context.startActivity(Intent(v.context, MainActivity::class.java))

                }
            }

            noButton.setOnClickListener { dialog!!.dismiss() }


        }

        private fun editItem(item: Item) {
            alertDialogBuilder = AlertDialog.Builder(context)
            val view1 = LayoutInflater.from(context).inflate(R.layout.popup, null)

            val titleText = view1.findViewById<View>(R.id.title) as TextView
            val itemName = view1.findViewById<View>(R.id.itemNameID) as EditText
            val itemQuantity = view1.findViewById<View>(R.id.itemQuantityID) as EditText
            val saveButton = view1.findViewById<View>(R.id.save_item_button) as Button

            titleText.text = "Update Item"
            itemName.setText(item.itemName)
            itemQuantity.setText(item.itemQuantity)

            alertDialogBuilder!!.setView(view1)
            dialog = alertDialogBuilder!!.create()
            dialog!!.show()

            saveButton.setOnClickListener { v ->
                val db = DataBaseHandler(context)
                item.setItemName(itemName.text.toString())
                item.setItemQuantity(itemQuantity.text.toString())

                if (!itemName.text.toString().isEmpty() && !itemQuantity.text.toString().isEmpty()) {
                    db.updateItem(item)
                    notifyItemChanged(adapterPosition, item)
                    dialog!!.dismiss()
                } else if (itemName.text.toString().isEmpty() && !itemQuantity.text.toString().isEmpty()) {

                    Toast.makeText(v.context, "Item Name is Empty", Toast.LENGTH_SHORT).show()

                } else if (!itemName.text.toString().isEmpty() && itemQuantity.text.toString().isEmpty()) {

                    Toast.makeText(v.context, "Item Quantity is Empty", Toast.LENGTH_SHORT).show()

                } else if (itemName.text.toString().isEmpty() && itemQuantity.text.toString().isEmpty()) {

                    Toast.makeText(v.context, "Item Name and Quantity are Empty", Toast.LENGTH_SHORT).show()

                }
            }


        }

    }
}
