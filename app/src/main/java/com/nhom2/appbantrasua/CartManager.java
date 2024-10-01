package com.nhom2.appbantrasua;


import android.content.Context;

import com.nhom2.appbantrasua.DAL.CartAdapter;
import com.nhom2.appbantrasua.Entity.Product;
import com.nhom2.appbantrasua.GUI.AccountActivity;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cart;
    CartAdapter cartAdapter = new CartAdapter();
    Context context;
    private CartManager() {
        cart = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void ClearList(){
        cart.clear();
    }

    public List<Product> getCartItems(Context context) {
        if(cartAdapter.loadCartItems(AccountActivity.getInstance().account.getUserName(), context) != null)
            cart = cartAdapter.loadCartItems(AccountActivity.getInstance().account.getUserName(), context);
        return cart;
    }

    public void LoadAndSaveData(Product product, Context context){
        cart.add(product);
        cartAdapter.saveCartItems(cart, AccountActivity.getInstance().account.getUserName(), context);
    }
}
