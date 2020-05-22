package com.example.apporder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormDangKy extends AppCompatActivity {
    private TextInputEditText txtEHo;
    private TextInputEditText txtETen;
    private TextInputEditText txtEDiaChi;
    private Button btnNgaySinh;
    private Button btnDangKy;
    private RadioButton radioButtonNam;
    private RadioButton radioButtonNu;
    private EditText edTEmail;

    private boolean kiemTraChonNgay = false;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_dang_ky);

        txtEHo = (TextInputEditText) findViewById(R.id.txtEHo);
        txtETen = (TextInputEditText) findViewById(R.id.txtETen);
        btnNgaySinh = (Button) findViewById(R.id.button);
        txtEDiaChi = (TextInputEditText) findViewById(R.id.txtEDiaChi);
        edTEmail = (EditText) findViewById(R.id.edTEmail);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        radioButtonNam = (RadioButton) findViewById(R.id.radioButtonNam);
        radioButtonNu = (RadioButton) findViewById(R.id.radioButtonNu);


        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (kiemTraRong() == true && kiemTraCheckBox() == true  && kiemTraChonNgay == true) {
                    Intent TV = new Intent(FormDangKy.this, DangNhapKhachHang.class);
                    startActivity(TV);

                    //test

                }

            }
        });


        // Bắt sự kiện khi click vào ô Ngày sinh
        btnNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        //Báo lỗi cho ô Họ người dùng
        txtEHo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtEHo.getText().toString().isEmpty()) {
                    txtEHo.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });
        //Báo lỗi cho ô Tên người dùng
        txtETen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtETen.getText().toString().isEmpty()) {
                    txtETen.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });

        //Báo lỗi cho ô Địa chỉ người dùng
        txtEDiaChi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtEDiaChi.getText().toString().isEmpty()) {
                    txtEDiaChi.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });

        //Báo lỗi cho ô Email người dùng
        edTEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (edTEmail.getText().toString().isEmpty()) {
                    edTEmail.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });


    }

    private boolean KiemTraRong(){
        if(!txtEHo.getText().toString().isEmpty() && !txtETen.getText().toString().isEmpty()
            && txtEDiaChi.getText().toString().isEmpty() && edTEmail.getText().toString().isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    private void ChonNgay(){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        // Gọi vào lịch có sẵn
        DatePickerDialog datePickerDialog = new DatePickerDialog(FormDangKy.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Chọn ngày tháng cần chọn, nếu không có sẽ được mặc định theo ngày tháng hiện tại
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                btnNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));


                kiemTraChonNgay = true;
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private boolean kiemTraRong() {
        if (!txtEHo.getText().toString().isEmpty() && !txtETen.getText().toString().isEmpty()
                && !txtEDiaChi.getText().toString().isEmpty() && !edTEmail.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    private boolean kiemTraCheckBox(){
        if (radioButtonNam.isChecked() || radioButtonNu.isChecked()){
            return true;
        } else {
            return false;
        }
    }

}
