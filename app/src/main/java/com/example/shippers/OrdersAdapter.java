package com.example.shippers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OrdersAdapter extends ArrayAdapter<String> {
    private Activity contextActivity;
    private String[] pickUpAddress;
    private String[] shipAddress;

    public OrdersAdapter(Activity contextActivity,
                         String[] pickUpAddress,
                         String[] shipAddress)
    {
        super(contextActivity, R.layout.orders_frame, pickUpAddress); //Trả thử về pickUpAddress
        //Get values
        this.contextActivity = contextActivity;
        this.pickUpAddress = pickUpAddress;
        this.shipAddress = shipAddress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = contextActivity.getLayoutInflater(); //Gán vào giao diện chính
        View rowValue = layoutInflater.inflate(R.layout.orders_frame, null, true);
        //
        TextView pAddress = (TextView) rowValue.findViewById(R.id.pickUpAddressId);
        TextView sAddress = (TextView) rowValue.findViewById(R.id.shipAddressId);
        //
        pAddress.setText(pickUpAddress[position]);
        sAddress.setText(shipAddress[position]);
        //
        return rowValue;
    }
}
