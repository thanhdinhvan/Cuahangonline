package com.example.cuahangthietbionline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.adapter.LaptopAdapter;
import com.example.cuahangthietbionline.model.Sanpham;
import com.example.cuahangthietbionline.ultil.CheckConection;
import com.example.cuahangthietbionline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarlt;
    ListView lvlt;
    LaptopAdapter laptopAdapter;
    ArrayList<Sanpham> manglt;
    int idlt = 0;
    int page = 1;
    View footerview;
    boolean isALoading = false;
    mHandler mHandler;
    boolean limitaData=false;

    private static final String TAG = "LaptopActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        Anhxa();
        if (CheckConection.haveNetworkConnection(getApplicationContext())) {
            GetIdloaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();

        } else {
            CheckConection.ShowToast_Short(getApplicationContext(), "ban hay kiem tra lai ket noi");
            finish();
        }

    }
    private void LoadMoreData() {
        lvlt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",manglt.get(position));
                startActivity(intent);
            }
        });
        lvlt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem==totalItem&&totalItem!=0 && isALoading==false&&limitaData==false){
                    isALoading= true;
                    LaptopActivity.ThreadData threadData = new LaptopActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ch,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), com.example.cuahangthietbionline.activity.Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdanlaptop + "?page=" + Page;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tenlt = "";
                int gialt = 0;
                String hinhanhlt = "";
                String Mota = "";
                int Idsplt = 0;
                if (response != null&&response.length()!=2) {
                    lvlt.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tenlt = jsonObject.getString("tensp");
                            gialt = jsonObject.getInt("giasp");
                            hinhanhlt = jsonObject.getString("hinhanhsanpham");
                            Mota = jsonObject.getString("motasp");
                            Idsplt = jsonObject.getInt("idsanpham");
                            manglt.add(new Sanpham(id, Tenlt, gialt, hinhanhlt, Mota, Idsplt));
                            laptopAdapter.notifyDataSetChanged();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitaData =true;
                    lvlt.removeFooterView(footerview);
                    CheckConection.ShowToast_Short(getApplicationContext(),"da het du lieu ! ");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(idlt));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarlt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void GetIdloaisp() {
        idlt = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisanpham", idlt + "");

    }

    private void Anhxa() {
        toolbarlt = findViewById(R.id.toolbarlaptop);
        lvlt = findViewById(R.id.listviewlaptop);
        manglt = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(), manglt);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);


        mHandler = new mHandler();
        lvlt.setAdapter(laptopAdapter);


    }
    public class mHandler extends Handler {
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0 :
                    lvlt.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isALoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}