package com.example.lalafood.Customers.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lalafood.R;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<Integer> {
    private Activity contextActivity;
    private ArrayList<String> foodName;
    private ArrayList<Integer> foodPrice;
    private ArrayList<String> foodUnit;
    //Vẫn có thể truyền ảnh onl, nhưng xài ảnh mặc định
    public  FoodAdapter(Activity contextActivity, ArrayList<String> foodName,
                        ArrayList<Integer> foodPrice, ArrayList<String> foodUnit)
    {
        super(contextActivity, R.layout.food_frame, foodPrice); //Tạm thời trả về giá
        this.contextActivity = contextActivity;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodUnit = foodUnit;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = contextActivity.getLayoutInflater(); //Gán vào giao diện chính
        View rowValue = layoutInflater.inflate(R.layout.food_frame, null, true);
        //Ánh xạ
        TextView fName = (TextView) rowValue.findViewById(R.id.foodName);
        TextView foodPrice = (TextView) rowValue.findViewById(R.id.foodPrice);
        TextView foodUnit = (TextView) rowValue.findViewById(R.id.foodUnit);
        //Set text
        fName.setText(String.valueOf(this.foodName.get(position)));
        foodPrice.setText("Giá tiền: " + String.valueOf(this.foodPrice.get(position)) + "đ");
        foodUnit.setText("Đơn vị: " + String.valueOf(this.foodUnit.get(position)));
        return rowValue;
    }
}
