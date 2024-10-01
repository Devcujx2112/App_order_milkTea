package com.nhom2.appbantrasua.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhom2.appbantrasua.DatabaseHelper;
import com.nhom2.appbantrasua.Entity.History;
import com.nhom2.appbantrasua.Entity.Product;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DAL_History implements Serializable {
    public static DatabaseHelper databaseHelper;
    private Context context;

    // Table and columns
    private static final String TABLE_HISTORY = "History";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "Phone";
    private static final String COLUMN_ADDRESS = "Address";
    private static final String COLUMN_LIST_PRODUCT = "listProduct";
    private static final String COLUMN_TOTAL_AMOUNT = "totalAmount";
    private static final String COLUMN_USERNAME = "username";






    public void InitLogin(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public void addHistory(String username, String name, String phone, String address, List<Product> listProduct, String totalAmount) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_ADDRESS, address);

        // Chuyển đổi List<Product> thành JSON
        Gson gson = new Gson();
        String jsonListProduct = gson.toJson(listProduct);
        values.put(COLUMN_LIST_PRODUCT, jsonListProduct);

        values.put(COLUMN_TOTAL_AMOUNT, totalAmount);
        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }


    public List<History> loadHistoryByUsername(String username) {
        List<History> historyList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = db.query(TABLE_HISTORY,
                null,
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>() {}.getType();

            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS));
                String listProductJson = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LIST_PRODUCT));
                String totalAmount = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_AMOUNT));

                // Convert JSON string back to list of products
                List<Product> listProduct = gson.fromJson(listProductJson, type);

                // Add each history to the list
                historyList.add(new History(username, name, phone, address, listProduct, totalAmount));

            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return historyList;
    }


    public List<History> LoadAllHistory() {
        List<History> historyList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor = databaseHelper.GetData("SELECT * FROM History");

        if (cursor != null && cursor.moveToFirst()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>() {
            }.getType();

            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS));
                String listProductJson = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LIST_PRODUCT));
                String totalAmount = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_AMOUNT));

                // Convert JSON string back to list of products
                List<Product> listProduct = gson.fromJson(listProductJson, type);

                // Add each history to the list
                historyList.add(new History(name, phone, address, listProduct, totalAmount));

            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return historyList;
    }
}
