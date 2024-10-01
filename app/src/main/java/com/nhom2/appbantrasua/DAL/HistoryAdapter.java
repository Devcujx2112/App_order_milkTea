package com.nhom2.appbantrasua.DAL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom2.appbantrasua.Entity.History;
import com.nhom2.appbantrasua.R;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private List<History> listHistory;
    private float sum = 0;

    public HistoryAdapter() {
    }

    public HistoryAdapter (Context context, List<History> historyItems){
        this.context = context;
        listHistory = historyItems;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {

        History history = listHistory.get(listHistory.size() - 1 - position);
        if (history == null){
            return;
        }
        String nameProducts = "";

        holder.textnamehistory.setText("Họ tên: " + history.getName());
        holder.textphonehistory.setText("Số điện thoại: " + history.getPhone());
        holder.textaddresshistory.setText("Địa chỉ nhận: " + history.getAddress());

        for (int i = 0; i < history.getListProduct().size(); i++){
            nameProducts += "        - " + history.getListProduct().get(i).getName()+ "  x" + history.getListProduct().get(i).getQuality() + "\n";
        }

        holder.text_order_items.setText("Đồ uống: " + "\n" + nameProducts);

        holder.text_total_amount.setText("Tổng tiền: " + history.getTotalAmount());
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView textnamehistory;
        TextView textphonehistory;
        TextView textaddresshistory;
        TextView text_order_items;
        TextView text_total_amount;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textnamehistory = itemView.findViewById(R.id.textnamehistory);
            this.textphonehistory = itemView.findViewById(R.id.textphonehistory);
            this.textaddresshistory = itemView.findViewById(R.id.textaddresshistory);
            this.text_order_items = itemView.findViewById(R.id.text_order_items);
            this.text_total_amount = itemView.findViewById(R.id.text_total_amount);
        }
    }
}
