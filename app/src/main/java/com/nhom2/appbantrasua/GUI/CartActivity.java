package com.nhom2.appbantrasua.GUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom2.appbantrasua.DAL.CartAdapter;
import com.nhom2.appbantrasua.CartManager;
import com.nhom2.appbantrasua.Entity.Product;
import com.nhom2.appbantrasua.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private TextView totalPriceTextView;
    private CartAdapter cartAdapter;
    private Button payButton;
    LinearLayout payment, cartLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        payment = findViewById(R.id.payment);
        cartLayout = findViewById(R.id.layoutCart);
        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        TotalAmount();
        List<Product> cartItems = CartManager.getInstance().getCartItems(this);
        ReLoadRycyclerView(cartItems);
    }

    public void ReLoadRycyclerView(List<Product> cartItems){
        cartAdapter = new CartAdapter(this, cartItems, this);
        cartRecyclerView.setAdapter(cartAdapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void TotalAmount(){
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        payButton = findViewById(R.id.payButton);
        List<Product> cartItems = CartManager.getInstance().getCartItems(this);
        // Giả sử getPrice() trả về giá sản phẩm
        double totalPrice = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuality()).sum();

        // Format total price with thousands separator
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = numberFormat.format(totalPrice);
        totalPriceTextView.setText("Tổng tiền: " + formattedPrice + " VND");

        payButton.setOnClickListener(v -> {
            // Handle payment process

            if(totalPrice > 0){
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                intent.putExtra("totalPrice", formattedPrice);
                startActivity(intent);
            }else
                Toast.makeText(this, "vui lòng chọn đồ uống mới được thanh toán", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Trở về trang chủ (MainActivity)
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
