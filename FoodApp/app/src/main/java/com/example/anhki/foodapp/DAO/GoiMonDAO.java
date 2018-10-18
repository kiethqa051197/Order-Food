package com.example.anhki.foodapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.example.anhki.foodapp.DTO.ChiTietGoiMonDTO;
import com.example.anhki.foodapp.DTO.GoiMonDTO;
import com.example.anhki.foodapp.DTO.ThanhToanDTO;
import com.example.anhki.foodapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class GoiMonDAO {
    SQLiteDatabase database;

    public GoiMonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemGoiMon(GoiMonDTO goiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_MABAN, goiMonDTO.getMaBan());
        contentValues.put(CreateDatabase.TB_GOIMON_MANV, goiMonDTO.getMaNhanVien());
        contentValues.put(CreateDatabase.TB_GOIMON_NGAYGOI, goiMonDTO.getNgayGoi());
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, goiMonDTO.getTinhTrang());

        long magoimon = database.insert(CreateDatabase.TB_GOIMON, null, contentValues);

        return magoimon;
    }

   public long LayMaGoiMonTheoMaBan(int maban, String tinhtrang){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_GOIMON + " WHERE " + CreateDatabase.TB_GOIMON_MABAN + " = '" + maban + "' AND "
                + CreateDatabase.TB_GOIMON_TINHTRANG + " = '" + tinhtrang + "'";

        long magoimon = 0;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MAGOIMON));

            cursor.moveToNext();
        }
        return magoimon;
   }

   public boolean KiemTraMonAnDaTonTai(int magoimon, int mamonan){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + mamonan + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + magoimon;

        Cursor cursor = database.rawQuery(truyvan, null);
        if (cursor.getCount() != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public int LaySoLuongMonAnTheoMaGoiMon(int magoimon, int mamonan){
        int soluong = 0;
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + mamonan + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + magoimon;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG));

            cursor.moveToNext();
        }

        return soluong;
    }

    public boolean CapNhatSoLuong(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoLuong());

        long kiemtra = database.update(CreateDatabase.TB_CHITIETGOIMON, contentValues ,CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + chiTietGoiMonDTO.getMaGoiMon() + " AND "
                + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + chiTietGoiMonDTO.getMaMonAn(), null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean ThemChiTietGoiMon(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoLuong());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAGOIMON, chiTietGoiMonDTO.getMaGoiMon());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAMONAN, chiTietGoiMonDTO.getMaMonAn());

        long kiemtra =  database.insert(CreateDatabase.TB_CHITIETGOIMON, null, contentValues);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<ThanhToanDTO> LayDanhSachMonAnTheoMaGoiMon(int magoimon){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " ct," + CreateDatabase.TB_MONAN + " ma WHERE "
                + "ct." + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = ma." + CreateDatabase.TB_MONAN_MAMON
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + magoimon;

        List<ThanhToanDTO> thanhToanDTOS = new ArrayList<ThanhToanDTO>();
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThanhToanDTO thanhToanDTO = new ThanhToanDTO();
            thanhToanDTO.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG)));
            thanhToanDTO.setGiatien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            thanhToanDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));

            thanhToanDTOS.add(thanhToanDTO);
            cursor.moveToNext();
        }
        return thanhToanDTOS;
    }

    public boolean CapNhatTrangThaiGoiMonTheoMaBan(int maban, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, maban);

        long kiemtra = database.update(CreateDatabase.TB_GOIMON, contentValues, CreateDatabase.TB_GOIMON_MABAN + " = " + maban, null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }
}
