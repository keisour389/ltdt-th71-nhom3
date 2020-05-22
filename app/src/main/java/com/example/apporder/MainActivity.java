package com.example.apporder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnDangNhapSdt, btnDangKy, btnNguoiBan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDangNhapSdt = (Button) findViewById(R.id.btnDangNhapSdt);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        btnNguoiBan = (Button) findViewById(R.id.btnNguoiBan);




        btnDangNhapSdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TV = new Intent(MainActivity.this, DangNhapKhachHang.class);
                startActivity(TV);

            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TV = new Intent(MainActivity.this, DangKySdt.class);
                startActivity(TV);

            }
        });

        btnNguoiBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TV = new Intent(MainActivity.this, NguoiBanHang.class);
                startActivity(TV);

            }
        });

    }
}
