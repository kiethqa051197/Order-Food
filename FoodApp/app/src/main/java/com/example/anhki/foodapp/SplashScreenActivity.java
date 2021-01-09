package com.example.anhki.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.anhki.foodapp.DAO.NhanVienDAO;
import com.example.anhki.foodapp.DAO.QuyenDAO;
import com.example.anhki.foodapp.DTO.NhanVienDTO;
import com.example.anhki.foodapp.Database.CreateDatabase;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences mMoLanDau;
    private SharedPreferences.Editor editor;

    private QuyenDAO quyenDAO;
    private NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        @SuppressLint("CommitPrefEdits") Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                Log.d("kiemtra", e.getMessage());
            } finally {
                mMoLanDau = getSharedPreferences("SPR_MOLANDAU", 0);
                if (mMoLanDau != null) {
                    boolean firstOpen = mMoLanDau.getBoolean("MOLANDAU", true);

                    if (firstOpen){
                        CreateDatabase createDatabase = new CreateDatabase(this);
                        createDatabase.open();

                        quyenDAO = new QuyenDAO(this);
                        quyenDAO.ThemQuyen("Quản lý");
                        quyenDAO.ThemQuyen("Nhân viên");

                        nhanVienDAO = new NhanVienDAO(this);
                        NhanVienDTO nhanVienDTO = new NhanVienDTO();
                        nhanVienDTO.setTENDANGNHAP("admin");
                        nhanVienDTO.setCMND(111111111);
                        nhanVienDTO.setGIOITINH("Nam");
                        nhanVienDTO.setMATKHAU("admin");
                        nhanVienDTO.setNGAYSINH("01/01/1997");
                        nhanVienDTO.setMAQUYEN(0);

                        nhanVienDAO.ThemNV(nhanVienDTO);

                        editor = mMoLanDau.edit();
                        editor.putBoolean("MOLANDAU", false);
                        editor.apply();
                    }

                    Intent iDangNhap = new Intent(this, DangNhapActivity.class);
                    startActivity(iDangNhap);
                    finish();
                }
            }
        });
        thread.start();
    }
}