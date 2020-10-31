package com.example.cuahangthietbionline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telecom.GatewayInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.cuahangthietbionline.R;
import com.example.cuahangthietbionline.adapter.LoaispAdapter;
import com.example.cuahangthietbionline.adapter.SanphamAdapter;
import com.example.cuahangthietbionline.model.Giohang;
import com.example.cuahangthietbionline.model.Loaisp;
import com.example.cuahangthietbionline.model.Sanpham;
import com.example.cuahangthietbionline.ultil.CheckConection;
import com.example.cuahangthietbionline.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import javax.xml.transform.ErrorListener;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;
    LoaispAdapter loaispAdapter;
    int id = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";

    public static ArrayList<Giohang> mang  GioHang;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if (CheckConection.haveNetworkConnection(getApplicationContext())) {
            Actionbar();
            ActionViewFlipper();
            GetDuLieuLoaisp();
            GetDuLieuSPMoiNhat();
            CatchOnItemListView();
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


    private void CatchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConection.ShowToast_Short(getApplicationContext(), "ban hay kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham", mangloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConection.ShowToast_Short(getApplicationContext(), "ban hay kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idloaisanpham", mangloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConection.ShowToast_Short(getApplicationContext(), "ban hay kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);

                            startActivity(intent);
                        } else {
                            CheckConection.ShowToast_Short(getApplicationContext(), "ban hay kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConection.ShowToast_Short(getApplicationContext(), "ban hay kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });

    }

    private void GetDuLieuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String Tensanpham = "";
                    Integer Giasanpham = 0;
                    String Hinhanhsanpham = "";
                    String Motasanpham = "";
                    int IDSanpham = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensp");

                            Giasanpham = jsonObject.getInt("giasp");
                            Hinhanhsanpham = jsonObject.getString("hinhanhsanpham");

                            Motasanpham = jsonObject.getString("motasp");
                            IDSanpham = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID, Tensanpham, Giasanpham, Hinhanhsanpham, Motasanpham, IDSanpham));
                            sanphamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("abc", error.getMessage() + "");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuLoaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanLoaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id, tenloaisp, hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    mangloaisp.add(3, new Loaisp(0, "lien he", "https://nhakhoajavi.com/wp-content/uploads/2019/06/hotline-icon.png"));
                    mangloaisp.add(4, new Loaisp(0, "thong tin", "https://png.pngtree.com/png-vector/20190501/ourlarge/pngtree-vector-information-icon-png-image_1017092.jpg"));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://tinhte.cdnforo.com/store/2014/08/2572609_HInh_2.jpg");
        mangquangcao.add("https://farm1.staticflickr.com/487/32868981216_26d6af282c_o.png");
        mangquangcao.add("https://farm1.staticflickr.com/487/32868981216_26d6af282c_o.png");
        mangquangcao.add("https://farm1.staticflickr.com/487/32868981216_26d6af282c_o.png");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);

            Animation annotation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
            Animation annotation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
            viewFlipper.setInAnimation((Animation) annotation_slide_in);
            viewFlipper.setInAnimation((Animation) annotation_slide_out);


        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.Toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerView = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.navigationview);
        listView = findViewById(R.id.listmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0, new Loaisp(0, "trang chu", "https://thuycanhmiennam.com/wp-content/uploads/2017/04/icon-home-cam.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp, getApplicationContext());
        listView.setAdapter(loaispAdapter);
        mangsanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(), mangsanpham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(sanphamAdapter);
        if (mangGioHang != null) {

        } else {
            mangGioHang = new ArrayList<>();
        }
    }

}