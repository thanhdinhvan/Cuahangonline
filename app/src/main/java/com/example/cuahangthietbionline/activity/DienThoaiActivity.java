package com.example.cuahangthietbionline.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
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
import com.example.cuahangthietbionline.adapter.DienthoaiAdapter;
import com.example.cuahangthietbionline.model.Sanpham;
import com.example.cuahangthietbionline.ultil.CheckConection;
import com.example.cuahangthietbionline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbardt;
    ListView lvdt;
    DienthoaiAdapter dienthoaiAdapter;
    ArrayList<Sanpham> mangdt;
    int iddt = 0;
    int page = 1;
    View footerview;
    boolean isALoading = false;
    mHandler mHandler;
    boolean limitaData=false;
    private static final String TAG = "DienThoaiActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.d("thanh.nt1", " " + R.menu.menu + " menu = " + menu);
        getMenuInflater().inflate(R.menu.menu_ch, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), com.example.cuahangthietbionline.activity.Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }




    private void LoadMoreData() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(getApplicationContext(),ChiTietSanPham.class);
                Log.d(TAG, "onItemClick: "+Thread.currentThread().getName());
                intent.putExtra("thongtinsanpham",mangdt.get(position));
                startActivity(intent);
            }
        });
        lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem==totalItem&&totalItem!=0 && isALoading==false&&limitaData==false){
                    isALoading= true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandienthoai + "?page=" + Page;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tendt = "";
                int giadt = 0;
                String hinhanhdt = "";
                String Mota = "";
                int Idspdt = 0;
                if (response != null && response.length()!=2 ) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tendt = jsonObject.getString("tensp");
                            giadt = jsonObject.getInt("giasp");
                            hinhanhdt = jsonObject.getString("hinhanhsanpham");
                            Mota = jsonObject.getString("motasp");
                            Idspdt = jsonObject.getInt("idsanpham");
                            mangdt.add(new Sanpham(id, Tendt, giadt, hinhanhdt, Mota, Idspdt));
                            dienthoaiAdapter.notifyDataSetChanged();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    limitaData =true;
                    lvdt.removeFooterView(footerview);
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
                param.put("idsanpham", String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void GetIdloaisp() {
        iddt = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisanpham", iddt + "");

    }

    private void Anhxa() {
        toolbardt = findViewById(R.id.toolbardienthoai);
        lvdt = findViewById(R.id.listviewdienthoai);
        mangdt = new ArrayList<>();
        dienthoaiAdapter = new DienthoaiAdapter(getApplicationContext(), mangdt);
        lvdt.setAdapter(dienthoaiAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler= new mHandler();
    }
    public class mHandler extends Handler{
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0 :
                    lvdt.addFooterView(footerview);
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