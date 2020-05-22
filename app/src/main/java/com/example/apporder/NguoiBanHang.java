package com.example.apporder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class NguoiBanHang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_ban_hang);

    }


    // bắt sự kiện khi nhấn vào menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_nguoi_ban, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        // Lựa chọn item cần chọn
        switch (item.getItemId()){
            case R.id.menuTaoNhaHang:
                Intent tnh = new Intent(NguoiBanHang.this, TenNhaHang_DiaChi_NBH.class);
                startActivity(tnh);
                break;
            case R.id.menuQuanLyNhaHang:
                    Intent qlnh = new Intent(NguoiBanHang.this,QuanLyNhaHang.class);
                startActivity(qlnh);
                break;
            case R.id.menuCaiDat:
                break;
            case R.id.menuThongTinDangNhap:
                break;
            case R.id.menuThayDoiNgonNgu:
                break;
            case R.id.menuDangXuat:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
