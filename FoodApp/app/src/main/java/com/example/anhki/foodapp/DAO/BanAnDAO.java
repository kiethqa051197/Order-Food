package com.example.anhki.foodapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhki.foodapp.DTO.BanAnDTO;
import com.example.anhki.foodapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO {

    SQLiteDatabase database;

    public BanAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemBanAn(String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenban);
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, "false");

        long kiemtra = database.insert(CreateDatabase.TB_BANAN, null, contentValues);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<BanAnDTO> LayTatCaBanAn(){
        List<BanAnDTO> banAnDTOList = new ArrayList<BanAnDTO>();
        String truyvan= "SELECT * FROM " + CreateDatabase.TB_BANAN;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_BANAN_MABAN)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TENBAN)));

            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }
        return banAnDTOList;
    }

    public String LayTinhTrangBan(int maban){
        String tinhtrang = "";
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_BANAN + " WHERE " + CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'";
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TINHTRANG));

            cursor.moveToNext();
        }

        return tinhtrang;
    }

    public boolean CapNhatTinhTrangBan(int maban, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, tinhtrang);

        long kiemtra = database.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'", null);

        if (kiemtra != 0) {
            return true;
        }else {
            return false;
        }
    }

    public boolean CapNhatTenBan(int maban, String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenban);

        long kiemtra = database.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN + " = '" + maban + "'", null);

        if (kiemtra != 0) {
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaBanAn(int maban){
        long kiemtra = database.delete(CreateDatabase.TB_BANAN, CreateDatabase.TB_BANAN_MABAN + " = " + maban, null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }
}