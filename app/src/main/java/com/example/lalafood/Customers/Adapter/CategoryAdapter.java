package com.example.lalafood.Customers.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.lalafood.R;

public class CategoryAdapter extends ArrayAdapter<Integer> {
    private final Activity context;
    private final Integer[] imgid;

    public CategoryAdapter(Activity context, Integer[] imgid)
    {
        super(context, R.layout.food_categories, imgid);
        this.context = context;
        this.imgid = imgid;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.food_categories, null, true);
        ImageView img = row.findViewById(R.id.categoryPic);

        img.setImageResource(imgid[position]);
        return row;
    }

}
