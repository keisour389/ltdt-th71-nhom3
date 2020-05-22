package com.example.myAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.FoodOrder.R;

public class CategoryAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] subtitle;
    private final Integer[] imgid;

    public CategoryAdapter(Activity context, String[] subtitle, Integer[] imgid)
    {
        super(context, R.layout.category, subtitle);
        this.context = context;
        this.subtitle = subtitle;
        this.imgid = imgid;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.category, null, true);
        TextView sub_title =  row.findViewById(R.id.categoryName);
        ImageView img = row.findViewById(R.id.categoryPic);

        sub_title.setText(subtitle[position]);
        img.setImageResource(imgid[position]);
        return row;
    }

}
