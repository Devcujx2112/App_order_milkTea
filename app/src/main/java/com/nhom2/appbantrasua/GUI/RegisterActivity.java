package com.nhom2.appbantrasua.GUI;

import android.content.Intent;
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
import com.nhom2.appbantrasua.DAO.DAO_LoginRegister;
import com.nhom2.appbantrasua.DatabaseHelper;
import com.nhom2.appbantrasua.R;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RegisterActivity extends AppCompatActivity {
    EditText txtUserName, fullName, email, password, otpInput, confirmPassword;

    Button btnRegister, btnCancel, btnVerifyOTP, btnResendOTP;
    private ExecutorService executorService;
    LinearLayout registerLayout, otpLayout;
    String generatedOTP;

    String _username;
    String _pass;
    String _email;
    String _fullName;
    public DatabaseHelper database;

//region DAO
    DAO_LoginRegister daoLoginRegister = new DAO_LoginRegister();

//endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        daoLoginRegister.InitLogin(this);
        executorService = Executors.newSingleThreadExecutor();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _username = txtUserName.getText().toString().trim();
                _pass = password.getText().toString().trim();
                _email = email.getText().toString().trim();
                _fullName = fullName.getText().toString().trim();
                // Validate fields first

                if (_username.isEmpty() || _pass.isEmpty() || _email.isEmpty() || _fullName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!_pass.equals(confirmPassword.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendEmail(_email);
            }
        });

        // Verify OTP button
        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputOTP = otpInput.getText().toString();
                if (inputOTP.equals(generatedOTP)) {
                    Toast.makeText(RegisterActivity.this, "Xác thực thành công!", Toast.LENGTH_SHORT).show();
                    txtUserName.setText("");
                    password.setText("");
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    daoLoginRegister.InsertAccount(_username, _pass, _fullName, _email, "0");
                    startActivity(intent);

                    // Proceed with registration flow
                } else {
                    Toast.makeText(RegisterActivity.this, "Mã OTP không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(_email);
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void sendEmail(String _email) {
        // Sử dụng ExecutorService để thực hiện việc gửi email trên background thread
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                GmailSender sender = new GmailSender("nhompro88@gmail.com", "picz syny ykix mkmh");
                generatedOTP = generateVerificationCode();
                sender.sendMail(_email, "Mã xác thực", "Mã xác thực của bạn là: " + generatedOTP);
                // Show success message and switch to OTP layout
                runOnUiThread(() -> {
                    Toast.makeText(RegisterActivity.this, "Đã gửi mã xác thực đến email!", Toast.LENGTH_SHORT).show();
                    registerLayout.setVisibility(View.GONE);  // Hide registration form
                    otpLayout.setVisibility(View.VISIBLE);  // Show OTP form
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    Toast.makeText(RegisterActivity.this, "Gửi email thất bại!", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private String generateVerificationCode() {
        int randomCode = (int) (Math.random() * 90000) + 10000; // 5-digit OTP
        return String.valueOf(randomCode);
    }

    void AnhXa() {
        // Register
        txtUserName = findViewById(R.id.edittextuser);
        fullName = findViewById(R.id.edittextfullname);
        email = findViewById(R.id.edittextemail);
        password = findViewById(R.id.edittextpass);
        confirmPassword = findViewById(R.id.edittextconfirmpass);
        btnRegister = findViewById(R.id.buttondangky);
        btnCancel = findViewById(R.id.buttoncancel);


        // OTP
        otpInput = findViewById(R.id.edittextotp);
        btnVerifyOTP = findViewById(R.id.buttonverifyotp);
        btnResendOTP = findViewById(R.id.buttonresendotp);
        registerLayout = findViewById(R.id.registerotp);
        otpLayout = findViewById(R.id.otp);
    }
}
