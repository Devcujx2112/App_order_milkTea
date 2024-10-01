package com.nhom2.appbantrasua.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nhom2.appbantrasua.DAO.DAO_Product;
import com.nhom2.appbantrasua.Entity.Product;
import com.nhom2.appbantrasua.ImageSliderAdapter;
import com.nhom2.appbantrasua.DAL.ProductAdapter;
import com.nhom2.appbantrasua.R;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    SearchView searchView;
    private ImageSliderAdapter imageSliderAdapter;
// run imgae slider
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
// region DAO
    DAO_Product daoProduct = new DAO_Product();
// endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // hiển thị ảnh ở slider
        List<Integer> imageList = Arrays.asList(R.drawable.slider1, R.drawable.slider2, R.drawable.slider3);
        imageSliderAdapter = new ImageSliderAdapter(this, imageList);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == imageList.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // Chuyển trang sau mỗi 3 giây
            }
        };
        handler.postDelayed(runnable, 3000); // Bắt đầu trượt tự động sau 3 giây
        daoProduct.InitLogin(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        anhxa();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Product> products = daoProduct.ShowListProduct();
////        // data tĩnh
        ProductAdapter adapter = new ProductAdapter(products);
        recyclerView.setAdapter(adapter);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return true;
                }
            });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_cart) {
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    //Toast.makeText(MainActivity.this, "Giỏ hàng được chọn", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else if (id == R.id.nav_home) {
                    // Xử lý mục Đồ uống
                    Toast.makeText(MainActivity.this, "Trở về trang chủ", Toast.LENGTH_SHORT).show();
                }else if(id == R.id.nav_history ){
                    Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                    Toast.makeText(MainActivity.this, "Lịch sử đã được chọn", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else if (id == R.id.nav_contact) {
                    Intent intent = new Intent(MainActivity.this, ContacActivity.class);
                    Toast.makeText(MainActivity.this, "Liên hệ được chọn", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else if (id == R.id.nav_account) {
                    // Xử lý mục Tài khoản
                    Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                    Toast.makeText(MainActivity.this, "Tài khoản được chọn", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                return true;
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); // Dừng auto scroll khi activity bị pause
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000); // Tiếp tục auto scroll khi activity resume
    }
    private void anhxa(){
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        recyclerView = findViewById(R.id.recyclerView);
        viewPager.setAdapter(imageSliderAdapter);
        searchView = findViewById(R.id.searchView);
    }

    }


