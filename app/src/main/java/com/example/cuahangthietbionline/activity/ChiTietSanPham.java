package com.example.cuahangthietbionline.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.model.Giohang;
import com.example.cuahangthietbionline.model.Sanpham;

import java.text.DecimalFormat;


public class ChiTietSanPham extends AppCompatActivity{
    Toolbar toolbarChitiet;
    ImageView imageViewChitiet;
    TextView txtten,txtgia,txtmota;
    Spinner   spinner;
    Button buttonDatMua;
    int id = 0 ;
    String Tenchitiet="";
    int Giachitiet=0;
    String Hinhanhchitiet="";
    String Motachitiet="";
    int IDsanpham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        Actiontoolbar();
        Getinformation();
        CathEventSpinner();
        EvenButton();
    }


    private void EvenButton() {
        buttonDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGioHang.size()>0){
                int s1 = Integer.parseInt(spinner.getSelectedItem().toString());
                boolean exists = false;
                for (int i =0 ; i<MainActivity.mangGioHang.size();i++){
                    if (MainActivity.mangGioHang.get(i).getIdsanpham()==id){
                    MainActivity.mangGioHang.get(i).setSoLuong( MainActivity.mangGioHang.get(i).getSoLuong()+s1);
                        if (MainActivity.mangGioHang.get(i).getSoLuong()>10){
                            MainActivity.mangGioHang.get(i).setSoLuong(10);
                        }
                        MainActivity.mangGioHang.get(i).setGia(Giachitiet*MainActivity.mangGioHang.get(i).getSoLuong());
                        exists = true;
                    }
                }
                if (exists==false){{
                    int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong*Giachitiet;
                    MainActivity.mangGioHang.add(new Giohang(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
                }}
                }else {
                    int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong*Giachitiet;
                    MainActivity.mangGioHang.add(new Giohang(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
                }
                Intent intent = new Intent(getApplicationContext(), com.example.cuahangthietbionline.activity.Giohang.class);
                startActivity(intent);
            }
        });
    }

    private void CathEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_dropdown_item_1line,soluong);
        spinner.setAdapter(arrayAdapter);
    }


    private void Getinformation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getID();
        Tenchitiet = sanpham.getTensanpham();
        Giachitiet= sanpham.getGiasanpham();
        Hinhanhchitiet = sanpham.getHinhanhsanpham();
        Motachitiet = sanpham.getMotasanpham();
        IDsanpham = sanpham.getIDSanpham();
        txtten.setText(Tenchitiet);
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        txtgia.setText("Giá : "+decimalFormat.format(Giachitiet)+"Đ");
        txtmota.setText(Motachitiet);
        Glide.with(getApplicationContext()).load(Hinhanhchitiet)
                .placeholder(R.drawable.common_full_open_on_phone)
                .error(R.drawable.googleg_disabled_color_18)
                .into(imageViewChitiet);
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChitiet = findViewById(R.id.toolbarChitietsanpham1);
        imageViewChitiet= findViewById(R.id.imageviewchitietsanpham);
        txtten = findViewById(R.id.textviewsanpham);
        txtgia =findViewById(R.id.giasanpham);
        txtmota =findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spinner);
        buttonDatMua= findViewById(R.id.buttonDatMua);
    }
}