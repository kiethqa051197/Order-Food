package com.example.anhki.foodapp.DTO;

public class NhanVienDTO {
    public int MANV;
    public int CMND;
    public int MAQUYEN;
    public String TENDANGNHAP, MATKHAU, GIOITINH, NGAYSINH;

    public int getMAQUYEN() {
        return MAQUYEN;
    }

    public void setMAQUYEN(int MAQUYEN) {
        this.MAQUYEN = MAQUYEN;
    }

    public int getMANV() {
        return MANV;
    }

    public void setMANV(int MANV) {
        this.MANV = MANV;
    }

    public int getCMND() {
        return CMND;
    }

    public void setCMND(int CMND) {
        this.CMND = CMND;
    }

    public String getTENDANGNHAP() {
        return TENDANGNHAP;
    }

    public void setTENDANGNHAP(String TENDANGNHAP) {
        this.TENDANGNHAP = TENDANGNHAP;
    }

    public String getMATKHAU() {
        return MATKHAU;
    }

    public void setMATKHAU(String MATKHAU) {
        this.MATKHAU = MATKHAU;
    }

    public String getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(String GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public String getNGAYSINH() {
        return NGAYSINH;
    }

    public void setNGAYSINH(String NGAYSINH) {
        this.NGAYSINH = NGAYSINH;
    }
}
