package com.example.anhki.foodapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.anhki.foodapp.Fragment.HienThiBanAnFragment;
import com.example.anhki.foodapp.Fragment.HienThiNhanVienFragment;
import com.example.anhki.foodapp.Fragment.HienThiThucDonFragment;

public class TrangChuActicity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView txtTenNhanVien_Navigation;
    FragmentManager fragmentManager;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationview_trangchu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        View view = navigationView.inflateHeaderView(R.layout.layout_header_navigation_trangchu);
        txtTenNhanVien_Navigation = (TextView) view.findViewById(R.id.txtTenNhanVienNavigation);

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
                ;break;

            case R.id.itThucDon:
                FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                tranHienThiThucDon.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                tranHienThiThucDon.replace(R.id.content, hienThiThucDonFragment);
                tranHienThiThucDon.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;

            case R.id.itNhanVien:
                FragmentTransaction tranNhanVien = fragmentManager.beginTransaction();
                HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
                tranNhanVien.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                tranNhanVien.replace(R.id.content, hienThiNhanVienFragment);
                tranNhanVien.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;
        }
        return false;
    }
}
