package com.jeny.hang.appmusic.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.View;

import com.jeny.hang.appmusic.Adapter.DanhsachtatcachudeAdapter;
import com.jeny.hang.appmusic.Model.Chude;
import com.jeny.hang.appmusic.R;
import com.jeny.hang.appmusic.Service.APIService;
import com.jeny.hang.appmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static com.jeny.hang.appmusic.R.*;

public class DanhsachtatcachudeActivity extends AppCompatActivity {
    RecyclerView recyclerViewtatcachude;
    Toolbar toolbartatcachude;
    DanhsachtatcachudeAdapter danhsachtatcachudeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtatcachude);
        init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        retrofit2.Call<List<Chude>> callback = dataservice.GetAllChuDe();
        callback.enqueue(new Callback<List<Chude>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Chude>> call, Response<List<Chude>> response) {
                ArrayList<Chude> mangchude = (ArrayList<Chude>) response.body();
                danhsachtatcachudeAdapter = new DanhsachtatcachudeAdapter(DanhsachtatcachudeActivity.this,mangchude);
                recyclerViewtatcachude.setLayoutManager(new GridLayoutManager(DanhsachtatcachudeActivity.this,1));
                recyclerViewtatcachude.setAdapter(danhsachtatcachudeAdapter);
                //Log.d("AAA",mangchude.get(0).getTenChuDe());
            }

            @Override
            public void onFailure(retrofit2.Call<List<Chude>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerViewtatcachude = findViewById(id.recycleviewallchude);
        toolbartatcachude = findViewById(id.toolbarallchude);
        setSupportActionBar(toolbartatcachude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Chủ Đề");
        toolbartatcachude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
