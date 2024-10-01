package com.nhom2.appbantrasua.DAL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom2.appbantrasua.Entity.LoginRegister;
import com.nhom2.appbantrasua.GUI.admin_Account;
import com.nhom2.appbantrasua.R;

import java.util.List;

public class Account_Admin extends RecyclerView.Adapter<Account_Admin.MyViewHolder>{
    admin_Account _adminAccount;
    private List<LoginRegister> accounts;
    Context context;

    public Account_Admin(admin_Account _adminAccount, List<LoginRegister> accounts) {
        this._adminAccount = _adminAccount;
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account,parent,false);
        context = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LoginRegister account = accounts.get(position);
        holder.tv_userName.setText(account.getUserName());
        holder.tv_password.setText(account.getPassword());
        holder.tv_fullNam.setText(account.getName());
        holder.tv_email.setText(account.getOTP());

        holder.itemView.setOnClickListener(v -> {
            _adminAccount.SetText(
                    String.valueOf(account.getUserName()),
                    String.valueOf(account.getPassword()),
                    String.valueOf(account.getName()),
                    String.valueOf(account.getOTP())
            );
        });
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_userName,tv_password,tv_fullNam,tv_email;
        private LinearLayout cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_userName = itemView.findViewById(R.id.tv_userName);
            tv_password = itemView.findViewById(R.id.tv_passWord);
            tv_fullNam = itemView.findViewById(R.id.tv_fullName);
            tv_email = itemView.findViewById(R.id.tv_email);
            cardView = itemView.findViewById(R.id.account_admin);
        }
    }


}
