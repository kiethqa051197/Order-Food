package com.example.anhki.foodapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anhki.foodapp.DTO.LoaiMonAnDTO;
import com.example.anhki.foodapp.R;

import java.util.List;

public class AdapterHienThiLoaiMonAn extends BaseAdapter{
    private final Context context;
    private final int layout;
    private final List<LoaiMonAnDTO> loaiMonAnDTOList;
    private ViewHolderLoaiMonAn viewHolderLoaiMonAn;

    public AdapterHienThiLoaiMonAn(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList){
        this.context = context;
        this.layout = layout;
        this.loaiMonAnDTOList = loaiMonAnDTOList;
    }

    @Override
    public int getCount() {
        return loaiMonAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiMonAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loaiMonAnDTOList.get(position).getMaLoai();
    }

    public static class ViewHolderLoaiMonAn{
        TextView txtTenLoai;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_spinloaithucdon, parent, false);

            viewHolderLoaiMonAn.txtTenLoai = (TextView) view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        } else
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_spinloaithucdon, parent, false);

            viewHolderLoaiMonAn.txtTenLoai = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        } else
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }
}
