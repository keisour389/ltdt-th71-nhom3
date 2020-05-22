package com.example.apporder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DangNhapKhachHang extends AppCompatActivity {
    private EditText editTextSdt;

    private Button btnTiepTheo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap_khach_hang);

        btnTiepTheo = (Button) findViewById(R.id.btnTiepTheo);
        editTextSdt = (EditText) findViewById(R.id.editTextSdt);

        //click Kết nối với số điện thoại
        btnTiepTheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraRong() == true) {
                    Intent TV = new Intent(DangNhapKhachHang.this, MatKhauKhachHang.class);
                    startActivity(TV);
                }
            }
        });
    }
    private boolean kiemTraRong() {
        if (!editTextSdt.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
