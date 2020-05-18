package com.example.shippers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shippers.Adapter.OrdersFragmentAdapter;
import com.example.shippers.Fragment.FragmentOption;
import com.example.shippers.Fragment.FragmentOrders;
import com.example.shippers.Fragment.FragmentPickUp;
import com.google.android.material.tabs.TabLayout;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.TooManyListenersException;

public class MainActivity extends AppCompatActivity {
    private Context context = this;
    private String[] pickUpAddress = {"Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6",
            "Quận 7","Quận 8","Quận 9"};
    private String[] shipAddress = {"Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6",
            "Quận 7","Quận 8","Quận 9"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//        //
        TabLayout tabOrders = (TabLayout) findViewById(R.id.tabOrders);
        ViewPager viewPagerOrders = (ViewPager) findViewById(R.id.ordersViewPager);
        OrdersFragmentAdapter ordersFragmentAdapter = new OrdersFragmentAdapter(getSupportFragmentManager());
        ordersFragmentAdapter.addFragment(new FragmentPickUp(), "Nhận hàng");
        ordersFragmentAdapter.addFragment(new FragmentOrders(), "Đơn hàng");
        ordersFragmentAdapter.addFragment(new FragmentOption(), "Cài đặt");
        viewPagerOrders.setAdapter(ordersFragmentAdapter);
        tabOrders.setupWithViewPager(viewPagerOrders);
    }

}
