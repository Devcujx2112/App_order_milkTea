package com.nhom2.appbantrasua.DAL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhom2.appbantrasua.CartManager;
import com.nhom2.appbantrasua.Entity.Product;
import com.nhom2.appbantrasua.GUI.AccountActivity;
import com.nhom2.appbantrasua.GUI.CartActivity;
import com.nhom2.appbantrasua.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    public static List<Product> cartItems;
    int quatity = 1;

    CartActivity _cartActivity;

    public CartAdapter(){}

    public CartAdapter(Context context, List<Product> cartItems, CartActivity cartActivity) {
        this.context = context;
        _cartActivity = cartActivity;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(cartItems.size() - 1 - position);


        if (isBase64(product.getImageResource())){
            byte[] decodedBytes = Base64.decode(product.getImageResource(), Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            holder.productImageView.setImageBitmap(decodedBitmap);
        }else {
            int imageResId = context.getResources().getIdentifier(product.getImageResource(), "drawable", context.getPackageName());
            holder.productImageView.setImageResource(imageResId);
        }



        holder.productNameTextView.setText(product.getName());
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = numberFormat.format(product.getPrice());
        holder.productPriceTextView.setText(formattedPrice + " VND");
        holder.productQualityTextView.setText(String.valueOf(product.getQuality()));

//Start Button increase and decrase Cart
        holder.increaseQuantityButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quatity = Integer.parseInt(holder.productQualityTextView.getText().toString());
                quatity++;
                holder.productQualityTextView.setText(String.valueOf(quatity));
                updateQualityProductItem(holder.getAdapterPosition(), AccountActivity.getInstance().account.getUserName(), context, quatity);
                _cartActivity.TotalAmount();
            }
        });


        holder.decreaseQuantityButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quatity = Integer.parseInt(holder.productQualityTextView.getText().toString());
                if(quatity > 0){
                    quatity--;
                    updateQualityProductItem(holder.getAdapterPosition(), AccountActivity.getInstance().account.getUserName(), context, quatity);
                }

                if(quatity == 0){
                    Toast.makeText(context, "Sản phẩm này đã được xóa", Toast.LENGTH_SHORT).show();
                    removeProductFromCart(holder.getAdapterPosition(), AccountActivity.getInstance().account.getUserName(), context);
                    _cartActivity.ReLoadRycyclerView(CartManager.getInstance().getCartItems(context));
                }
                holder.productQualityTextView.setText(String.valueOf(quatity));
                _cartActivity.TotalAmount();
            }
        });
    }
//End

    public boolean isBase64(String s) {
        // Kiểm tra chuỗi có khớp với định dạng Base64 không

        s = s.replace("\n", "");

        if (s == null || s.isEmpty()) {
            return false;
        }
        String base64Pattern = "^[A-Za-z0-9+/]*={0,2}$"; // Biểu thức chính quy cho Base64
        return s.matches(base64Pattern) && (s.length() % 4 == 0); // Kiểm tra cả chiều dài
    }

    public void updateQualityProductItem(int productId, String username, Context context,int quality){
        List<Product> cartItems = loadCartItems(username, context);
        cartItems.get(productId).setQuality(quality);
        saveCartItems(cartItems, username, context);
    }


    public void removeProductFromCart(int productId, String username, Context context) {
        // Bước 1: Tải danh sách sản phẩm hiện tại của tài khoản
        List<Product> cartItems = loadCartItems(username, context);
        if (cartItems != null) {
            cartItems.remove(productId);
            // Bước 3: Lưu lại danh sách đã cập nhật
            saveCartItems(cartItems, username, context);
        }
    }

    public void addProductToCart(Product newProduct, String username, Context context) {
        // Bước 1: Tải danh sách sản phẩm hiện tại của tài khoản
        List<Product> cartItems = loadCartItems(username, context);

        // Bước 2: Nếu danh sách chưa tồn tại, khởi tạo danh sách mới
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        // Bước 3: Thêm sản phẩm mới vào danh sách
        cartItems.add(newProduct);

        // Bước 4: Lưu lại danh sách đã cập nhật
        saveCartItems(cartItems, username, context);
    }

    public List<Product> loadCartItems(String username, Context context) {
        List<Product> cartItems = null;
        String filename = "cartItems_" + username + ".json";  // Đọc file theo tài khoản

        try (FileInputStream fis = context.openFileInput(filename);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            Gson gson = new Gson();
            Type productListType = new TypeToken<List<Product>>(){}.getType();
            cartItems = gson.fromJson(br, productListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public void saveCartItems(List<Product> cartItems, String username, Context context) {
        Gson gson = new Gson();
        String json = gson.toJson(cartItems);

        String filename = "cartItems_" + username + ".json";

        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearCartItems(String username, Context context) {
        String filename = "cartItems_" + username + ".json";

        File file = new File(context.getFilesDir(), filename);
        if (file.exists()) {
            if (file.delete()) {
                // File deleted successfully
                Log.d("Cart", "Cart items file deleted successfully.");
            } else {
                // Failed to delete file
                Log.e("Cart", "Failed to delete cart items file.");
            }
        } else {
            // File does not exist
            Log.d("Cart", "Cart items file does not exist.");
        }
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView productQualityTextView;
        ImageView decreaseQuantityButtonCart, increaseQuantityButtonCart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.cartProductImageView);
            productNameTextView = itemView.findViewById(R.id.cartProductNameTextView);
            productPriceTextView = itemView.findViewById(R.id.cartProductPriceTextView);
            productQualityTextView = itemView.findViewById(R.id.quantityTextView);
            decreaseQuantityButtonCart = itemView.findViewById(R.id.decreaseQuantityButtonCart);
            increaseQuantityButtonCart = itemView.findViewById(R.id.increaseQuantityButtonCart);
        }
    }
}
