package com.example.cuahangthietbionline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.model.Sanpham;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraylaptop;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arraylaptop) {
        this.context = context;
        this.arraylaptop = arraylaptop;
    }

    @Override
    public int getCount() {
        return arraylaptop.size();
    }

    @Override
    public Object getItem(int position) {

        return arraylaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView texttenlaptop,txtgialaptop,txtmotalaptop;
        public ImageView imageViewlaptop;



    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder= null;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_laptop,null);
            viewHolder.texttenlaptop = convertView.findViewById(R.id.textviewlaptop);
            viewHolder.txtgialaptop = convertView.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmotalaptop = convertView.findViewById(R.id.textviewmotalaptop);
            viewHolder.imageViewlaptop = convertView.findViewById(R.id.imageviewlaptop);
            convertView.setTag(viewHolder);
        }
        else {

            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);


        viewHolder.texttenlaptop.setText(sanpham.getTensanpham());
        viewHolder.texttenlaptop.setMaxLines(2);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgialaptop.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham())+" Đ");
        viewHolder.txtmotalaptop.setMaxLines(5);
        viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        Glide.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.common_full_open_on_phone)
                .error(R.drawable.googleg_disabled_color_18)
                .into(viewHolder.imageViewlaptop);

        return convertView;
    }
}
