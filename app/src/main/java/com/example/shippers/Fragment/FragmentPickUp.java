package com.example.shippers.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shippers.GetOrderActivity;
import com.example.shippers.OrdersAdapter;
import com.example.shippers.R;

public class FragmentPickUp extends Fragment {
    View view;
    private String[] pickUpAddress = {"Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6",
            "Quận 7","Quận 8","Quận 9"};
    private String[] shipAddress = {"Quận 1", "Quận 2", "Quận 3", "Quận 4", "Quận 5", "Quận 6",
            "Quận 7","Quận 8","Quận 9"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pick_up, container, false);
        //
        final OrdersAdapter ordersAdapter = new OrdersAdapter(getActivity(), pickUpAddress, shipAddress);
        final ListView ordersList = (ListView) view.findViewById(R.id.ordersListId);
        ordersList.setAdapter(ordersAdapter);
        //
        Switch workingSwitch = (Switch) view.findViewById(R.id.workingSwitch);
        workingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    ordersList.setAdapter(ordersAdapter);
                }
                else
                {
                    ordersList.removeAllViewsInLayout();
                    Toast.makeText(getContext(), "Ứng dụng đang nghĩ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //
        ordersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent pickUpIntent = new Intent(getActivity(), GetOrderActivity.class);
                startActivity(pickUpIntent);

            }
        });
        return view;
    }
}
