package com.nhom2.appbantrasua.GUI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom2.appbantrasua.DAL.Account_Admin;
import com.nhom2.appbantrasua.DAO.DAO_LoginRegister;
import com.nhom2.appbantrasua.Entity.LoginRegister;
import com.nhom2.appbantrasua.R;
import java.util.ArrayList;
import java.util.List;


public class admin_Account extends Fragment {

    EditText txt_userName, txt_passWord, txt_fullName, txt_email;
    Button btn_clear, btn_delete;
    RecyclerView list_account;
    DAO_LoginRegister daoLogin = new DAO_LoginRegister();
    List<LoginRegister> account = new ArrayList<>();
    Account_Admin MyAdapter;
    public admin_Account() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin__account, container, false);

        AnhXa(view);
        daoLogin.InitLogin(getContext());

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        list_account.addItemDecoration(itemDecoration);


        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_userName.setText("");
                txt_passWord.setText("");
                txt_email.setText("");
                txt_fullName.setText("");
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = txt_userName.getText().toString().trim();
                Log.e("DoneInsertID","id" + userName);
                if (userName.isEmpty()){
                    Toast.makeText(view.getContext(),"Không tìm thấy id sản phẩm",Toast.LENGTH_SHORT).show();
                }else {
                    boolean row = daoLogin.DeleteAccount(userName);
                    if (row){
                        txt_userName.setText("");
                        txt_passWord.setText("");
                        txt_fullName.setText("");
                        txt_email.setText("");
                        LoadShowAccount();
                        Toast.makeText(view.getContext(),"Xóa tài khoản thành công",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(view.getContext(),"Xóa tài khoản thất bại",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        LoadShowAccount();
        return view;
    }

    public void LoadShowAccount(){
        account = daoLogin.ShowAllAccount();
        MyAdapter = new Account_Admin(this, account);
        list_account.setAdapter(MyAdapter);
        list_account.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    public void SetText(String userName,String password,String fullName,String email){
        txt_userName.setText(userName);
        txt_passWord.setText(password);
        txt_fullName.setText(fullName);
        txt_email.setText(email);
    }

    public void AnhXa(View view) {
        txt_userName = view.findViewById(R.id.txt_userName);
        txt_passWord = view.findViewById(R.id.txt_passWord);
        txt_fullName = view.findViewById(R.id.txt_name);
        txt_email = view.findViewById(R.id.txt_email);
        //btn
        btn_clear = view.findViewById(R.id.btn_clear);
        btn_delete = view.findViewById(R.id.btn_delete);
        //list
        list_account = view.findViewById(R.id.list_account);


    }
}