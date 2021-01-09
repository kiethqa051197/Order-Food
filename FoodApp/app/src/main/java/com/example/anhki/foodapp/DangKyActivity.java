package com.example.anhki.foodapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhki.foodapp.DAO.NhanVienDAO;
import com.example.anhki.foodapp.DAO.QuyenDAO;
import com.example.anhki.foodapp.DTO.NhanVienDTO;
import com.example.anhki.foodapp.DTO.QuyenDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edTenDangNhap, edMatKhau, edNgaySinh, edCMND;
    private Button btnDongY, btnThoat;
    private TextView txtTieuDeDangKy;
    private RadioGroup rgGioiTinh;
    private RadioButton rdNam, rdNu;
    private Spinner spinQuyen;

    private String sGioiTinh;

    private NhanVienDAO nhanVienDAO;
    private QuyenDAO quyenDAO;

    private int manhanvien = 0;

    private List<QuyenDTO> quyenDTOList;
    private List<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        edTenDangNhap = findViewById(R.id.edTenDangNhapDK);
        edMatKhau = findViewById(R.id.edMatKhauDK);
        edNgaySinh = findViewById(R.id.edNgaySinhDK);
        edCMND = findViewById(R.id.edCMNDDK);

        rdNam = findViewById(R.id.rdNam);
        rdNu = findViewById(R.id.rdNu);

        txtTieuDeDangKy = findViewById(R.id.txtTieuDeDangKy);

        btnDongY = findViewById(R.id.btnDongYDK);
        btnThoat = findViewById(R.id.btnThoatDK);

        spinQuyen = findViewById(R.id.spinQuyen);

        rgGioiTinh = findViewById(R.id.rgGioiTinhDK);

        btnDongY.setOnClickListener(this);
        btnThoat.setOnClickListener(this);

        edNgaySinh.setOnClickListener(this);

        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);

        quyenDTOList = quyenDAO.LayDanhSachQuyen();
        dataAdapter = new ArrayList<>();

        for (int i = 0; i < quyenDTOList.size(); i ++){
            String tenquyen = quyenDTOList.get(i).getTenQuyen();
            dataAdapter.add(tenquyen);
        }

        manhanvien = getIntent().getIntExtra("manhanvien", 0);

        // đỗ dữ liệu lên spiner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataAdapter);
        spinQuyen.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (manhanvien != 0){
            txtTieuDeDangKy.setText(getResources().getString(R.string.capnhatnhanvien));
            NhanVienDTO nhanVienDTO = nhanVienDAO.LayDanhSachNhanVienTheoMa(manhanvien);

            edTenDangNhap.setText(nhanVienDTO.getTENDANGNHAP());
            edMatKhau.setText(nhanVienDTO.getMATKHAU());
            edCMND.setText(String.valueOf(nhanVienDTO.getCMND()));
            edNgaySinh.setText(nhanVienDTO.getNGAYSINH());

            String gioitinh = nhanVienDTO.getGIOITINH();
            if (gioitinh.equals("Nam")) rdNam.setChecked(true);
            else rdNu.setChecked(true);
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void DongYThemNhanVien(){
        String sTenDangNhap = edTenDangNhap.getText().toString();
        String sMatKhau = edMatKhau.getText().toString();

        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh="Nam";
                break;
            case R.id.rdNu:
                sGioiTinh="Nữ";
                break;
        }

        String sNgaySinh = edNgaySinh.getText().toString();
        int sCMND = Integer.parseInt(edCMND.getText().toString());

        if(sTenDangNhap == null || sTenDangNhap.equals(""))
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loitendangnhap), Toast.LENGTH_SHORT).show();
        else if(sMatKhau==null || sMatKhau.equals(""))
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loinhapmatkhau), Toast.LENGTH_SHORT).show();
        else {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setTENDANGNHAP(sTenDangNhap);
            nhanVienDTO.setMATKHAU(sMatKhau);
            nhanVienDTO.setCMND(sCMND);
            nhanVienDTO.setNGAYSINH(sNgaySinh);
            nhanVienDTO.setGIOITINH(sGioiTinh);

            int vitri = spinQuyen.getSelectedItemPosition();
            int maquyen = quyenDTOList.get(vitri).getMaQuyen();
            nhanVienDTO.setMAQUYEN(maquyen);

            boolean kiemtra = nhanVienDAO.ThemNV(nhanVienDTO);
            if(kiemtra) Toast.makeText(DangKyActivity.this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            else Toast.makeText(DangKyActivity.this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void SuaNhanVien() {
        String sTenDangNhap = edTenDangNhap.getText().toString();
        String sMatKhau = edMatKhau.getText().toString();
        String sNgaySinh = edNgaySinh.getText().toString();
        int sCMND = Integer.parseInt(edCMND.getText().toString());
        switch (rgGioiTinh.getCheckedRadioButtonId()) {
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;
            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }

        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        nhanVienDTO.setMANV(manhanvien);
        nhanVienDTO.setTENDANGNHAP(sTenDangNhap);
        nhanVienDTO.setMATKHAU(sMatKhau);
        nhanVienDTO.setCMND(sCMND);
        nhanVienDTO.setNGAYSINH(sNgaySinh);
        nhanVienDTO.setGIOITINH(sGioiTinh);

        boolean kiemtra = nhanVienDAO.SuaNV(nhanVienDTO);
        if (kiemtra)
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDongYDK:
                if (manhanvien != 0)
                    //Thực hiện code sửa nhân viên
                    SuaNhanVien();
                else
                    //Thực hiện thêm mới nhân viên
                    DongYThemNhanVien();
                break;
            case R.id.btnThoatDK:
                finish();
                break;
            case R.id.edNgaySinhDK:
                ChooseDay();
                break;
        }
    }

    //Chọn ngày
    @SuppressLint({"NewApi", "LocalSuppress", "SimpleDateFormat"})
    public void ChooseDay(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (!edNgaySinh.getText().toString().equals(""))
                cal.setTime(sdf.parse(edNgaySinh.getText().toString()));
            else
                cal.getTime();

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, (datePicker, yearSelected, monthSelected, daySelected) -> {
                monthSelected = monthSelected + 1;
                Date date = StringToDate(daySelected + "/" + monthSelected + "/" + yearSelected, "dd/MM/yyyy");
                edNgaySinh.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
            }, year, month, day);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setTitle("Chọn ngày sinh");

            dialog.show();
        } catch (ParseException e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SimpleDateFormat")
    public Date StringToDate(String dob, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(dob);
        } catch (ParseException e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
