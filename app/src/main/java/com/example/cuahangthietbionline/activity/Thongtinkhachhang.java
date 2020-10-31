package com.example.cuahangthietbionline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.ultil.CheckConection;
import com.example.cuahangthietbionline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Thongtinkhachhang extends AppCompatActivity {
    EditText edtenkhachhang,edemail,edsodienthoai;
    Button btxacnhan,bttrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        Anhxa();
        bttrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConection.haveNetworkConnection(getApplicationContext())){
            EvenButton();
        }else{
            CheckConection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối! ");
        }
    }

    private void EvenButton() {
        btxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = edtenkhachhang.getText().toString().trim();
                final String sdt = edsodienthoai.getText().toString().trim();
                final String email= edemail.getText().toString().trim();
                if(ten.length()>0&&sdt.length()>0&&email.length()>0){
                    final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest= new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if(Integer.parseInt(madonhang)>0){
                                RequestQueue queue =Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                    if (response.equals("1")){
                                        MainActivity.mangGioHang.clear();
                                        CheckConection.ShowToast_Short(getApplicationContext(),"Bạn đã thêm dữ liệu dỏ hàng thành công");
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        CheckConection.ShowToast_Short(getApplicationContext(),"MỜi bạn tiếp tục mua hàng");
                                    }else{
                                        CheckConection.ShowToast_Short(getApplicationContext(),"Dữ liệu giỏ hàng của bạn đã bị lỗi");
                                    }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray= new JSONArray();
                                        for (int i=0;i<MainActivity.mangGioHang.size();i++){
                                            JSONObject jsonObject= new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.mangGioHang.get(i).getIdsanpham());
                                                jsonObject.put("tensanpham",MainActivity.mangGioHang.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.mangGioHang.get(i).getGia());
                                                jsonObject.put("soluongsanpham",MainActivity.mangGioHang.get(i).getSoLuong());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap= new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    } ,new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String ,String> hashMap= new HashMap<String, String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    CheckConection.ShowToast_Short(getApplicationContext()," Hãy kiểm tra lại dữ liệu");
                }
            }
        });
    }

    private void Anhxa() {
        edtenkhachhang= findViewById(R.id.edittextenkhachhang);
        edemail= findViewById(R.id.edittextemailkhachhang);
        edsodienthoai= findViewById(R.id.edittexsdt);
        btxacnhan= findViewById(R.id.btxacnhan);
        bttrove= findViewById(R.id.bttrolai);

    }
}