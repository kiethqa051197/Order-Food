package com.example.anhki.foodapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.anhki.foodapp.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.example.anhki.foodapp.DAO.LoaiMonAnDAO;
import com.example.anhki.foodapp.DTO.LoaiMonAnDTO;
import com.example.anhki.foodapp.R;
import com.example.anhki.foodapp.ThemThucDonActivity;
import com.example.anhki.foodapp.TrangChuActicity;

import java.util.List;

public class HienThiThucDonFragment extends Fragment{

    GridView gridView;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    LoaiMonAnDAO loaiMonAnDAO;
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;

    int maban;
    int maquyen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActicity) getActivity()).getSupportActionBar().setTitle(R.string.thucdon); //khi gọi getActivity thì hệ thống không hiểu là của activity nào
        //mà trong TrangChuActivity chứa tất cả Fragment nên ta ép kiểu cho nó về TrangChuActivity
        gridView = (GridView) view.findViewById(R.id.gvHienThiThucDon);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());
        loaiMonAnDTOs = loaiMonAnDAO.LayDanhSachLoaiMonAn();

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        AdapterHienThiLoaiMonAnThucDon adapter = new AdapterHienThiLoaiMonAnThucDon(getActivity(), R.layout.custom_layout_hienloaimonan, loaiMonAnDTOs);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Bundle bDuLieuThucDon = getArguments();
        if (bDuLieuThucDon != null){

            maban = bDuLieuThucDon.getInt("maban");
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maloai = loaiMonAnDTOs.get(position).getMaLoai();

                HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("maloai", maloai);
                bundle.putInt("maban", maban);
                hienThiDanhSachMonAnFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, hienThiDanhSachMonAnFragment).addToBackStack("hienthiloai");

                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (maquyen == 1){
            MenuItem itThemThucDon = menu.add(1, R.id.itThemThucDon, 1, R.string.themthucdon);
            itThemThucDon.setIcon(R.drawable.logodangnhap);
            itThemThucDon.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
       }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.itThemThucDon:
                Intent iThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
                startActivity(iThemThucDon);
                getActivity().overridePendingTransition(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                ;break;
        }
        return super.onOptionsItemSelected(item);
    }
}
