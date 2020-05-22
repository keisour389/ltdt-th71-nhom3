package com.example.apporder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ThucDonAdapter extends BaseAdapter {
    // cần Activy bên Quản lý nhà hàng để có thể truy cập được DialogSua
    private QuanLyNhaHang context;
    private int layout;
    private List<ThucDon> thucDonList;

    public ThucDonAdapter(QuanLyNhaHang context, int layout, List<ThucDon> thucDonList) {
        this.context = context;
        this.layout = layout;
        this.thucDonList = thucDonList;
    }

    @Override
    public int getCount() {
        return thucDonList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView textViewTenMon, textViewMoTa;
        ImageView Delete, UpDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if ( convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.textViewTenMon = (TextView) convertView.findViewById(R.id.textViewTenMon);
            //holder.textViewMoTa   = (TextView) convertView.findViewById(R.id.textViewMoTa);
            holder.UpDate         = (ImageView) convertView.findViewById(R.id.UpDate);
            holder.Delete         = (ImageView) convertView.findViewById(R.id.Delete);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ThucDon thucDon = thucDonList.get(position);
        holder.textViewTenMon.setText(thucDon.getTenMon());
        //holder.textViewMoTa.setText(thucDon.getMoTa());
        // Bắt sự kiện sửa
        holder.UpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSua(thucDon.getTenMon(), thucDon.getId());
            }
        });
        // Bắt sự kiện xóa
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoa(thucDon.getTenMon(), thucDon.getId());
            }
        });

        return convertView;
    }
}
