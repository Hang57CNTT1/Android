package com.jeny.hang.appmusic.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.jeny.hang.appmusic.Adapter.DanhsachtatcaalbumAdapter;
import com.jeny.hang.appmusic.Model.Album;
import com.jeny.hang.appmusic.R;
import com.jeny.hang.appmusic.Service.APIService;
import com.jeny.hang.appmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachtatcaalbumActivity extends AppCompatActivity {
    RecyclerView recyclerViewAllalbum;
    Toolbar toolbarAllalbum;
    ArrayList<Album> mangalbum;
    DanhsachtatcaalbumAdapter danhsachtatcaalbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtatcaalbum);
        init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Album>> callback = dataservice.GetAllalbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                mangalbum = (ArrayList<Album>) response.body();
                danhsachtatcaalbumAdapter = new DanhsachtatcaalbumAdapter(DanhsachtatcaalbumActivity.this,mangalbum);
                recyclerViewAllalbum.setLayoutManager(new GridLayoutManager(DanhsachtatcaalbumActivity.this,2));
                recyclerViewAllalbum.setAdapter(danhsachtatcaalbumAdapter);
             //   Log.d("AAA",mangalbum.get(1).getTenAlbum());
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });

    }

    private void init() {
        recyclerViewAllalbum = findViewById(R.id.recycleviewAllalbum);
        toolbarAllalbum = findViewById(R.id.toobarAllalbum);
        setSupportActionBar(toolbarAllalbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Các Album");
        toolbarAllalbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
