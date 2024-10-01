package com.nhom2.appbantrasua.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.nhom2.appbantrasua.DAO.DAO_LoginRegister;
import com.nhom2.appbantrasua.Entity.LoginRegister;
import com.nhom2.appbantrasua.R;

import java.util.concurrent.ExecutorService;


public class LoginActivity extends AppCompatActivity
{
    Button btnNextPageRegister, btnDangNhap;
    EditText userNameAccount, passwordAccout;
    private ExecutorService executorService;
    DAO_LoginRegister daoRegister = new DAO_LoginRegister();
    AccountActivity accountActivity;
    public LoginRegister account;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        daoRegister.InitLogin(this);
        btnNextPageRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAccount(userNameAccount.getText().toString().trim(), passwordAccout.getText().toString().trim());
            }
        });
    }
    String saveAccount = "userName_password login";
    public void ResumLoginAccount(){
        SharedPreferences sharedPreferences = getSharedPreferences(saveAccount, MODE_PRIVATE);
        String userName = sharedPreferences.getString("UserName", "");
        String password = sharedPreferences.getString("Password", "");

        if (!userName.isEmpty() && !password.isEmpty()){
            CheckAccount(userName, password);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        ResumLoginAccount();
    }
    private void CheckAccount(String userName, String password){
        if(userName.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
        }
        try{
            if(daoRegister.checkAccount(userName, password) != null){
                if (daoRegister.checkAdmin(userName) == 1) {
                    account = daoRegister.checkAccount(userName, password);
                    AccountActivity.getInstance().account = this.account;
                    homeAdmin();
                    finish();
                } else {
                    account = daoRegister.checkAccount(userName, password);
                    AccountActivity.getInstance().account = this.account;

                    SharedPreferences sharedPreferences = getSharedPreferences(saveAccount, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UserName", userName);
                    editor.putString("Password", password);
                    editor.commit();
                    goToMainActivity();
                    finish();
                }
            }else{
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }


    }
    private void homeAdmin(){
        Intent intent = new Intent(this,Home_admin.class);
        startActivity(intent);
    }
     private void goToMainActivity (){
         Intent intent = new Intent(this, MainActivity.class);
         startActivity(intent);
    }

    void AnhXa(){
        //Login
        userNameAccount = findViewById(R.id.edittextuser);
        passwordAccout = findViewById(R.id.edittextpass);

        btnNextPageRegister = findViewById(R.id.buttonchuyentrangdangky);
        btnDangNhap = findViewById(R.id.buttondangnhap);
    }
}
