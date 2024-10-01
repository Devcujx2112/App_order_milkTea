package com.nhom2.appbantrasua.GUI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nhom2.appbantrasua.DAL.Product_Admin;
import com.nhom2.appbantrasua.DAO.DAO_Product;
import com.nhom2.appbantrasua.Entity.Product;
import com.nhom2.appbantrasua.R;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public class admin_sanPham extends Fragment {
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    DAO_Product daoProduct = new DAO_Product();
    String encodedImage = "";

    TextView txt_idSanPham;
    EditText  txt_tenSanPham, txt_moTa, txt_giaCa;
    Button btn_them,btn_xoa,btn_sua,btn_clear,btn_addImage;
    public ImageView img_sanPham;
    RecyclerView recyclerView;
    List<Product> products;
    Product_Admin MyAdapter;

    public admin_sanPham() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_san_pham, container, false);

        AnhXa(view);
        daoProduct.InitLogin(getContext());

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String id = String.valueOf(daoProduct.ShowListProduct().size() );
                    String nameproduct = txt_tenSanPham.getText().toString().trim();
                    String description = txt_moTa.getText().toString().trim();
                    String price = txt_giaCa.getText().toString().trim();
                    String imgprd = "";
                    if (encodedImage != ""){
                        imgprd = encodedImage.toString().trim();
                    }

                    if (id.isEmpty() || nameproduct.isEmpty() || description.isEmpty() || price.isEmpty() || imgprd.isEmpty()) {
                        Toast.makeText(view.getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        daoProduct.Insert_Product(id,nameproduct,description,price,imgprd);
                        Toast.makeText(view.getContext(), "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                        LoadShowProduct();
                        txt_idSanPham.setText("");
                        txt_tenSanPham.setText("");
                        txt_moTa.setText("");
                        txt_giaCa.setText("");
                        img_sanPham.setImageResource(0);
                    }
                }catch (Exception e){

                }
            }
        });

        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String id = txt_idSanPham.getText().toString().trim();
                    String nameproduct = txt_tenSanPham.getText().toString().trim();
                    String description = txt_moTa.getText().toString().trim();
                    String price = txt_giaCa.getText().toString().trim();
                    String imgprd = "";
                    Log.d("id","id"+id);
                    Log.d("name","id"+nameproduct);
                    Log.d("moTa","id"+description);
                    Log.d("gia","id"+price);
                    if (!encodedImage.isEmpty()) {
                        imgprd = encodedImage.toString().trim();
                    }

                    if (id.isEmpty()) {
                        Toast.makeText(view.getContext(), "ID sản phẩm không được để trống!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (nameproduct.isEmpty() || description.isEmpty() || price.isEmpty()) {
                        Toast.makeText(view.getContext(), "Vui lòng điền đầy đủ thông tin sản phẩm!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    daoProduct.UpdateProductByID(id, nameproduct, description, price, imgprd);

                    txt_idSanPham.setText("");
                    txt_tenSanPham.setText("");
                    txt_moTa.setText("");
                    txt_giaCa.setText("");
                    img_sanPham.setImageResource(0);
                    Toast.makeText(view.getContext(), "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    LoadShowProduct();
                }catch (Exception e){

                }

            }
        });


        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String id = txt_idSanPham.getText().toString().trim();
                    Log.e("DoneInsertID","id" + id);
                    if (id.isEmpty()){
                        Toast.makeText(view.getContext(),"Không tìm thấy id sản phẩm",Toast.LENGTH_SHORT).show();
                    }else {
                        boolean row = daoProduct.DeLeTeProduct(id);
                        if (row){
                            txt_idSanPham.setText("");
                            txt_tenSanPham.setText("");
                            txt_moTa.setText("");
                            txt_giaCa.setText("");
                            img_sanPham.setImageResource(0);
                            LoadShowProduct();
                            Toast.makeText(view.getContext(),"Xóa sản phẩm thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(view.getContext(),"Xóa sản phẩm thất bại",Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (Exception e){

                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_idSanPham.setText("");
                txt_tenSanPham.setText("");
                txt_moTa.setText("");
                txt_giaCa.setText("");
                img_sanPham.setImageResource(0);
            }
        });
//
//
        LoadShowProduct();
//        btn_addImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},999);
//                }
//            }
//        });
        return view;
    }


    public void SetText(String id, String temSp, String moTa, String giaCa){
        txt_idSanPham.setText(id);
        txt_tenSanPham.setText(temSp);
        txt_moTa.setText(moTa);
        txt_giaCa.setText(giaCa);
    }


    private static final int PICK_IMAGE = 1;

    // onViewCreated is called after the view is created
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khi nhấn vào imageView, mở thư viện ảnh
        btn_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }});
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    // Sử dụng registerForActivityResult thay thế cho startActivityForResult
    private final ActivityResultLauncher<Intent> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    img_sanPham.setImageURI(imageUri);
                    try {
                        // Chuyển đổi Uri thành Bitmap
                        Bitmap bitmap = uriToBitmap(imageUri);

                        // Chuyển đổi Bitmap thành mảng byte
                        byte[] byteArray = bitmapToByteArray(bitmap);

                        // Chuyển đổi mảng byte thành chuỗi Base64
                        encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        // Lưu chuỗi Base64 vào nơi mong muốn
                        // Ví dụ: SharedPreferences, cơ sở dữ liệu, hoặc truyền qua intent

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    private Bitmap uriToBitmap(Uri uri) throws Exception {
        InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
        return BitmapFactory.decodeStream(inputStream);
    }

    // Chuyển đổi Bitmap thành mảng byte
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream); // Bạn có thể thay đổi định dạng và chất lượng
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            // Lấy ảnh từ intent
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            img_sanPham.setImageBitmap(photo);
        }
    }

    public void LoadShowProduct(){
        products = daoProduct.ShowListProduct();
        MyAdapter = new Product_Admin(products, this);
        recyclerView.setAdapter(MyAdapter);


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }


    private void AnhXa(View view){
        // txt
        txt_idSanPham = view.findViewById(R.id.txt_idSanPham);
        txt_tenSanPham = view.findViewById(R.id.txt_tenSanPham);
        txt_moTa = view.findViewById(R.id.txt_moTa);
        txt_giaCa = view.findViewById(R.id.txt_giaCa);
        // buttons
        btn_them = view.findViewById(R.id.btn_them);
        btn_xoa = view.findViewById(R.id.btn_xoa);
        btn_sua = view.findViewById(R.id.btn_sua);
        btn_clear = view.findViewById(R.id.btn_clear);
        btn_addImage = view.findViewById(R.id.btn_addImg);
        //img
        img_sanPham = view.findViewById(R.id.img_sanPham);
        //list
        recyclerView = view.findViewById(R.id.recyclerView);
    }
}