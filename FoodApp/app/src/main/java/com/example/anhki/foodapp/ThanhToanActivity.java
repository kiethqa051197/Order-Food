package com.example.anhki.foodapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhki.foodapp.CustomAdapter.AdapterHienThiThanhToan;
import com.example.anhki.foodapp.DAO.BanAnDAO;
import com.example.anhki.foodapp.DAO.GoiMonDAO;
import com.example.anhki.foodapp.DTO.ThanhToanDTO;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener{
    private GridView gridView;
    private Button btnThanhToan, btnThoat;
    private TextView txtTongTien;
    private GoiMonDAO goiMonDAO;
    private List<ThanhToanDTO> thanhToanDTOList;
    private AdapterHienThiThanhToan adapterHienThiThanhToan;
    private BanAnDAO banAnDAO;
    private FragmentManager fragmentManager;

    long tongtien = 0;
    int maban;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);

        gridView = findViewById(R.id.gvThanhToan);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnThoat = findViewById(R.id.btnThoatThanhToan);
        txtTongTien = findViewById(R.id.txtTongTien);

        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);

        fragmentManager = getSupportFragmentManager();

        maban = getIntent().getIntExtra("maban", 0);
        if (maban != 0){
            HienThiThanhToan();

            for (int i = 0; i < thanhToanDTOList.size(); i++){
                int soluong = thanhToanDTOList.get(i).getSoLuong();
                int giatien = thanhToanDTOList.get(i).getGiatien();

                tongtien += (soluong * giatien);
            }
            txtTongTien.setText(getResources().getString(R.string.tongcong) + " " + tongtien);
        }

        btnThanhToan.setOnClickListener(this);
        btnThoat.setOnClickListener(this);
    }

    private void HienThiThanhToan(){
        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban, "false");
        thanhToanDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);

        adapterHienThiThanhToan = new AdapterHienThiThanhToan(this, R.layout.custom_layout_hienthithanhtoan, thanhToanDTOList);
        gridView.setAdapter(adapterHienThiThanhToan);
        adapterHienThiThanhToan.notifyDataSetChanged();
    }

    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnThanhToan:
                boolean kiemrabanan = banAnDAO.CapNhatTinhTrangBan(maban, "false");
                boolean kiemtragoimom = goiMonDAO.CapNhatTrangThaiGoiMonTheoMaBan(maban, "true");

                if(kiemrabanan && kiemtragoimom){
                    Toast.makeText(ThanhToanActivity.this, getResources().getString(R.string.thanhtoanthanhcong), Toast.LENGTH_SHORT);
                    HienThiThanhToan();
                }else
                    Toast.makeText(ThanhToanActivity.this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT);
                break;
            case R.id.btnThoatThanhToan:
                finish();
                break;
        }
    }
}
