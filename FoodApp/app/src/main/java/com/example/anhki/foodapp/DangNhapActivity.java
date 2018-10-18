package com.example.anhki.foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anhki.foodapp.DAO.NhanVienDAO;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edTenDangNhap, edMatKhau;
    Button btnDongY, btnDangKy;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);

        edTenDangNhap = (EditText) findViewById(R.id.edTenDangNhapDN);
        edMatKhau = (EditText) findViewById(R.id.edMatKhauDN);

        btnDongY = (Button) findViewById(R.id.btnDongYDN);
        btnDangKy = (Button) findViewById(R.id.btnDangKyDN);

        nhanVienDAO = new NhanVienDAO(this);

        btnDongY.setOnClickListener(this);
        btnDangKy.setOnClickListener(this);

        btnDangKy.setVisibility(View.GONE);
        HienThiButtonDangKyVaDongY();
    }

    private void HienThiButtonDangKyVaDongY(){
        boolean kiemtra = nhanVienDAO.KiemTraNhanVien();
        if (kiemtra){
            btnDangKy.setVisibility(View.GONE);
            btnDongY.setVisibility(View.VISIBLE);
        }
        else{
            btnDangKy.setVisibility(View.VISIBLE);
            btnDongY.setVisibility(View.GONE);
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
            editor.commit();

            Intent iTrangChu = new Intent(DangNhapActivity.this, TrangChuActicity.class); // chuyển đổi 2 activity
            iTrangChu.putExtra("tendn", edTenDangNhap.getText().toString());
            iTrangChu.putExtra("manhanvien", kiemtra);
            startActivity(iTrangChu);
            overridePendingTransition(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
        } else {
            Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnDangKy(){
        Intent iDangKy = new Intent(DangNhapActivity.this, DangKyActivity.class);
        iDangKy.putExtra("landautien", 1);
        startActivity(iDangKy);
    }

    @Override
    public void onResume(){
        super.onResume();
        HienThiButtonDangKyVaDongY();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnDangKyDN:
                btnDangKy();
                ;break;

            case R.id.btnDongYDN:
                btnDongY();
                ;break;
        }
    }
}
