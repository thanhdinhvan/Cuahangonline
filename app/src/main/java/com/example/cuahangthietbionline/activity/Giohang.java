package com.example.cuahangthietbionline.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.adapter.GiohangAdapter;
import com.example.cuahangthietbionline.ultil.CheckConection;

import java.text.DecimalFormat;

public class Giohang extends AppCompatActivity {
    ListView listViewgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan,btntieptucmua;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionToolbar();
        CheckData();
        EvenUltil();
        CactchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.mangGioHang.size()>0){
                    Intent intent = new Intent(getApplicationContext(),Thongtinkhachhang.class);
                    startActivity(intent);
                }else {
                    CheckConection.ShowToast_Short(getApplicationContext(),"Giỏ hàng hienj đang không có sản phẩm nào để thanh toán.");
                }
            }
        });
    }

    private void CactchOnItemListView() {
        listViewgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(Giohang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.mangGioHang.size()<=0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.mangGioHang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EvenUltil();
                            if (MainActivity.mangGioHang.size()<=0){
                                txtthongbao.setVisibility(View.VISIBLE);

                            }else{
                                txtthongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EvenUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      giohangAdapter.notifyDataSetChanged();
                      EvenUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static  void EvenUltil() {
        long tongtien=0;
        for (int i=0;i<MainActivity.mangGioHang.size();i++){
            tongtien += MainActivity.mangGioHang.get(i).getGia();
            DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
            txttongtien.setText(decimalFormat.format(tongtien) +"Đ");


        }
    }

    private void CheckData() {
        if(MainActivity.mangGioHang.size()<=0){
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            listViewgiohang.setVisibility(View.VISIBLE);
        }else {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            listViewgiohang.setVisibility(View.VISIBLE);

        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        listViewgiohang = findViewById(R.id.listviewgiohang);
        txtthongbao = findViewById(R.id.textviewthongbao);
        txttongtien = findViewById(R.id.txttongtien);
        btnthanhtoan=findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua=findViewById(R.id.buttontieptucmuahang);
        toolbargiohang=findViewById(R.id.toolbarGiohang);
        giohangAdapter = new GiohangAdapter(Giohang.this,MainActivity.mangGioHang);
        listViewgiohang.setAdapter(giohangAdapter);
    }
}