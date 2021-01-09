package com.example.anhki.foodapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhki.foodapp.Fragment.HienThiBanAnFragment;
import com.example.anhki.foodapp.Fragment.HienThiNhanVienFragment;
import com.example.anhki.foodapp.Fragment.HienThiThucDonFragment;

public class TrangChuActicity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView txtTenNhanVien_Navigation;
    private FragmentManager fragmentManager;

    private final int STORAGE_PERMISSION_CODE = 1;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestStoragePermission();

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationview_trangchu);
        toolbar = findViewById(R.id.toolbar);

        View view = navigationView.inflateHeaderView(R.layout.layout_header_navigation_trangchu);
        txtTenNhanVien_Navigation = view.findViewById(R.id.txtTenNhanVienNavigation);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.mo, R.string.dong){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String tendn = intent.getStringExtra("tendn");//lưu ý , lấy dữ liệu được gửi từ form dangnhap
        txtTenNhanVien_Navigation.setText(tendn);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
        tranHienThiBanAn.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
        tranHienThiBanAn.replace(R.id.content, hienThiBanAnFragment);
        tranHienThiBanAn.commit();
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itTrangChu:
                FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
                HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
                tranHienThiBanAn.replace(R.id.content, hienThiBanAnFragment);
                tranHienThiBanAn.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.itThucDon:
                FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                tranHienThiThucDon.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                tranHienThiThucDon.replace(R.id.content, hienThiThucDonFragment);
                tranHienThiThucDon.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.itNhanVien:
                FragmentTransaction tranNhanVien = fragmentManager.beginTransaction();
                HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
                tranNhanVien.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                tranNhanVien.replace(R.id.content, hienThiNhanVienFragment);
                tranNhanVien.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
        }
        return false;
    }
}
