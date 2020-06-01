package com.example.shippers.Customer.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shippers.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantAdapter  extends ArrayAdapter<String> {
    private Activity contextActivity;
    private ArrayList<String> restaurantImage; //URL
    private ArrayList<String> restaurantName;
    private ArrayList<String> restaurantStatus;
    private ArrayList<String> restaurantOpenTime;
    private ArrayList<String> restaurantCloseTime;
    public RestaurantAdapter(Activity contextActivity, ArrayList<String> restaurantImage,
                             ArrayList<String> restaurantName, ArrayList<String> restaurantStatus,
                             ArrayList<String> restaurantOpenTime, ArrayList<String> restaurantCloseTime)
    {
        super(contextActivity, R.layout.restaurant_frame, restaurantStatus);
        this.contextActivity = contextActivity;
        this.restaurantImage = restaurantImage;
        this.restaurantName = restaurantName;
        this.restaurantStatus = restaurantStatus;
        this.restaurantOpenTime = restaurantOpenTime;
        this.restaurantCloseTime = restaurantCloseTime;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = contextActivity.getLayoutInflater(); //Gán vào giao diện chính
        View rowValue = layoutInflater.inflate(R.layout.restaurant_frame, null, true);
        //Ánh xạ
        ImageView rImage = (ImageView) rowValue.findViewById(R.id.restaurantImage);
        TextView rName = (TextView) rowValue.findViewById(R.id.restaurantName);
        TextView rStatus = (TextView) rowValue.findViewById(R.id.restaurantStatus);
        TextView rOpen = (TextView) rowValue.findViewById(R.id.restaurantOpenTime);
        TextView rClose = (TextView) rowValue.findViewById(R.id.restaurantCloseTime);
        //Set image
        Picasso.get().load(this.restaurantImage.get(position)).into(rImage);
        //Set text
        rName.setText(String.valueOf(this.restaurantName.get(position)));
        rStatus.setText(String.valueOf(this.restaurantStatus.get(position)));
        rOpen.setText(String.valueOf(this.restaurantOpenTime.get(position)));
        rClose.setText(String.valueOf(this.restaurantCloseTime.get(position)));
        return rowValue;
    }
}
