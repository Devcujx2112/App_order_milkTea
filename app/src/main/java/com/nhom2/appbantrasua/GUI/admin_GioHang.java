package com.nhom2.appbantrasua.GUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.nhom2.appbantrasua.R;


public class admin_GioHang extends Fragment {

    public admin_GioHang() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin__gio_hang, container, false);
    }
}