package com.nhom2.appbantrasua.DAO;

import android.content.Context;
import com.nhom2.appbantrasua.DAL.DAL_LoginRegister;
import com.nhom2.appbantrasua.Entity.LoginRegister;
import java.io.Serializable;
import java.util.List;

public class DAO_LoginRegister implements Serializable {
    DAL_LoginRegister dal = new DAL_LoginRegister();

    public void InitLogin(Context context){
        dal.InitLogin(context);
    }

    public void InsertAccount(String username, String password, String name, String otp, String quyen){
        dal.InsertAccount(username, password, name, otp, quyen);
    }

    public LoginRegister checkAccount(String userName, String password){
        return dal.checkAccount(userName, password);
    }

    public int checkAdmin(String userName){
        return dal.checkAdmin(userName);
    }

    public void ChangePasswordAccount(String userName, String newPassword){
        dal.ChangePasswordAccount(userName, newPassword);
    }
    public List<LoginRegister> ShowAllAccount(){
        return dal.ShowAccount();
    }
    public boolean DeleteAccount(String userName){
        return dal.DeleteAccount(userName);
    }
}
