package com.example.shippers;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class OrdersAdapter extends ArrayAdapter<String> {
    private Activity contextActivity;
    private ArrayList<Integer> orderId;
    private ArrayList<String> pickUpAddress;
    private ArrayList<String> shipAddress;
    private ArrayList<String> note;
    private String textTitle;
    private String textTilteColor;

    public OrdersAdapter(Activity contextActivity,
                         ArrayList<Integer> orderId,
                         ArrayList<String> pickUpAddress,
                         ArrayList<String> shipAddress,
                         ArrayList<String> note,
                         String textTitle,
                         String textTilteColor)
    {
        super(contextActivity, R.layout.orders_frame, pickUpAddress); //Trả thử về pickUpAddress
        //Get values
        this.contextActivity = contextActivity;
        this.orderId = orderId;
        this.note = note;
        this.pickUpAddress = pickUpAddress;
        this.shipAddress = shipAddress;
        this.textTitle = textTitle;
        this.textTilteColor = textTilteColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = contextActivity.getLayoutInflater(); //Gán vào giao diện chính
        View rowValue = layoutInflater.inflate(R.layout.orders_frame, null, true);
        //Ánh xạ
        TextView titleOrders =  (TextView) rowValue.findViewById(R.id.titleOrders);
        TextView pAddress = (TextView) rowValue.findViewById(R.id.pickUpAddressId);
        TextView sAddress = (TextView) rowValue.findViewById(R.id.shipAddressId);
        TextView oId = (TextView) rowValue.findViewById(R.id.ordersId);
        TextView nOte = (TextView) rowValue.findViewById(R.id.ordersNoteId);
        //Set title
        titleOrders.setText(textTitle);
        titleOrders.setBackgroundColor(Color.parseColor(textTilteColor)); //Parse color
        //Set body
        oId.setText("Mã đơn hàng: " + orderId.get(position).toString()); //toString vì id là số
        pAddress.setText("Nhận hàng: " + pickUpAddress.get(position));
        sAddress.setText("Giao hàng: " + shipAddress.get(position));
        nOte.setText("Ghi chú: " + note.get(position));
        //
        return rowValue;
    }
}