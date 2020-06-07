package com.example.lalafood.Shippers.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.lalafood.Shippers.Adapter.OrdersFragmentAdapter;
import com.example.lalafood.Shippers.Fragment.FragmentGetOrder;
import com.example.lalafood.Shippers.Fragment.FragmentOption;
import com.example.lalafood.Shippers.Fragment.FragmentOrders;
import com.example.lalafood.Shippers.Fragment.FragmentPickUp;
import com.example.lalafood.R;
import com.google.android.material.tabs.TabLayout;

///Summary
///+Không thể truyền dữ liệu theo phương thức khởi tạo, chỉ có thể truyền bằng intent
///+Các form thay đổi đều trả về giao diện chính. Hiện tại chỉ có 1 activity cho tất cả
///End summary
public class ShippersMainActivity extends AppCompatActivity {
    //
    public static final String ACCOUNT = "ACCOUNT";
    public static final String RESET_ORDER_ADAPTER = "RESET_ORDER_ADAPTER";
    //Intent
    private String orderStatus = "Đã nhận";

    //Các biến dùng chung
    TabLayout tabOrders;
    ViewPager viewPagerOrders;
    Menu orderType;
    //Action Bar
    private ActionBar actionBar;
    //
    public Boolean isPickUp;
    private Boolean moreInfoOrder;

    OrdersFragmentAdapter ordersFragmentAdapter;
    public Context context = this;
    private String[] pickUpAddress = {"Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6",
            "Quận 7","Quận 8","Quận 9"};
    private String[] shipAddress = {"Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6",
            "Quận 7","Quận 8","Quận 9"};


    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final TabLayout tabOrders = (TabLayout) findViewById(R.id.tabOrders);
        final ViewPager viewPagerOrders = (ViewPager) findViewById(R.id.ordersViewPager);
        //Sau khi inflate thì menu sẽ hiểu được menu hiện tại
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.order_type, menu);

        //Trang pick up ở đầu nên không có order type
        //Cứ set các Item Invisible là Menu sẽ Invisible
        setVisibleMenu(menu, false);

        //
        actionBar = getSupportActionBar();
        actionBar.setTitle("Nhận hàng"); //7 tab
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.test_layout);
        viewPagerOrders.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //0 -> 1 > 2
//                Pickup/Get Order -> Orders -> Options

                switch (position)
                {
                    case 0:
                        setVisibleMenu(menu, false);
                        actionBar.setTitle("Nhận hàng"); //7 tab
                        break;
                    case 1:
                        //Tạo menu
                        setVisibleMenu(menu, true);
                        actionBar.setTitle("Đơn hàng"); //7 tab
                        break;
                    case 2:
                        setVisibleMenu(menu, false);
                        actionBar.setTitle("Cài đặt"); //8 tab
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        final ActionBar actionBar = getSupportActionBar();
        //
//        final OrdersAdapter ordersAdapter = new OrdersAdapter(this, pickUpAddress, shipAddress);
//        final ListView ordersList = (ListView) findViewById(R.id.ordersListId);
//        ordersList.setAdapter(ordersAdapter);
//        //
//        Switch workingSwitch = (Switch) findViewById(R.id.workingSwitch);
//        workingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    ordersList.setAdapter(ordersAdapter);
//                }
//                else
//                {
//                    ordersList.removeAllViewsInLayout();
//                    Toast.makeText(context, "Ứng dụng đang nghĩ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        TabLayout tabOrders = (TabLayout) findViewById(R.id.tabOrders);
        ViewPager viewPagerOrders = (ViewPager) findViewById(R.id.ordersViewPager);

//      //Tạo 1 intent để get data
        Intent getData = getIntent();
        isPickUp = getData.getBooleanExtra(FragmentPickUp.IS_PICKUP, false); //Nếu chưa get được mặc định là là trang chủ
        moreInfoOrder = getData.getBooleanExtra(FragmentOrders.MORE_INFO_ORDER, false);
        //resetOrderAdapter = getData.getIntExtra(ShippersMainActivity.RESET_ORDER_ADAPTER, 0);
        //Lấy account từ login password

        if(this.isPickUp == true) //Get được data
        {
            actionBar.setDisplayHomeAsUpEnabled(true);

            ordersFragmentAdapter = new OrdersFragmentAdapter(getSupportFragmentManager());
            ordersFragmentAdapter.addFragment(new FragmentGetOrder(), "Nhận hàng");
            ordersFragmentAdapter.addFragment(new FragmentOrders(), "Đơn hàng");
            ordersFragmentAdapter.addFragment(new FragmentOption(), "Cài đặt");
            viewPagerOrders.setAdapter(ordersFragmentAdapter);
            tabOrders.setupWithViewPager(viewPagerOrders);

        }
        else if(this.moreInfoOrder == true)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);

            ordersFragmentAdapter = new OrdersFragmentAdapter(getSupportFragmentManager());
            ordersFragmentAdapter.addFragment(new FragmentPickUp(), "Nhận hàng");
            ordersFragmentAdapter.addFragment(new FragmentGetOrder(), "Đơn hàng");
            ordersFragmentAdapter.addFragment(new FragmentOption(), "Cài đặt");
            viewPagerOrders.setAdapter(ordersFragmentAdapter);
            viewPagerOrders.setCurrentItem(1); //Nhảy đến vị trí xem đơn hàng
            tabOrders.setupWithViewPager(viewPagerOrders);
        }
        else
        {
            actionBar.setDisplayHomeAsUpEnabled(false); //Nếu là trang chủ thì không có nút quay lại

            ordersFragmentAdapter = new OrdersFragmentAdapter(getSupportFragmentManager());
            ordersFragmentAdapter.addFragment(new FragmentPickUp(), "Nhận hàng");
            ordersFragmentAdapter.addFragment(new FragmentOrders(), "Đơn hàng");
            ordersFragmentAdapter.addFragment(new FragmentOption(), "Cài đặt");
            viewPagerOrders.setAdapter(ordersFragmentAdapter);
            tabOrders.setupWithViewPager(viewPagerOrders);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        //Nút quay lại
        if(id == android.R.id.home)
        {
            //Kết thúc hiện tại
            this.finish();
            //Reset lại trang chính
            Intent intent = new Intent(context, ShippersMainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    //Ẩn/hiện menu
    public void setVisibleMenu(Menu menu, boolean isVisible)
    {
        for(int i = 0; i < menu.size(); i++)
        {
            menu.getItem(i).setVisible(isVisible);
        }
    }

}
