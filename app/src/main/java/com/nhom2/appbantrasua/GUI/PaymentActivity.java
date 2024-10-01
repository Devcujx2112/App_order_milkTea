package com.nhom2.appbantrasua.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.nhom2.appbantrasua.CartManager;
import com.nhom2.appbantrasua.DAL.CartAdapter;
import com.nhom2.appbantrasua.DAO.DAO_History;
import com.nhom2.appbantrasua.Entity.History;
import com.nhom2.appbantrasua.Entity.Product;
import com.nhom2.appbantrasua.HistoryManager;
import com.nhom2.appbantrasua.R;
import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    TextView fullname, price;
    EditText address, phoneNumber;

    Button paymentCancel, buttonPaymentConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Init();

        DAO_History daoHistory = new DAO_History();
        daoHistory.InitLogin(this);
        fullname.setText(AccountActivity.getInstance().account.getName());
        String _price = getIntent().getStringExtra("totalPrice");
        price.setText(_price + " VND");

        buttonPaymentConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(address.getText().toString().isEmpty() || phoneNumber.getText().toString().isEmpty()){
                    Toast.makeText(PaymentActivity.this, "Phải điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PaymentActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                    CartAdapter cart = new CartAdapter();
                    List<Product> list = new ArrayList<>(cart.cartItems);
                    List<History> historyList = HistoryManager.getInstance().getListHistory();
                    History history = new History();
                    history.setListProduct(list);
                    history.setName(AccountActivity.getInstance().account.getName());
                    history.setAddress(address.getText().toString());
                    history.setPhone(phoneNumber.getText().toString());
                    history.setTotalAmount(_price);
                    historyList.add(history);
                    daoHistory.addHistory(AccountActivity.getInstance().account.getUserName(),
                            history.getName(),
                            history.getPhone(), history.getAddress(),history.getListProduct(), history.getTotalAmount());

                    HistoryManager.getInstance().setListHistory(daoHistory.loadHistoryByUsername(AccountActivity.getInstance().account.getUserName()));
                    cart.clearCartItems(AccountActivity.getInstance().account.getUserName(), getBaseContext());
                    CartManager.getInstance().ClearList();
                    Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });


        paymentCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, CartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    void Init(){
        fullname = findViewById(R.id.edittextfullnamepayment);
        price = findViewById(R.id.textpricepayment);
        address = findViewById(R.id.textaddresspayment);
        phoneNumber = findViewById(R.id.textphonepayment);
        buttonPaymentConfirm = findViewById(R.id.buttonconfirmpayment);
        paymentCancel = findViewById(R.id.buttoncancelpayment);
    }

}
