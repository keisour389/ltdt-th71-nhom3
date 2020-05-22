package com.example.apporder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class DangKyMatKhau extends AppCompatActivity {
    private Button btnTiepTheoDangKyForm;
    private TextInputEditText txtEMatKhauDangKy, txtENhapLaiMatKhauDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_mat_khau);

        btnTiepTheoDangKyForm = (Button) findViewById(R.id.btnTiepTheoDangKyForm);
        txtEMatKhauDangKy = (TextInputEditText) findViewById(R.id.txtEMatKhauDangKy);
        txtENhapLaiMatKhauDangKy = (TextInputEditText) findViewById(R.id.txtENhapLaiMatKhauDangKy);


        btnTiepTheoDangKyForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (kiemTraRong() == true && kiemTraGiongNhau() == true) {
                    Intent TV = new Intent(DangKyMatKhau.this, FormDangKy.class);
                    startActivity(TV);
                }

            }
        });
        //Báo lỗi cho ô Mật khẩu
        txtEMatKhauDangKy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtEMatKhauDangKy.getText().toString().isEmpty()) {
                    txtEMatKhauDangKy.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });

        //Báo lỗi cho ô Nhập lại mật khẩu
        txtENhapLaiMatKhauDangKy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtENhapLaiMatKhauDangKy.getText().toString().isEmpty()) {
                    txtENhapLaiMatKhauDangKy.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });
    }


    // Kiểm tra 2 ô dữ liệu có rỗng hay 0.
    private boolean kiemTraRong() {
        if (!txtEMatKhauDangKy.getText().toString().isEmpty() && !txtENhapLaiMatKhauDangKy.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean kiemTraGiongNhau() {
        if (txtEMatKhauDangKy.getText().toString().equals(txtENhapLaiMatKhauDangKy.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
