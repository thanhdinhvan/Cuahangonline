package com.example.cuahangthietbionline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.model.Loaisp;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> arrayListloaisp ;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> arrayListloaisp, Context context) {
        this.arrayListloaisp = arrayListloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListloaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListloaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public  class  ViewHolder{
    TextView txttenloaisp;
    ImageView imgloaisp;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.txttenloaisp = convertView.findViewById(R.id.textviewloaisp);
            viewHolder.imgloaisp = convertView.findViewById(R.id.imageviewloaisp);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();

        }
        Loaisp loaisp = (Loaisp) getItem(position);
        viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
        Glide.with(context).load(loaisp.getHinhanhloaisp())
                .placeholder(R.drawable.common_google_signin_btn_icon_light)
                .error(R.drawable.common_google_signin_btn_icon_dark)
                .into(viewHolder.imgloaisp);

        return convertView;
    }
}
