package com.example.shoppinglist;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME="ShoppingDB";
    private static final String TABLE_NAME="Item_Table";
    //Table Columns
    private static final String KEY_ID="id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SIZE = "size";
    private static final String KEY_QTY = "quantity";
    private static final String KEY_DETAILS = "details";
    private static final String KEY_URGENT = "urgent";
    private static final String KEY_BOUGHT = "bought";
    private static final String KEY_DATE = "date";


    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE "  + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT NOT NULL, "
                + KEY_DETAILS + " TEXT, "
                + KEY_SIZE + " TEXT NOT NULL, "
                + KEY_QTY + " INTEGER DEFAULT 0, "
                + KEY_URGENT + " INTEGER DEFAULT 0, "
                + KEY_BOUGHT + " INTEGER DEFAULT 0, "
                + KEY_DATE + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Drop existed table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //Recreate table
        onCreate(sqLiteDatabase);
    }


    // Add item
    public long addItem(Item item){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, item.getName());
        contentValues.put(KEY_DETAILS, item.getDetails());
        contentValues.put(KEY_SIZE, item.getSize());
        contentValues.put(KEY_QTY, item.getQuantity());
        contentValues.put(KEY_URGENT, item.getUrgent());
        contentValues.put(KEY_BOUGHT, item.getBought());
        contentValues.put(KEY_DATE, item.getDate());
        return sqLiteDatabase.insert(TABLE_NAME,null, contentValues);
    }

    //Get DB number of rows
    public long rowNums(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_NAME);
    }

    //Update an item
    public long updateItem(Item item){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, item.getName());
        contentValues.put(KEY_DETAILS, item.getDetails());
        contentValues.put(KEY_SIZE, item.getSize());
        contentValues.put(KEY_QTY, item.getQuantity());
        contentValues.put(KEY_URGENT, item.getUrgent());
        contentValues.put(KEY_BOUGHT, item.getBought());
        contentValues.put(KEY_DATE, item.getDate());
        return sqLiteDatabase.update(TABLE_NAME, contentValues,KEY_ID + "=" + item.getId(),null);
    }

    //Remove an item
    public long removeItem(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,KEY_ID + "=" + id,null);
    }

    // Get all item
    public ArrayList<Item> getAllItems(){
        ArrayList<Item> allItems = new ArrayList<>();

        String selectAll = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectAll, null);

        if(cursor.moveToFirst())
        {
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String details = cursor.getString(2);
                String size = cursor.getString(3);
                int quantity = cursor.getInt(4);
                int urgent = cursor.getInt(5);
                boolean isUrgent = false;
                if(urgent==1){
                    isUrgent = true;
                }
                int bought = cursor.getInt(6);
                boolean isBought = false;
                if(bought==1){
                    isBought = true;
                }
                String date = cursor.getString(7);
                Item item = new Item(name, size, details, quantity, isUrgent);
                item.setId(id);
                item.setBought(isBought);
                item.setDate(date);

                allItems.add(item);
            } while (cursor.moveToNext());
        }
        return allItems;
    }
}
