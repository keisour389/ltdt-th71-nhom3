package com.example.apporder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class TenNhaHang_DiaChi_NBH extends AppCompatActivity {
    private TextInputEditText txtETenNhaHang;
    private TextInputEditText txtEDiaChiNhaHang;
    private Button btnTiepTheoBanHang;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten_nha_hang__dia_chi__n_b_h);
        txtETenNhaHang = (TextInputEditText) findViewById(R.id.txtETenNhaHang);
        txtEDiaChiNhaHang = (TextInputEditText) findViewById(R.id.txtEDiaChiNhaHang);
        btnTiepTheoBanHang = (Button) findViewById(R.id.btnTiepTheoBanHang);


        btnTiepTheoBanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraRong() == true) {
                    Intent TV = new Intent(TenNhaHang_DiaChi_NBH.this, QuanLyNhaHang.class);
                    startActivity(TV);
                }
            }
        });


        //Báo lỗi cho ô Địa chỉ nhà hàng
        txtEDiaChiNhaHang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtEDiaChiNhaHang.getText().toString().isEmpty()) {
                    txtEDiaChiNhaHang.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });

        //Báo lỗi cho ô Tên nhà hàng
        txtETenNhaHang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtETenNhaHang.getText().toString().isEmpty()) {
                    txtETenNhaHang.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });
    }
    private boolean kiemTraRong() {
        if (!txtEDiaChiNhaHang.getText().toString().isEmpty() && !txtETenNhaHang.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
