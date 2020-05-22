package com.example.apporder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DangKySdt extends AppCompatActivity {
    private Button btnTiepTheoDangKyMK;
    private EditText editTextSdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_sdt);

        btnTiepTheoDangKyMK = (Button) findViewById(R.id.btnTiepTheoDangKyMK);
        editTextSdt = (EditText) findViewById(R.id.editTextSdt);

        btnTiepTheoDangKyMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraRong() == true) {
                    Intent TV = new Intent(DangKySdt.this, DangKyMatKhau.class);
                    startActivity(TV);
                }
            }
        });
        editTextSdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editTextSdt.getText().toString().isEmpty()) {
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
