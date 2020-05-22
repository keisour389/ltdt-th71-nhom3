package com.example.FoodOrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.fragments.FragmentNgonNgu;
import com.example.helper.LocaleHelper;
import com.example.myAdapter.CategoryAdapter;
import com.example.myAdapter.CustomPagerAdapter;


public class TrangChuChuaTimKiem extends AppCompatActivity {
    private GridView categoryGroup;//Khởi tạo gridview
    String[] subTitle = {
            "Sản phẩm 1", "Sản phẩm 2",
            "Sản phẩm 3", "Sản phẩm 4",
            "Sản phẩm 5", "Sản phẩm 6",
            "Sản phẩm 7", "Sản phẩm 8"
    };
    //Tên category
    Integer[] imgId = {
            R.drawable.stretch_vertical ,  R.drawable.stretch_vertical_2,
            R.drawable.stretch_vertical_3, R.drawable.stretch_vertical_4,
            R.drawable.stretch_vertical_5, R.drawable.stretch_vertical_5,
            R.drawable.stretch_vertical_5, R.drawable.stretch_vertical_5,
    };
    //Hình category

    public enum  CustomPagerEnum{
        RED(R.string.red, R.layout.view_red),
        BLUE(R.string.blue, R.layout.view_blue),
        ORANGE(R.string.orange, R.layout.view_orange);

        private int mTitleResId;
        private int mLayoutResId;

        CustomPagerEnum(int titleResId, int layoutResId) {
            mTitleResId = titleResId;
            mLayoutResId = layoutResId;
        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_chu_chua_tim_kiem);
        //Khởi tạo viewpager
        ViewPager viewpager = findViewById(R.id.viewpager_promotions);
        viewpager.setAdapter(new CustomPagerAdapter(this));
        //Các intent
        Context mContext = getApplicationContext();
        //Bind các category vào layout
        CategoryAdapter adapter = new CategoryAdapter(this, subTitle, imgId);
        categoryGroup = findViewById(R.id.category_group);
        categoryGroup.setAdapter(adapter);
        categoryGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Intent rest = new Intent(TrangChuChuaTimKiem.this, TrangChuTimKiemNhaHang.class);
                    startActivity(rest);
                }
            }
        });
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

//    public void languageSelect(String lang)
//    {
//        Locale locale = new Locale(lang); //Chọn locale theo biến lang
//        Configuration config = new Configuration();
//        config.locale = locale; //Set locale cho activity
//        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//        //Cập nhật ngôn ngữ đã chọn ở trên
//        Intent intent = new Intent(TrangChuChuaTimKiem.this, TrangChuChuaTimKiem.class); // Khởi động lại activity
//        startActivity(intent);
//    }
}
