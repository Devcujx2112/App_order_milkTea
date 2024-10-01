package com.nhom2.appbantrasua.Entity;

import java.io.Serializable;

public class LoginRegister implements Serializable {
    private String userName;
    private String password;
    private String name;
    private String OTP;
    private String quyen;

    public LoginRegister(String userName, String password, String name, String OTP, String quyen) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.OTP = OTP;
        this.quyen = quyen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    @Override
    public String toString() {
        return "LoginRegister{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", OTP='" + OTP + '\'' +
                ", quyen='" + quyen + '\'' +
                '}';
    }
}
