package com.example.anhki.foodapp.CustomAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhki.foodapp.DAO.BanAnDAO;
import com.example.anhki.foodapp.DAO.GoiMonDAO;
import com.example.anhki.foodapp.DAO.LoaiMonAnDAO;
import com.example.anhki.foodapp.DTO.BanAnDTO;
import com.example.anhki.foodapp.DTO.GoiMonDTO;
import com.example.anhki.foodapp.DTO.LoaiMonAnDTO;
import com.example.anhki.foodapp.DTO.ThanhToanDTO;
import com.example.anhki.foodapp.Fragment.HienThiThucDonFragment;
import com.example.anhki.foodapp.R;
import com.example.anhki.foodapp.ThanhToanActivity;
import com.example.anhki.foodapp.TrangChuActicity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener{
    private final Context context;
    private final int layout;

    private final List<BanAnDTO> banAnDTOList;
    private ViewHolderBanAn viewHolderBanAn;

    private final BanAnDAO banAnDAO;
    private final GoiMonDAO goiMonDAO;
    private final LoaiMonAnDAO loaiMonAnDAO;

    private final FragmentManager fragmentManager;

    public AdapterHienThiBanAn(Context context, int layout, List<BanAnDTO> banAnDTOList){
        this.context = context;
        this.layout = layout;
        this.banAnDTOList = banAnDTOList;

        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
        loaiMonAnDAO = new LoaiMonAnDAO(context);
        fragmentManager = ((TrangChuActicity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnDTOList.get(position).getMaBan();
    }

    @SuppressLint({"NonConstantResourceId", "SimpleDateFormat"})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        int maban = banAnDTOList.get((int) viewHolderBanAn.imBanAn.getTag()).getMaBan();

        viewHolderBanAn = (ViewHolderBanAn) ((View)v.getParent()).getTag();
        switch (id){
            case R.id.imBanAn:
                banAnDTOList.get((int) v.getTag()).setDuocChon(true);
                HienThiButton();
                break;
            case R.id.imGoiMon:
                List<LoaiMonAnDTO> loaiMonAnDTOs = loaiMonAnDAO.LayDanhSachLoaiMonAn();

                if (loaiMonAnDTOs.size() > 0){
                    Intent LayiTrangChu = ((TrangChuActicity)context).getIntent();
                    int manhanvien = LayiTrangChu.getIntExtra("manhanvien", 0);

                    String tinhtrang = banAnDAO.LayTinhTrangBan(maban);
                    if (tinhtrang.equals("false")){
                        // thực hiện code thêm bảng gọi món va cập nhật lại tình trạng bàn
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                        String ngaygoi= dateFormat.format(calendar.getTime());

                        GoiMonDTO goiMonDTO = new GoiMonDTO();
                        goiMonDTO.setMaBan(maban);
                        goiMonDTO.setMaNhanVien(manhanvien);
                        goiMonDTO.setNgayGoi(ngaygoi);
                        goiMonDTO.setTinhTrang("false");

                        long kiemtra = goiMonDAO.ThemGoiMon(goiMonDTO);
                        banAnDAO.CapNhatTinhTrangBan(maban, "true");
                        if (kiemtra == 0)
                            Toast.makeText(context, context.getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                    }

                    FragmentTransaction tranThucDonTransaction = fragmentManager.beginTransaction();
                    HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                    Bundle bDuLieuThucDon = new Bundle();
                    bDuLieuThucDon.putInt("maban", maban);

                    hienThiThucDonFragment.setArguments(bDuLieuThucDon);

                    tranThucDonTransaction.replace(R.id.content, hienThiThucDonFragment).addToBackStack("hienthibanan");
                    tranThucDonTransaction.commit();
                }else {
                    Toast.makeText(context, "Chưa có danh sách món ăn!!!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.imThanhToan:
                int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban, "false");
                List<ThanhToanDTO> thanhToanDTOS = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);

                if (magoimon != 0){
                    if (thanhToanDTOS.size() > 0){
                        Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
                        iThanhToan.putExtra("maban", maban);
                        context.startActivity(iThanhToan);
                    }else
                        Toast.makeText(context, "Bàn chưa gọi món!!!", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(context, "Bàn chưa gọi món!!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imAnButton:
                AnButton(true);
                break;
        }
    }

    public static class ViewHolderBanAn{
        ImageView imBanAn, imGoiMon, imThanhToan, imAnButton;
        TextView txtTenBanAn;
    }

    private void HienThiButton(){
        viewHolderBanAn.imGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.hieuung_hienthi_button_banan);
        viewHolderBanAn.imGoiMon.startAnimation(animation);
        viewHolderBanAn.imThanhToan.startAnimation(animation);
        viewHolderBanAn.imAnButton.startAnimation(animation);
    }

    private void AnButton(boolean hieuung){
        if (hieuung){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.hieuung_anbutton_banan);
            viewHolderBanAn.imGoiMon.startAnimation(animation);
            viewHolderBanAn.imThanhToan.startAnimation(animation);
            viewHolderBanAn.imAnButton.startAnimation(animation);
        }

        viewHolderBanAn.imGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn = new ViewHolderBanAn();
            view = inflater.inflate(R.layout.custom_layout_hienthibanan, parent, false);
            viewHolderBanAn.imBanAn = view.findViewById(R.id.imBanAn);
            viewHolderBanAn.imGoiMon = view.findViewById(R.id.imGoiMon);
            viewHolderBanAn.imThanhToan = view.findViewById(R.id.imThanhToan);
            viewHolderBanAn.imAnButton = view.findViewById(R.id.imAnButton);
            viewHolderBanAn.txtTenBanAn = view.findViewById(R.id.txtTenBanAn);

            view.setTag(viewHolderBanAn);
        }else
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();

        if (banAnDTOList.get(position).isDuocChon())    //lấy tất cả thuộc tính đc chọn có bằng true hay không
            HienThiButton();
        else
            AnButton(false);

        BanAnDTO banAnDTO = banAnDTOList.get(position); // position tương ứng với mỗi giá trị khi gridview tạo ra

        String kttinhtrang = banAnDAO.LayTinhTrangBan(banAnDTO.getMaBan());
        if (kttinhtrang.equals("true"))
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banantrue);
        else
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banan);

        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());
        viewHolderBanAn.imBanAn.setTag(position);

        viewHolderBanAn.imBanAn.setOnClickListener(this);
        viewHolderBanAn.imGoiMon.setOnClickListener(this);
        viewHolderBanAn.imThanhToan.setOnClickListener(this);
        viewHolderBanAn.imAnButton.setOnClickListener(this);

        return view;
    }
}
