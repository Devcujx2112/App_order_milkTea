package com.nhom2.appbantrasua.DAL;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom2.appbantrasua.Entity.Product;
import com.nhom2.appbantrasua.GUI.ProductDetailsActivity;
import com.nhom2.appbantrasua.R;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private List<Product> productListFull;
    Context context;
    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
        this.productListFull = new ArrayList<>(productList);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        context = parent.getContext();
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // định dạng số tiền
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = numberFormat.format(product.getPrice());

        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText(formattedPrice + " VND");

        if (isBase64(product.getImageResource())){
            byte[] decodedBytes = Base64.decode(product.getImageResource(), Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            holder.productImage.setImageBitmap(decodedBitmap);
        }else {
            int imageResId = context.getResources().getIdentifier(product.getImageResource(), "drawable", context.getPackageName());
            holder.productImage.setImageResource(imageResId);
        }

        // Xử lý khi người dùng nhấn vào sản phẩm
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProductDetailsActivity.class);
            intent.putExtra("product", product);
            v.getContext().startActivity(intent);
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

    public  Filter getFilter(){
        return productFilter;

    }
    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filteredProducts = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredProducts.addAll(productListFull); // No filter, return full list
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Product product : productListFull) {
                    if (product.getName().toLowerCase().contains(filterPattern)) {
                        filteredProducts.add(product);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredProducts;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((List) results.values);
            notifyDataSetChanged(); // Notify adapter to refresh the view
        }
    };
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productPrice;
        ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }


}
