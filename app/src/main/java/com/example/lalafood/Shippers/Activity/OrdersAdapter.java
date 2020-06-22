package com.example.lalafood.Shippers.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lalafood.Helper.LocaleHelper;
import com.example.lalafood.R;

import java.util.ArrayList;

public class OrdersAdapter extends ArrayAdapter<String> {
    private Activity contextActivity;
    private ArrayList<Integer> orderId;
    private ArrayList<String> pickUpAddress;
    private ArrayList<String> shipAddress;
    private ArrayList<String> note;
    private ArrayList<Integer> shipFee;
    private String textTitle;
    private String textTitleColor;


    //Phương thức khởi tạo của khách hàng
    public OrdersAdapter(Activity contextActivity,
                         ArrayList<Integer> orderId,
                         ArrayList<String> pickUpAddress,
                         ArrayList<String> shipAddress,
                         ArrayList<String> note,
                         String textTitle,
                         String textTitleColor)
    {
        super(contextActivity, R.layout.orders_frame, pickUpAddress); //Trả thử về pickUpAddress
        //Get values
        this.contextActivity = contextActivity;
        this.orderId = orderId;
        this.note = note;
        this.pickUpAddress = pickUpAddress;
        this.shipAddress = shipAddress;
        this.textTitle = textTitle;
        this.textTitleColor = textTitleColor;
    }

    //Phương thức khỏi tạo của shippers
    public OrdersAdapter(Activity contextActivity,
                         ArrayList<Integer> orderId,
                         ArrayList<String> pickUpAddress,
                         ArrayList<String> shipAddress,
                         ArrayList<String> note,
                         ArrayList<Integer> shipFee,
                         String textTitle,
                         String textTitleColor)
    {
        super(contextActivity, R.layout.orders_frame, pickUpAddress); //Trả thử về pickUpAddress
        //Get values
        this.contextActivity = contextActivity;
        this.orderId = orderId;
        this.note = note;
        this.pickUpAddress = pickUpAddress;
        this.shipAddress = shipAddress;
        this.shipFee = shipFee;
        this.textTitle = textTitle;
        this.textTitleColor = textTitleColor;
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
        TextView shipCost = (TextView) rowValue.findViewById(R.id.ordersPriceId);
        //Set title
        titleOrders.setText(textTitle);
        titleOrders.setBackgroundColor(Color.parseColor(textTitleColor)); //Parse color
        //Set strings
        String order_id = contextActivity.getResources().getString(R.string.order_id);
        String take_order = contextActivity.getResources().getString(R.string.take_order);
        String ship_to = contextActivity.getResources().getString(R.string.ship_to);
        String notes = contextActivity.getResources().getString(R.string.note);
        //Set body
        oId.setText(order_id + ": " + orderId.get(position).toString()); //toString vì id là số
        pAddress.setText(take_order + ": " + pickUpAddress.get(position));
        sAddress.setText(ship_to + ": " + shipAddress.get(position));
        nOte.setText(notes + ":  " + note.get(position));
        shipCost.setText(String.format("%s VNĐ", shipFee.get(position).toString())); //Kiểm tra là nhận hàng
        //
        return rowValue;
    }
}
