package com.nhom2.appbantrasua;

import com.nhom2.appbantrasua.Entity.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private static HistoryManager instance;
    private static List<History> ListHistory;

    private HistoryManager() {
        ListHistory = new ArrayList<>();
    }

    public static HistoryManager getInstance() {
        if (instance == null) {
            instance = new HistoryManager();
        }
        return instance;
    }

    public List<History> getListHistory() {
        return ListHistory;
    }

    public void setListHistory(List<History> listHistory) {
        ListHistory = listHistory;
    }
}
