package com.nhom2.appbantrasua.DAL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom2.appbantrasua.DAO.DAO_Product;
import com.nhom2.appbantrasua.Entity.Product;
import com.nhom2.appbantrasua.GUI.admin_sanPham;
import com.nhom2.appbantrasua.R;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class Product_Admin extends RecyclerView.Adapter<Product_Admin.MyViewHolder>{
    admin_sanPham _adminPoduct;
    private List<Product> products;
    Context context;
    DAO_Product daoProduct = new DAO_Product();

    public Product_Admin(List<Product> products, admin_sanPham _adminPoduct) {
        this._adminPoduct = _adminPoduct;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        context = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product sanPham = products.get(position);
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = numberFormat.format(sanPham.getPrice());
        if (isBase64(sanPham.getImageResource())){
            byte[] decodedBytes = Base64.decode(sanPham.getImageResource(), Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            holder.imageView.setImageBitmap(decodedBitmap);
        }else {
            int imageResId = context.getResources().getIdentifier(sanPham.getImageResource(), "drawable", context.getPackageName());
            holder.imageView.setImageResource(imageResId);
        }
        holder.tensp.setText(sanPham.getName());
        holder.giaCa.setText(formattedPrice + " VND");
        holder.moTa.setText(sanPham.getDescription());

        holder.itemView.setOnClickListener(v -> {
            _adminPoduct.SetText(String.valueOf(sanPham.getId()), holder.tensp.getText().toString(), holder.moTa.getText().toString(), holder.giaCa.getText().toString());

            if (isBase64(sanPham.getImageResource()) ){
                byte[] decodedBytes = Base64.decode(sanPham.getImageResource(), Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                _adminPoduct.img_sanPham.setImageBitmap(decodedBitmap);
            }else {
                int imageResId = context.getResources().getIdentifier(sanPham.getImageResource(), "drawable", context.getPackageName());
                _adminPoduct.img_sanPham.setImageResource(imageResId);
            }
        });

    }
    public boolean isBase64(String s) {
        // Kiểm tra chuỗi có khớp với định dạng Base64 không

        s = s.replace("\n", "");

        if (s == null || s.isEmpty()) {
            return false;
        }
        String base64Pattern = "^[A-Za-z0-9+/]*={0,2}$"; // Biểu thức chính quy cho Base64
        return s.matches(base64Pattern) && (s.length() % 4 == 0); // Kiểm tra cả chiều dài
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout cardView;
        private ImageView imageView;
        private TextView tensp,giaCa,moTa;
        EditText txt_idSanPham, txt_tenSanPham, txt_moTa, txt_giaCa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.Product_admin);
            imageView = itemView.findViewById(R.id.productImage);
            tensp = itemView.findViewById(R.id.productName);
            giaCa = itemView.findViewById(R.id.productPrice);
            moTa = itemView.findViewById(R.id.productDescription);
        }
    }
}
