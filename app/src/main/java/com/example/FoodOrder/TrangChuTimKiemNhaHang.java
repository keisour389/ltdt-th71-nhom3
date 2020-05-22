package com.example.FoodOrder;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.fragments.FragmentNgonNgu;
import com.example.helper.LocaleHelper;
import com.example.myAdapter.DanhSachNhaHangAdapter;

public class TrangChuTimKiemNhaHang extends AppCompatActivity {
    private ListView listProduct; //Khai báo listView
    String[] mainTitle = {
            "Sản phẩm 1", "Sản phẩm 2",
            "Sản phẩm 3", "Sản phẩm 4",
            "Sản phẩm 5",
    };
    //Tên các Nhà hàng
    String[] subTitle = {
            "Sub1", "Sub2",
            "Sub3", "Sub4",
            "Sub5",
    };
    //Giới thiệu ngắn gọn
    Integer[] imgId = {
            R.drawable.stretch_vertical ,  R.drawable.stretch_vertical_2,
            R.drawable.stretch_vertical_3,  R.drawable.stretch_vertical_4,
            R.drawable.stretch_vertical_5,
    };
    //Hình ảnh
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu_tim_kiem_nha_hang);

        listProduct = findViewById(R.id.listProduct);
        //Bind listView với listView ở DanhSachNhaHangAdapter
        DanhSachNhaHangAdapter resAdapter = new DanhSachNhaHangAdapter(this, mainTitle, subTitle, imgId);
        listProduct.setAdapter(resAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){ // Inflate menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_change_address:
                return true;
            case R.id.action_change_language:
                DialogFragment langFrag = FragmentNgonNgu.newInstance();
                langFrag.show(getSupportFragmentManager(), "tag");
                return true;
            case R.id.action_check_order:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
