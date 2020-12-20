package com.example.anhki.foodapp;

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
import com.example.anhki.foodapp.Fragment.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{

    EditText edTenDangNhap, edMatKhau, edNgaySinh, edCMND;
    Button btnDongY, btnThoat;
    TextView txtTieuDeDangKy;
    RadioGroup rgGioiTinh;
    RadioButton rdNam, rdNu;
    Spinner spinQuyen;

    String sGioiTinh;
    NhanVienDAO nhanVienDAO;
    QuyenDAO quyenDAO;
    int manhanvien = 0;
    int landautien = 0;
    List<QuyenDTO> quyenDTOList;
    List<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.layout_dangky);

                // tạo database cho lần đầu tiên chạy chương trình
                //CreateDatabase createDatabase = new CreateDatabase(this);
                //createDatabase.open();

                edTenDangNhap = (EditText) findViewById(R.id.edTenDangNhapDK);
                edMatKhau = (EditText) findViewById(R.id.edMatKhauDK);
                edNgaySinh = (EditText) findViewById(R.id.edNgaySinhDK);
                edCMND = (EditText) findViewById(R.id.edCMNDDK);

                rdNam = (RadioButton) findViewById(R.id.rdNam);
                rdNu = (RadioButton) findViewById(R.id.rdNu);

                txtTieuDeDangKy = (TextView) findViewById(R.id.txtTieuDeDangKy);

                btnDongY = (Button) findViewById(R.id.btnDongYDK);
                btnThoat = (Button) findViewById(R.id.btnThoatDK);

                spinQuyen = (Spinner) findViewById(R.id.spinQuyen);

                rgGioiTinh = (RadioGroup) findViewById(R.id.rgGioiTinhDK);

                btnDongY.setOnClickListener(this);
                btnThoat.setOnClickListener(this);

                edNgaySinh.setOnFocusChangeListener(this);

                nhanVienDAO = new NhanVienDAO(this);
                quyenDAO = new QuyenDAO(this);

                quyenDTOList = quyenDAO.LayDanhSachQuyen();
                dataAdapter = new ArrayList<String>();

                for (int i = 0; i < quyenDTOList.size(); i ++){
                   String tenquyen = quyenDTOList.get(i).getTenQuyen();
                   dataAdapter.add(tenquyen);
                }

                manhanvien = getIntent().getIntExtra("manhanvien", 0);
                landautien = getIntent().getIntExtra("landautien", 0);

                if(landautien == 0){

                    /*chạy lần đầu tiên vì do dữ liệu thêm sau - những lần sau thì đóng lại nếu không sẽ bị ghi thêm dữ liệu*/
                    //quyenDAO.ThemQuyen("Quản lý");
                    //quyenDAO.ThemQuyen("Nhân viên");

                    // đỗ dữ liệu lên spiner
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataAdapter);
                    spinQuyen.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    spinQuyen.setVisibility(View.GONE);
                }

                if (manhanvien != 0){
                    txtTieuDeDangKy.setText(getResources().getString(R.string.capnhatnhanvien));
                    NhanVienDTO nhanVienDTO = nhanVienDAO.LayDanhSachNhanVienTheoMa(manhanvien);

                    edTenDangNhap.setText(nhanVienDTO.getTENDANGNHAP());
                    edMatKhau.setText(nhanVienDTO.getMATKHAU());
                    edCMND.setText(String.valueOf(nhanVienDTO.getCMND()));
                    edNgaySinh.setText(nhanVienDTO.getNGAYSINH());

                    String gioitinh = nhanVienDTO.getGIOITINH();
                    if (gioitinh.equals("Nam")){
                        rdNam.setChecked(true);
                    }else {
                        rdNu.setChecked(true);
                    }
                }
    }

    private void DongYThemNhanVien(){
        String sTenDangNhap = edTenDangNhap.getText().toString();
        String sMatKhau = edMatKhau.getText().toString();

        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh="Nam"; break;

            case R.id.rdNu:
                sGioiTinh="Nữ";break;
        }

        String sNgaySinh = edNgaySinh.getText().toString();
        int sCMND = Integer.parseInt(edCMND.getText().toString());

        if(sTenDangNhap == null || sTenDangNhap.equals("")){
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loitendangnhap), Toast.LENGTH_SHORT).show();
        }
        else if(sMatKhau==null || sMatKhau.equals("")){
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loinhapmatkhau), Toast.LENGTH_SHORT).show();
        }
        else {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setTENDANGNHAP(sTenDangNhap);
            nhanVienDTO.setMATKHAU(sMatKhau);
            nhanVienDTO.setCMND(sCMND);
            nhanVienDTO.setNGAYSINH(sNgaySinh);
            nhanVienDTO.setGIOITINH(sGioiTinh);
            if (landautien != 0){
                //gán mặt định quyền nhân viên là admin
                nhanVienDTO.setMAQUYEN(1);
            }else {
                //gán quyền bằng quyền mà admin khi chọn tạo nhân viên
                int vitri = spinQuyen.getSelectedItemPosition();
                int maquyen = quyenDTOList.get(vitri).getMaQuyen();
                nhanVienDTO.setMAQUYEN(maquyen);
            }

            boolean kiemtra = nhanVienDAO.ThemNV(nhanVienDTO);
            if(kiemtra){
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }
    }

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
        if (kiemtra) {
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btnDongYDK:
                if (manhanvien != 0){
                    //Thực hiện code sửa nhân viên
                    SuaNhanVien();
                }else {
                    //Thực hiện thêm mới nhân viên
                    DongYThemNhanVien();
                }
            break;

            case R.id.btnThoatDK:
                finish(); break;
        }
    }

    //bắt sự kiện onFocus tức là khi trỏ chuột vào edtitext sẽ hiện lên popup
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();

        switch (id){
            case R.id.edNgaySinhDK:
                if (hasFocus) {
                    //xuất popup ngày sinh
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getFragmentManager(), "Ngày sinh");
                }
                break;
        }
    }
}
