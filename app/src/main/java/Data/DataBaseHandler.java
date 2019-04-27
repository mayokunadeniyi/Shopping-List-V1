package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Item;
import Utils.Constants;

public class DataBaseHandler extends SQLiteOpenHelper {

    private Context ctx;

    public DataBaseHandler(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATAABASE_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ITEM_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "(" +
                Constants.KEY_ID + " INTEGER PRIMARY KEY," + Constants.KEY_ITEM_NAME +
                " TEXT," + Constants.KEY_QUANTITY + " TEXT," + Constants.KEY_DATE_ADDED +
                " LONG);";
        db.execSQL(CREATE_ITEM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        //Create a new DB
        onCreate(db);

    }

    /**
     * Perform CRUD Operations: CREATE, READ, UPDATE AND DELETE ITEM IN DB
     *
     */

    public void addNewItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.KEY_ITEM_NAME,item.getItemName());
        contentValues.put(Constants.KEY_QUANTITY,item.getItemQuantity());
        contentValues.put(Constants.KEY_DATE_ADDED,java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME,null,contentValues);
        db.close();
    }

    //Getting a single Item
    public Item getItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,Constants.KEY_ITEM_NAME,
        Constants.KEY_QUANTITY,Constants.KEY_DATE_ADDED},Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)},null,null,null,null);

        //Create item instance
        Item item = new Item();
        if (cursor != null){
            cursor.moveToFirst();
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME)));
            item.setItemQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QUANTITY)));

            //invoke the date format

            java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            String formated_date = dateFormat.format(new Date(cursor.getLong(
                    cursor.getColumnIndex(Constants.KEY_DATE_ADDED))).getTime());
            item.setItemDateCreated(formated_date);

        }
        return item;
    }

    //Getting all items
    public List<Item> getAllItem(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Item> itemList = new ArrayList<>();
        Cursor cursor = db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,Constants.KEY_ITEM_NAME,
                Constants.KEY_QUANTITY,Constants.KEY_DATE_ADDED},null,null,null,null,
                Constants.KEY_DATE_ADDED + " DESC");
        if (cursor.moveToFirst()){
            do {
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME)));
                item.setItemQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QUANTITY)));

                //invoke the date format

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formated_date = dateFormat.format(new Date(cursor.getLong(
                        cursor.getColumnIndex(Constants.KEY_DATE_ADDED))).getTime());
                item.setItemDateCreated(formated_date);

                //Add each item to item list
                itemList.add(item);

            }while (cursor.moveToNext());
        }

        return itemList;
    }

    //update an Item
    public int updateItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.KEY_ITEM_NAME,item.getItemName());
        contentValues.put(Constants.KEY_QUANTITY,item.getItemQuantity());
        contentValues.put(Constants.KEY_DATE_ADDED,java.lang.System.currentTimeMillis());

       return db.update(Constants.TABLE_NAME,contentValues,Constants.KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())});
    }

    //delete an Item
    public void deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    //get all Items counted
    public int getAllItemsCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null);
        return cursor.getCount();
    }


}
