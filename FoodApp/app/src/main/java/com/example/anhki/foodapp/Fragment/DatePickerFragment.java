package com.example.anhki.foodapp.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.anhki.foodapp.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
//fragment kiểu như là một activity độc lập ,có vòng đời và giao diện riêng ,có phương thức getActivity để lấy activity ra

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int iNam = calendar.get(Calendar.YEAR);
        int iThang = calendar.get(Calendar.MONTH);
        int iNgay = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, iNgay, iThang, iNam);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        EditText edNgaySinh = (EditText) getActivity().findViewById(R.id.edNgaySinhDK);
        String sNgaySinh = dayOfMonth + "/" + monthOfYear+1 + "/" + year; //month + 1: vì khi chạy lên tháng bị lùi lại 1
        edNgaySinh.setText(sNgaySinh);
    }
}
