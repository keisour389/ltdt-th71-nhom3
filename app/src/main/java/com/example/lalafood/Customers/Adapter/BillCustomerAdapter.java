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

public class BillCustomerAdapter extends ArrayAdapter<Integer>
{
    private Activity contextActivity;
    private ArrayList<String> foodName;
    private ArrayList<Integer> foodAmount;
    private ArrayList<Integer> foodPrice;
    public BillCustomerAdapter(Activity contextActivity, ArrayList<String> foodName,ArrayList<Integer> foodAmount,
                               ArrayList<Integer> foodPrice)
    {
        super(contextActivity, R.layout.customers_bill_order_completed, foodPrice); //Trả thử về pickUpAddress
        this.contextActivity = contextActivity;
        this.foodName = foodName;
        this.foodAmount = foodAmount;
        this.foodPrice = foodPrice;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = contextActivity.getLayoutInflater(); //Gán vào giao diện chính
        View rowValue = layoutInflater.inflate(R.layout.customers_bill_order_completed, null, true);
        //Ánh xạ
        TextView fName = (TextView) rowValue.findViewById(R.id.foodNameInCusOrder);
        TextView fAmount = (TextView) rowValue.findViewById(R.id.foodAmountInCusOrder);
        TextView fPrice = (TextView) rowValue.findViewById(R.id.foodPriceInCusOrder);
        //Set text
        fName.setText(this.foodName.get(position));
        fAmount.setText(String.valueOf(this.foodAmount.get(position)));
        fPrice.setText(String.valueOf(this.foodPrice.get(position)));
        return rowValue;
    }
}
