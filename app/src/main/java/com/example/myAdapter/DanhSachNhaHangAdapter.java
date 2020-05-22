package com.example.myAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.FoodOrder.R;

public class DanhSachNhaHangAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final Integer[] imgid;

    public DanhSachNhaHangAdapter(Activity context, String[] maintitle, String[] subtitle, Integer[] imgid)
    {
        super(context, R.layout.nha_hang, maintitle);
        this.context = context;
        this.maintitle = maintitle;
        this.subtitle = subtitle;
        this.imgid = imgid;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.nha_hang, null, true);
        TextView title =  row.findViewById(R.id.rest_name);
        TextView sub_title =  row.findViewById(R.id.rest_address);
        ImageView img = row.findViewById(R.id.rest_image);

        title.setText(maintitle[position]);
        sub_title.setText(subtitle[position]);
        img.setImageResource(imgid[position]);
        return row;
    }



}
