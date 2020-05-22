package com.example.FoodOrder;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.fragments.FragmentNgonNgu;
import com.example.fragments.FragmentSoLuongMonAn;
import com.example.myAdapter.DanhSachDoAnAdapter;

public class TrangDoAn extends AppCompatActivity {
    private ListView listFood; //Khai báo listView
    private Toolbar toolBar;
    private Button btnOrder;
    String[] mainTitle = {
            "Sản phẩm 1", "Sản phẩm 2",
            "Sản phẩm 3", "Sản phẩm 4",
            "Sản phẩm 5",
    };
    //Tên các món ăn
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
    //Hình các món ăn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_do_an);

        listFood = findViewById(R.id.list_food);
        DanhSachDoAnAdapter foodAdapter = new DanhSachDoAnAdapter(this, mainTitle, subTitle, imgId);
        listFood.setAdapter(foodAdapter);
        listFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogFragment foodFrag = FragmentSoLuongMonAn.newInstance();
                foodFrag.show(getSupportFragmentManager(), "tag");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
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

}
