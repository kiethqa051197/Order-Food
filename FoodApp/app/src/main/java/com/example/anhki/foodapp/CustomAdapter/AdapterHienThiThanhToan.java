package com.example.anhki.foodapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anhki.foodapp.DTO.ThanhToanDTO;
import com.example.anhki.foodapp.R;

import java.util.List;

public class AdapterHienThiThanhToan extends BaseAdapter{
    private final Context context;
    private final int layout;
    private final List<ThanhToanDTO> thanhToanDTOS;
    private ViewHolderThanhToan viewHolderThanhToan;

    public AdapterHienThiThanhToan(Context context, int layout, List<ThanhToanDTO> thanhToanDTOS){
        this.context = context;
        this.layout = layout;
        this.thanhToanDTOS = thanhToanDTOS;
    }

    @Override
    public int getCount() {
        return thanhToanDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhToanDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolderThanhToan{
        TextView txtTenMonAn, txtSoLuong, txtGiaTien;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolderThanhToan.txtTenMonAn = view.findViewById(R.id.txtTenMonAnThanToan);
            viewHolderThanhToan.txtGiaTien = view.findViewById(R.id.txtGiaTienThanhToan);
            viewHolderThanhToan.txtSoLuong = view.findViewById(R.id.txtSoLuongThanhToan);

            view.setTag(viewHolderThanhToan);
        }else {
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }

        ThanhToanDTO thanhToanDTO = thanhToanDTOS.get(position);

        viewHolderThanhToan.txtTenMonAn.setText(thanhToanDTO.getTenMonAn());
        viewHolderThanhToan.txtSoLuong.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolderThanhToan.txtGiaTien.setText(String.valueOf(thanhToanDTO.getGiatien()));

        return view ;
    }
}
