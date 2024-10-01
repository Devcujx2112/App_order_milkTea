package com.nhom2.appbantrasua.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import com.nhom2.appbantrasua.DatabaseHelper;
import com.nhom2.appbantrasua.Entity.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DAL_Product implements Serializable {

    private static DatabaseHelper database;
    private Context context;

    public void InitLogin(Context context) {
        database = new DatabaseHelper(context);
        this.context = context;
    }

    public List<Product> ShowListProduct(){
        List<Product> list = new ArrayList<>();
        Cursor cursor = database.GetData("SELECT * FROM Product");
        try {
            if(cursor.getCount() > 0){

                while (cursor.moveToNext()){
                    list.add(
                            new Product(cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getDouble(3),
                                    cursor.getString(4))
                    );
                }

            }
        } finally {
            cursor.close(); // Đảm bảo đóng con trỏ
        }

        Log.e("LISTTTTTTTT",  String.valueOf(list.size()));

        return list;
    }
    public void InsertProduct(String id, String nameproduct, String description, String price, String imgprd) {
        SQLiteDatabase data = database.getWritableDatabase();
        String query = "INSERT INTO Product (id, nameproduct, description, price, imgprd) VALUES (?, ?, ?, ?, ?)";
        SQLiteStatement sql = data.compileStatement(query);
        try {
            sql.bindString(1, id);
            sql.bindString(2, nameproduct);
            sql.bindString(3, description);
            sql.bindString(4, price);
            sql.bindString(5, imgprd);
            sql.executeInsert();
        } catch (Exception e) {
            Log.e("Database Error", "Thêm sản phẩm thất bại: " + e.getMessage());
        } finally {
            sql.close();
        }
    }

    public boolean deleteProduct(String id) {
        SQLiteDatabase data = database.getWritableDatabase();
        String query = "DELETE FROM Product WHERE id = ?";
        SQLiteStatement sql = data.compileStatement(query);
        int rowWrongs = 0;
        Log.e("datasqlinsert","123");
        try {
            sql.bindString(1, id);
            rowWrongs = sql.executeUpdateDelete();
        } catch (Exception e) {
            Log.d("DeleteSanPham", "error: " + e.getMessage());
        } finally {
            sql.close();
        }
        return rowWrongs > 0;
    }

    public void UpdateProduct(String id, String name, String moTa, String giaCa, String img) {
        SQLiteDatabase data = database.getWritableDatabase();
        String query = "UPDATE Product SET nameproduct = ?, description = ?, price = ?, imgprd = ? WHERE id = ?";
        SQLiteStatement sql = data.compileStatement(query);

        try {
            sql.bindString(1, name);
            sql.bindString(2, moTa);
            sql.bindDouble(3, Double.parseDouble(giaCa));
            sql.bindString(4, img);
            sql.bindString(5, id);

            sql.executeUpdateDelete();
        } catch (Exception e) {
            Log.d("UpdateProduct", "error: " + e.getMessage());
        } finally {
            sql.close();
        }
    }
}
