package com.example.cuahangthietbionline.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.activity.ChiTietSanPham;
import com.example.cuahangthietbionline.model.Sanpham;
import com.example.cuahangthietbionline.ultil.CheckConection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,parent,false);
        ItemHolder itemHolder = new ItemHolder(v);




        return itemHolder;
    }

    @Override
    public void onBindViewHolder( ItemHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txttensanpham.setText(sanpham.Tensanpham);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham())+" Đ");
        Glide.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.common_full_open_on_phone)
                .error(R.drawable.googleg_disabled_color_18)
                .into(holder.imghinhsanpham);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class  ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsanpham;
        public TextView txttensanpham,txtgiasanpham;

        public ItemHolder( View itemView) {
            super(itemView);
            imghinhsanpham = itemView.findViewById(R.id.imageviewsanpham);
            txtgiasanpham =   (TextView) itemView.findViewById(R.id.textviewgiasanpham);
            txttensanpham =  (TextView ) itemView.findViewById(R.id.textviewtensanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("thongtinsanpham",arraysanpham.get(getPosition()));
                    CheckConection.ShowToast_Short(context,arraysanpham.get(getPosition()).getTensanpham());
                    context.startActivity(intent);
                }
            });
        }
    }
}
