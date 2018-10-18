package com.example.anhki.foodapp.DTO;

public class GoiMonDTO {

    int MaGoiMon;
    int MaBan;
    int MaNhanVien;
    String TinhTrang, NgayGoi;


    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int maBan) {
        MaBan = maBan;
    }

    public int getMaGoiMon() {
        return MaGoiMon;
    }

    public void setMaGoiMon(int maGoiMon) {
        MaGoiMon = maGoiMon;
    }

    public int getMaNhanVien() {
        return MaNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        MaNhanVien = maNhanVien;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getNgayGoi() {
        return NgayGoi;
    }

    public void setNgayGoi(String ngayGoi) {
        NgayGoi = ngayGoi;
    }

}
