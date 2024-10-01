package com.nhom2.appbantrasua.DAO;

import android.content.Context;
import com.nhom2.appbantrasua.DAL.DAL_History;
import com.nhom2.appbantrasua.Entity.History;
import com.nhom2.appbantrasua.Entity.Product;
import java.util.List;

public class DAO_History {
    DAL_History dal = new DAL_History();

    public void InitLogin(Context context){
        dal.InitLogin(context);
    }

    public List<History> loadHistoryByUsername(String username) {
        return  dal.loadHistoryByUsername(username);
    }


    public void addHistory(String username, String name, String phone, String address, List<Product> listProduct, String totalAmount){
        dal.addHistory(username, name, phone, address, listProduct, totalAmount);
    }

    public List<History> LoadAllHistory(){
        return dal.LoadAllHistory();
    }
}
