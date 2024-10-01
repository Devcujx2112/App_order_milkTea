package com.nhom2.appbantrasua.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.nhom2.appbantrasua.CartManager;
import com.nhom2.appbantrasua.DAO.DAO_LoginRegister;
import com.nhom2.appbantrasua.Entity.LoginRegister;
import com.nhom2.appbantrasua.R;

public class AccountActivity  extends AppCompatActivity {
    private static AccountActivity instance;
    public static LoginRegister account = null;

    EditText userName, password, gmail, fullName, newPassword;
    Button btnBackHome, btnLogout, btnChangePassword, btnBackAccount, btnConfirmPassword;
    LinearLayout infoAccount, changePasswordAccount;
    DAO_LoginRegister daoRegister = new DAO_LoginRegister();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.account), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Init();
        daoRegister.InitLogin(this);
        if(account != null){
            userName.setText(account.getUserName());
            fullName.setText(account.getName());
            gmail.setText(account.getOTP());
        }


        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoAccount.setVisibility(View.GONE);  // Hide registration form
                changePasswordAccount.setVisibility(View.VISIBLE);
                password.setText("");
                newPassword.setText("");
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                account = null;
                CartManager.getInstance().ClearList();
                SharedPreferences sharedPreferences = getSharedPreferences("userName_password login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("UserName", "");
                editor.putString("Password", "");
                editor.commit();
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        btnBackAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePasswordAccount.setVisibility(View.GONE);  // Hide registration form
                infoAccount.setVisibility(View.VISIBLE);
            }
        });

        btnConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(account.getPassword().equals(password.getText().toString())){
                    Toast.makeText(AccountActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    daoRegister.ChangePasswordAccount(account.getUserName(), newPassword.getText().toString());
                    account.setPassword(newPassword.getText().toString());
                    changePasswordAccount.setVisibility(View.GONE);  // Hide registration form
                    infoAccount.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(AccountActivity.this, "Mật khẩu cũ không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static AccountActivity getInstance(){
        if (instance == null) {
            instance = new AccountActivity();
        }
        return instance;
    }

    void Init(){
        userName = findViewById(R.id.edittextuseraccount);
        fullName = findViewById(R.id.edittextfullnameaccount);
        gmail = findViewById(R.id.edittextemailaccount);
        password = findViewById(R.id.edittextpassaccount);
        newPassword = findViewById(R.id.edittextnewpassaccount);

        btnBackHome = findViewById(R.id.buttoncancelaccount);
        btnLogout = findViewById(R.id.buttonlogoutaccount);
        btnChangePassword = findViewById(R.id.buttonthaydoiaccount);
        infoAccount = findViewById(R.id.infoaccount);
        changePasswordAccount = findViewById(R.id.changepasswordaccount);

//linerlayout change password
        btnBackAccount = findViewById(R.id.buttonbackaccount);
        btnConfirmPassword = findViewById(R.id.buttonconfirmchangepasswordaccount);
    }
}
