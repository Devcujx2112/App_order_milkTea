package com.nhom2.appbantrasua.DAO;

import android.content.Context;
import com.nhom2.appbantrasua.DAL.DAL_Product;
import com.nhom2.appbantrasua.Entity.Product;
import java.io.Serializable;
import java.util.List;

public class DAO_Product implements Serializable {
    DAL_Product dal = new DAL_Product();

    public void InitLogin(Context context){
        dal.InitLogin(context);
    }

    public List<Product> ShowListProduct(){
//        Log.e("PRODUCTddddddd", String.valueOf(dal.ShowListProduct().size()));
        return dal.ShowListProduct();
    }
    public void Insert_Product(String id, String nameproduct, String description, String price, String imgprd){
        dal.InsertProduct(id,nameproduct,description,price,imgprd);
    }
    public boolean DeLeTeProduct(String id){
        return dal.deleteProduct(id);
    }
    public void UpdateProductByID(String id, String name, String moTa, String giaCa, String img){
        dal.UpdateProduct(id, name,moTa,giaCa,img);
    }
}
