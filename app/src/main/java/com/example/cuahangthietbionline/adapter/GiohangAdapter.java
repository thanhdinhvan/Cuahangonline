package com.example.cuahangthietbionline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.activity.MainActivity;
import com.example.cuahangthietbionline.model.Giohang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> listgiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> listgiohang) {
        this.context = context;
        this.listgiohang = listgiohang;
    }

    @Override
    public int getCount() {
        return listgiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return listgiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
    public TextView txttengiohang,txtgiagiohang;
    public ImageView imggiohang;
    public Button btntru,btngiatri,btncong;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder= null;
        if( convertView ==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_gio_hang,null);
            viewHolder.txttengiohang = convertView.findViewById(R.id.textviewgiohang);
            viewHolder.txtgiagiohang = convertView.findViewById(R.id.textgiatrimonhang);
            viewHolder.imggiohang=convertView.findViewById(R.id.imagegiohang);
            viewHolder.btncong  = convertView.findViewById(R.id.idbuttoncong);
            viewHolder.btntru = convertView.findViewById(R.id.idbuttontru);
            viewHolder.btngiatri = convertView.findViewById(R.id.idbuttongiatri);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText((decimalFormat.format(giohang.getGia()))+"Đ");
        Glide.with(context).load(giohang.getHinhAnhSanPham())
                .placeholder(R.drawable.common_full_open_on_phone)
                .error(R.drawable.googleg_standard_color_18)
                .into(viewHolder.imggiohang);
        viewHolder.btngiatri.setText(giohang.getSoLuong() + "");
        int sl = Integer.parseInt(viewHolder.btngiatri.getText().toString());
        if (sl>=10){
            viewHolder.btncong.setVisibility(convertView.INVISIBLE);
            viewHolder.btntru.setVisibility(convertView.VISIBLE);

        }else if(sl<=1){
            viewHolder.btntru.setVisibility(convertView.INVISIBLE);
        }    else if(sl>=1){
            viewHolder.btntru.setVisibility(convertView.VISIBLE);
            viewHolder.btncong.setVisibility(convertView.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btngiatri.getText().toString())+1;
                int slht = MainActivity.mangGioHang.get(position).getSoLuong();
                long giaht = MainActivity.mangGioHang.get(position).getGia();
                MainActivity.mangGioHang.get(position).setSoLuong(slmoinhat);
                DecimalFormat decimalFormat1= new DecimalFormat("###,###,###");
                long giamoinhat = (giaht*slmoinhat)/slht;
                MainActivity.mangGioHang.get(position).setGia(giamoinhat);
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+"Đ");
                com.example.cuahangthietbionline.activity.Giohang.EvenUltil();
                if (slmoinhat>9){
                    finalViewHolder.btncong.setVisibility(View.INVISIBLE);
                    finalViewHolder.btntru.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiatri.setText(String.valueOf(slmoinhat));
                }else{
                    finalViewHolder.btntru.setVisibility(View.VISIBLE);
                    finalViewHolder.btncong.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiatri.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHolder.btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btngiatri.getText().toString())-1;
                int slht = MainActivity.mangGioHang.get(position).getSoLuong();
                long giaht = MainActivity.mangGioHang.get(position).getGia();
                MainActivity.mangGioHang.get(position).setSoLuong(slmoinhat);
                DecimalFormat decimalFormat1= new DecimalFormat("###,###,###");
                long giamoinhat = (giaht*slmoinhat)/slht;
                MainActivity.mangGioHang.get(position).setGia(giamoinhat);
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+"Đ");
                com.example.cuahangthietbionline.activity.Giohang.EvenUltil();
                if (slmoinhat<2){
                    finalViewHolder.btncong.setVisibility(View.VISIBLE);
                    finalViewHolder.btntru.setVisibility(View.INVISIBLE);
                    finalViewHolder.btngiatri.setText(String.valueOf(slmoinhat));
                }else{
                    finalViewHolder.btntru.setVisibility(View.VISIBLE);
                    finalViewHolder.btncong.setVisibility(View.VISIBLE);
                    finalViewHolder.btngiatri.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return convertView;
    }

}
