package com.example.anhki.foodapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anhki.foodapp.DAO.NhanVienDAO;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edTenDangNhap, edMatKhau;
    private Button btnDongY;
    private NhanVienDAO nhanVienDAO;

    private final int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);

        edTenDangNhap = findViewById(R.id.edTenDangNhapDN);
        edMatKhau = findViewById(R.id.edMatKhauDN);

        btnDongY = findViewById(R.id.btnDongYDN);

        nhanVienDAO = new NhanVienDAO(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestStoragePermission();

        btnDongY.setOnClickListener(this);
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Ứng dụng cần được cấp quyền")
                    .setMessage("Ứng dụng cần được cấp quyền truy cập bộ nhớ để có thể sử dụng ứng dụng tốt hơn!")
                    .setPositiveButton("Ok", (dialog, which) -> ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE))
                    .setNegativeButton("Hủy", (dialog, which) -> System.exit(0))
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Đã được cấp quyền!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ứng dụng bị từ chối cấp quyền!", Toast.LENGTH_SHORT).show();
                requestStoragePermission();
            }
        }
    }

    private void btnDongY(){
        String sTenDangNhap = edTenDangNhap.getText().toString();
        String sMatKhau = edMatKhau.getText().toString();
        int kiemtra = nhanVienDAO.KiemTraDangNhap(sTenDangNhap, sMatKhau);
        int maquyen = nhanVienDAO.LayQuyenNhanVien(kiemtra);

        if (kiemtra != 0){
            SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE); // chỉ có ứng dụng này đc dùng
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("maquyen", maquyen);
            editor.apply();

            Intent iTrangChu = new Intent(DangNhapActivity.this, TrangChuActicity.class); // chuyển đổi 2 activity
            iTrangChu.putExtra("tendn", edTenDangNhap.getText().toString());
            iTrangChu.putExtra("manhanvien", kiemtra);
            startActivity(iTrangChu);

            finish();

            overridePendingTransition(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
        } else Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại!!", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnDongYDN) btnDongY();
    }
}
