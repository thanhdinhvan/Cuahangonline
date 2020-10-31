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

public class DienthoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraydienthoai;
    private static final String TAG = "DienthoaiAdapter";
    public DienthoaiAdapter(Context context, ArrayList<Sanpham> arraydienthoai) {
        this.context = context;
        this.arraydienthoai = arraydienthoai;
    }

    @Override
    public int getCount() {
        return arraydienthoai.size();
    }

    @Override
    public Object getItem(int position) {

        return arraydienthoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView texttendienthoai,txtgiadienthoai,txtmotadienthoai;
        public ImageView imageViewdienthoai;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder= null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dienthoai,null);
            viewHolder.texttendienthoai = convertView.findViewById(R.id.textviewdienthoai);
            viewHolder.txtgiadienthoai = convertView.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtmotadienthoai = convertView.findViewById(R.id.textviewmotadienthoai);
            viewHolder.imageViewdienthoai = convertView.findViewById(R.id.imageviewdienthoai);
            convertView.setTag(viewHolder);



        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final TextView txtGia = viewHolder.txtgiadienthoai;
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.texttendienthoai.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        new Thread(()-> {
            Log.d(TAG, "getView: thread "+Thread.currentThread().getName());
            txtGia.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham())+" Đ");
        });
        viewHolder.txtmotadienthoai.setMaxLines(2);
        viewHolder.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        Glide.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.common_full_open_on_phone)
                .error(R.drawable.googleg_disabled_color_18)
                .into(viewHolder.imageViewdienthoai);

        return convertView;
    }
}
