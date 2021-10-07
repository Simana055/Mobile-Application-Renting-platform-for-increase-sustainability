package com.example.myapplication_rentme.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication_rentme.CartProductModel;

import java.util.ArrayList;
import java.util.List;


public class CartDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CARTDB";

    public CartDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CartContract.SQL_CREATE_ENTRIES_NOTES);
        } catch (Exception e){
            Log.v("EXceptionn", "Exc: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + CartContract.CARTTABLE);
            onCreate(db);
        } catch (Exception e){
            Log.v("EXceptionn", "Exp: " + e.getMessage());
        }
    }

    public long insertItem(CartProductModel model){
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(CartContract.COLUMN_PRODUCT_QUANTITY, model.getQuantity());
            values.put(CartContract.COLUMN_PRODUCT_ID, model.getProductId());
            values.put(CartContract.COLUMN_PRODUCT_NAME, model.getProductName());
            values.put(CartContract.COLUMN_PRODUCT_IMAGE, model.getProductImage());
            values.put(CartContract.COLUMN_PRODUCT_DISC, model.getProductDiscription());
            values.put(CartContract.COLUMN_PRODUCT_PRICE, model.getProductPrice());
            values.put(CartContract.COLUMN_CATEGORY_NAME, model.getCatName());



//            if (model.isSpecialOffer()){
//                values.put(CartContract.COLUMN_ISSPECIAL, 1);
//            } else {
//                values.put(CartContract.COLUMN_ISSPECIAL, 0);
//            }
//
//            values.put(CartContract.COLUMN_OFFER_PRICE, model.getOfferPrice());

            long id = db.insert(CartContract.CARTTABLE, null, values);
            db.close();

            return id;
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Exceeeeption", e.toString());
            return -1;
        }
    }

    public int updateANote(CartProductModel model){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CartContract.COLUMN_PRODUCT_QUANTITY, model.getQuantity());
        values.put(CartContract.COLUMN_PRODUCT_ID, model.getProductId());
        values.put(CartContract.COLUMN_PRODUCT_NAME, model.getProductName());
        values.put(CartContract.COLUMN_PRODUCT_IMAGE, model.getProductImage());
        values.put(CartContract.COLUMN_PRODUCT_DISC, model.getProductDiscription());
        values.put(CartContract.COLUMN_PRODUCT_PRICE, model.getProductPrice());
        values.put(CartContract.COLUMN_CATEGORY_NAME, model.getCatName());
//        if (model.isSpecialOffer()){
//            values.put(CartContract.COLUMN_ISSPECIAL, 1);
//        } else {
//            values.put(CartContract.COLUMN_ISSPECIAL, 0);
//        }
//
//        values.put(CartContract.COLUMN_OFFER_PRICE, model.getOfferPrice());
        // updating row
        return db.update(CartContract.CARTTABLE, values, CartContract.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getId())});
    }

    public List<CartProductModel> getAllItems(){
        try {
            List<CartProductModel> list = new ArrayList<>();

            String query = "SELECT * FROM " + CartContract.CARTTABLE;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){
                do {
                    CartProductModel model = new CartProductModel();
                    model.setId(cursor.getInt(cursor.getColumnIndex(CartContract.COLUMN_ID)));
                    model.setQuantity(cursor.getInt(cursor.getColumnIndex(CartContract.COLUMN_PRODUCT_QUANTITY)));
                    model.setProductId(cursor.getString(cursor.getColumnIndex(CartContract.COLUMN_PRODUCT_ID)));
                    model.setProductName(cursor.getString(cursor.getColumnIndex(CartContract.COLUMN_PRODUCT_NAME)));
                    model.setProductImage(cursor.getString(cursor.getColumnIndex(CartContract.COLUMN_PRODUCT_IMAGE)));
                    model.setProductDiscription(cursor.getString(cursor.getColumnIndex(CartContract.COLUMN_PRODUCT_DISC)));
                    model.setProductPrice(cursor.getString(cursor.getColumnIndex(CartContract.COLUMN_PRODUCT_PRICE)));
                    model.setCatName(cursor.getString(cursor.getColumnIndex(CartContract.COLUMN_CATEGORY_NAME)));
//                    model.setOfferPrice(cursor.getString(cursor.getColumnIndex(CartContract.COLUMN_OFFER_PRICE)));
//                    if (cursor.getInt(cursor.getColumnIndex(CartContract.COLUMN_ISSPECIAL)) == 0){
//                        model.setSpecialOffer(false);
//                    } else {
//                        model.setSpecialOffer(true);
//                    }

                    list.add(model);
                }while (cursor.moveToNext());
            }
            cursor.close();

            db.close();

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteANote(CartProductModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CartContract.CARTTABLE, CartContract.COLUMN_ID + " = ?",
                new String[]{String.valueOf(model.getId())});
        db.close();
    }
}
