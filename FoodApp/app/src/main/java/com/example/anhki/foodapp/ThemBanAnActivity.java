package com.example.anhki.foodapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.anhki.foodapp.DAO.BanAnDAO;

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edTenThemBanAn;
    Button btnDongYThemBanAn;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thembanan);

        edTenThemBanAn = (EditText) findViewById(R.id.edTenThemBanAn);
        btnDongYThemBanAn = (Button) findViewById(R.id.btnDongYThemBanAn);

        banAnDAO = new BanAnDAO(this);
        btnDongYThemBanAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String sTenBanAn = edTenThemBanAn.getText().toString();
        if (sTenBanAn != null || sTenBanAn.equals("")){
            boolean kiemtra = banAnDAO.ThemBanAn(sTenBanAn);
            Intent intent = new Intent();
            intent.putExtra("ketquathem", kiemtra);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
