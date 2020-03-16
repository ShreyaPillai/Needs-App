package com.example.needs.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.needs.model.Item;
import com.example.needs.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ITEM_TABLE = "CREATE TABLE " + Constants.TABLE_NAME +"("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.KEY_ITEM +" TEXT, "
                + Constants.KEY_ITEM_QTY + " INTEGER, "
                + Constants.KEY_ITEM_COLOR + " TEXT, "
                + Constants.KEY_ITEM_SIZE + " TEXT, "
                + Constants.KEY_DATE + " LONG); ";

        db.execSQL(CREATE_ITEM_TABLE); //CREATING A TABLE

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_NAME);
        onCreate(db);

    }

    //CRUD operations

    //addItems

    public void addItem(Item item){

            SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.KEY_ITEM,item.getItemname());
        values.put(Constants.KEY_ITEM_QTY,item.getItemQty());
        values.put(Constants.KEY_ITEM_COLOR,item.getItemColor());
        values.put(Constants.KEY_ITEM_SIZE,item.getItemSize());
        values.put(Constants.KEY_DATE,java.lang.System.currentTimeMillis());//timestamp of the system

        //Insert the row
        db.insert(Constants.TABLE_NAME,null,values);

        Log.d("DBHandler", "added Item: ");
        db.close();
    }

    //Get a item

    public Item getItem(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new  String[]{Constants.KEY_ID,
                        Constants.KEY_ITEM,
                        Constants.KEY_ITEM_QTY,
                        Constants.KEY_ITEM_COLOR,
                        Constants.KEY_ITEM_SIZE,
                        Constants.KEY_DATE},
                Constants.KEY_ID + "=?",
                new  String[] {
                        String.valueOf(id)}
                        ,null,null,null,null);

                if(cursor!=null)
                    cursor.moveToFirst();

                Item item = new Item();
        if (cursor != null) {
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
            item.setItemname(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM)));
            item.setItemQty(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_QTY)));
            item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_COLOR)));
            item.setItemSize(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)));

            //convert timestamp to something reading

            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE)))
            .getTime());

            item.setDateitemadded(formattedDate);


        }
            return  item;
    }

    //get all items

    public List<Item> getallItem(){


        SQLiteDatabase db = this.getReadableDatabase();
        List<Item> itemList = new ArrayList<>();

      // String selectAll = " SELECT * FROM " + Constants.TABLE_NAME;

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new  String[]{Constants.KEY_ID,
                        Constants.KEY_ITEM,
                        Constants.KEY_ITEM_QTY,
                        Constants.KEY_ITEM_COLOR,
                        Constants.KEY_ITEM_SIZE,
                        Constants.KEY_DATE},
                null,null,null,null,
                  Constants.KEY_DATE + " DESC "  );

        if(cursor.moveToFirst())
        {

            do{
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setItemname(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM)));
                item.setItemQty(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_QTY)));
                item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_COLOR)));
                item.setItemSize(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE)))
                        .getTime());

                item.setDateitemadded(formattedDate);

                itemList.add(item);//ADDED TO ARRAY LIST

            }while(cursor.moveToNext());
        }

        return itemList;
    }
        //todo: add updateitem

    public int updateItem(Item item){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //Item item = getItem(id);

        values.put(Constants.KEY_ITEM,item.getItemname());
        values.put(Constants.KEY_ITEM_QTY,item.getItemQty());
        values.put(Constants.KEY_ITEM_COLOR,item.getItemColor());
        values.put(Constants.KEY_ITEM_SIZE,item.getItemSize());
        values.put(Constants.KEY_DATE,java.lang.System.currentTimeMillis());//timestamp of the system

        //Update row
        return  db.update(Constants.TABLE_NAME,values,
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())});

    }
        //todo: add deleteitem

    public void deleteItem(int id){

        SQLiteDatabase db =this.getWritableDatabase();


        db.delete(Constants.TABLE_NAME,
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();
    }
        //todo: getitem count

    public int getItemCount(){

        String countQuery = " SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery,null);

        return  cursor.getCount();
    }

}
