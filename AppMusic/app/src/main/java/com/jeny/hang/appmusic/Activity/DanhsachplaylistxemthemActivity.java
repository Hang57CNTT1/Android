package com.jeny.hang.appmusic.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.jeny.hang.appmusic.Adapter.DanhsachcacplaylistAdapter;
import com.jeny.hang.appmusic.Model.Playlist;
import com.jeny.hang.appmusic.R;
import com.jeny.hang.appmusic.Service.APIService;
import com.jeny.hang.appmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachplaylistxemthemActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewdanhsachplaylistxemthem;
    DanhsachcacplaylistAdapter danhsachcacplaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachplaylistxemthem);
        anhxa();
        init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Playlist>> callback = dataservice.GetDanhsachcacplaylist();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> mangplaylist = (ArrayList<Playlist>) response.body();
                danhsachcacplaylistAdapter = new DanhsachcacplaylistAdapter(DanhsachplaylistxemthemActivity.this,mangplaylist);
                recyclerViewdanhsachplaylistxemthem.setLayoutManager(new GridLayoutManager(DanhsachplaylistxemthemActivity.this,2));
                recyclerViewdanhsachplaylistxemthem.setAdapter(danhsachcacplaylistAdapter);
                //Log.d("AAA",mangplaylist.get(0).getTen());
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Play lists");
        //tạo mã màu trong res->values->colors
        toolbar.setTitleTextColor(getResources().getColor(R.color.mautim));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void anhxa() {
        toolbar = findViewById(R.id.toobardanhsachplaylist);
        recyclerViewdanhsachplaylistxemthem = findViewById(R.id.recycleviewdanhsachplaylistxemthem);

    }
}
