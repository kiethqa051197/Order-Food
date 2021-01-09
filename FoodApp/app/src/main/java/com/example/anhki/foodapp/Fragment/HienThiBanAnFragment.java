package com.example.anhki.foodapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.anhki.foodapp.CustomAdapter.AdapterHienThiBanAn;
import com.example.anhki.foodapp.DAO.BanAnDAO;
import com.example.anhki.foodapp.DTO.BanAnDTO;
import com.example.anhki.foodapp.R;
import com.example.anhki.foodapp.SuaBanAnActivity;
import com.example.anhki.foodapp.ThemBanAnActivity;
import com.example.anhki.foodapp.TrangChuActicity;

import java.util.List;

public class HienThiBanAnFragment extends Fragment {
    public static int RESQUEST_CODE_THEM = 111;
    public static int RESQUEST_CODE_SUA = 16;

    private GridView gvHienThiBanAn;
    private List<BanAnDTO> banAnDTOList;
    private BanAnDAO banAnDAO;

    private int maquyen = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActicity)getActivity()).getSupportActionBar().setTitle(R.string.banan); //khi gọi getActivity thì hệ thống không hiểu là của activity nào
                                                                                    //mà trong TrangChuActivity chứa tất cả Fragment nên ta ép kiểu cho nó về TrangChuActivity

        gvHienThiBanAn = view.findViewById(R.id.gvHienBanAn);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen", 0);

        banAnDAO = new BanAnDAO(getActivity());
        banAnDAO.LayTatCaBanAn();

        HienThiDanhSachBanAn();
        if (maquyen == 0) registerForContextMenu(gvHienThiBanAn);

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maban = banAnDTOList.get(vitri).getMaBan();

        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaBanAnActivity.class);
                intent.putExtra("maban", maban);
                startActivityForResult(intent, RESQUEST_CODE_SUA);
                break;
            case R.id.itXoa:
                boolean kiemtra = banAnDAO.XoaBanAn(maban);
                if (kiemtra){
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                    HienThiDanhSachBanAn();
                }else
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.loi) + maban, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maquyen == 0){
            //là quản lý
            MenuItem itThemBanAn = menu.add(1, R.id.itThemBanAn, 1, R.string.thembanan);
            itThemBanAn.setIcon(R.drawable.thembanan);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itThemBanAn) {
            Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
            startActivityForResult(iThemBanAn, RESQUEST_CODE_THEM);
        }
        return true;
    }

    private void HienThiDanhSachBanAn(){
        banAnDTOList = banAnDAO.LayTatCaBanAn();
        AdapterHienThiBanAn adapterHienThiBanAn = new AdapterHienThiBanAn(getActivity(), R.layout.custom_layout_hienthibanan, banAnDTOList);
        gvHienThiBanAn.setAdapter(adapterHienThiBanAn);
        adapterHienThiBanAn.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESQUEST_CODE_THEM){
            if (resultCode == Activity.RESULT_OK){
                boolean kiemtra = data.getBooleanExtra("ketquathem", false);
                if(kiemtra){
                    HienThiDanhSachBanAn();
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == RESQUEST_CODE_SUA){
            if (resultCode == Activity.RESULT_OK){
                boolean kiemtra = data.getBooleanExtra("kiemtra", false);
                HienThiDanhSachBanAn();
                if (kiemtra)
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
