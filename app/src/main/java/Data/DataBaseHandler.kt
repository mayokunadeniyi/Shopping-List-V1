package Data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList
import java.util.Date

import Model.Item
import Utils.Constants

class DataBaseHandler(private val ctx: Context?) : SQLiteOpenHelper(ctx, Constants.DATABASE_NAME, null, Constants.DATAABASE_VERSION) {

    //Getting all items
    //invoke the date format
    //Add each item to item list
    val allItem: List<Item>
        get() {
            val db = this.readableDatabase
            val itemList = ArrayList<Item>()
            val cursor = db.query(Constants.TABLE_NAME, arrayOf(Constants.KEY_ID, Constants.KEY_ITEM_NAME, Constants.KEY_QUANTITY, Constants.KEY_DATE_ADDED), null, null, null, null,
                    Constants.KEY_DATE_ADDED + " DESC")
            if (cursor.moveToFirst()) {
                do {
                    val item = Item()
                    item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))))
                    item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME)))
                    item.setItemQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QUANTITY)))

                    val dateFormat = java.text.DateFormat.getDateInstance()
                    val formated_date = dateFormat.format(Date(cursor.getLong(
                            cursor.getColumnIndex(Constants.KEY_DATE_ADDED))).time)
                    item.setItemDateCreated(formated_date)
                    itemList.add(item)

                } while (cursor.moveToNext())
            }

            return itemList
        }

    //get all Items counted
    val allItemsCount: Int
        get() {
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null)
            return cursor.count
        }

    override fun onCreate(db: SQLiteDatabase) {

        val CREATE_ITEM_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "(" +
                Constants.KEY_ID + " INTEGER PRIMARY KEY," + Constants.KEY_ITEM_NAME +
                " TEXT," + Constants.KEY_QUANTITY + " TEXT," + Constants.KEY_DATE_ADDED +
                " LONG);"
        db.execSQL(CREATE_ITEM_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME)

        //Create a new DB
        onCreate(db)

    }

    /**
     * Perform CRUD Operations: CREATE, READ, UPDATE AND DELETE ITEM IN DB
     *
     */

    fun addNewItem(item: Item) {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(Constants.KEY_ITEM_NAME, item.itemName)
        contentValues.put(Constants.KEY_QUANTITY, item.itemQuantity)
        contentValues.put(Constants.KEY_DATE_ADDED, java.lang.System.currentTimeMillis())

        db.insert(Constants.TABLE_NAME, null, contentValues)
        db.close()
    }

    //Getting a single Item
    fun getItem(id: Int): Item {
        val db = this.readableDatabase

        val cursor = db.query(Constants.TABLE_NAME, arrayOf(Constants.KEY_ID, Constants.KEY_ITEM_NAME, Constants.KEY_QUANTITY, Constants.KEY_DATE_ADDED), Constants.KEY_ID + "=?",
                arrayOf(id.toString()), null, null, null, null)

        //Create item instance
        val item = Item()
        if (cursor != null) {
            cursor.moveToFirst()
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))))
            item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME)))
            item.setItemQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QUANTITY)))

            //invoke the date format

            val dateFormat = java.text.DateFormat.getDateInstance()
            val formated_date = dateFormat.format(Date(cursor.getLong(
                    cursor.getColumnIndex(Constants.KEY_DATE_ADDED))).time)
            item.setItemDateCreated(formated_date)

        }
        return item
    }

    //update an Item
    fun updateItem(item: Item): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constants.KEY_ITEM_NAME, item.itemName)
        contentValues.put(Constants.KEY_QUANTITY, item.itemQuantity)
        contentValues.put(Constants.KEY_DATE_ADDED, java.lang.System.currentTimeMillis())

        return db.update(Constants.TABLE_NAME, contentValues, Constants.KEY_ID + "=?",
                arrayOf(item.id.toString()))
    }

    //delete an Item
    fun deleteItem(id: Int) {
        val db = this.writableDatabase
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " =?", arrayOf(id.toString()))
        db.close()
    }


}
