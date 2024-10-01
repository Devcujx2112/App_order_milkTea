package com.nhom2.appbantrasua.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom2.appbantrasua.DAL.HistoryAdapter;
import com.nhom2.appbantrasua.DAO.DAO_History;
import com.nhom2.appbantrasua.DAO.DAO_LoginRegister;
import com.nhom2.appbantrasua.Entity.History;
import com.nhom2.appbantrasua.R;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView historyRecyclerView;
    private HistoryAdapter _historyAdapter;
    DAO_History daoHistory = new DAO_History();
    DAO_LoginRegister daoRegister = new DAO_LoginRegister();
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.history), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        daoHistory.InitLogin(this);
        daoRegister.InitLogin(this);

        Toolbar toolbar = findViewById(R.id.toolbarhistory);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        historyRecyclerView = findViewById(R.id.recycleViewhistory);
        TextView sumHistory = findViewById(R.id.sumhistory);
        TextView textorderhistory = findViewById(R.id.textorderhistory);

        if(daoRegister.checkAdmin(AccountActivity.getInstance().account.getUserName()) == 1){
            List<History> historyItems = daoHistory.LoadAllHistory();
            int sum = 0;
            for (History history :historyItems) {
                sum += Integer.parseInt(history.getTotalAmount().replace(".", ""));
            }
            textorderhistory.setText("Thống kê");
            sumHistory.setText("Tổng tiền : " + sum);

            ReLoadRycyclerView(historyItems);
        }else{
            List<History> historyItems = daoHistory.loadHistoryByUsername(AccountActivity.getInstance().account.getUserName());
            ReLoadRycyclerView(historyItems);
        }
    }

    public void ReLoadRycyclerView(List<History> historyAdapter){
        _historyAdapter = new HistoryAdapter(this, historyAdapter);
        historyRecyclerView.setAdapter(_historyAdapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Intent intent;
                if(daoRegister.checkAdmin(AccountActivity.getInstance().account.getUserName()) == 1){
                    intent = new Intent(HistoryActivity.this, Home_admin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    intent = new Intent(HistoryActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
