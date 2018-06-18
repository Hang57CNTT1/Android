package com.jeny.hang.appmusic.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jeny.hang.appmusic.Adapter.DanhsachtheloaitheochudeAdapter;
import com.jeny.hang.appmusic.Model.Chude;
import com.jeny.hang.appmusic.Model.Theloai;
import com.jeny.hang.appmusic.R;
import com.jeny.hang.appmusic.Service.APIService;
import com.jeny.hang.appmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachtheloaitheochudeActivity extends AppCompatActivity {

    Toolbar toolbartheloaitheochude;
    RecyclerView recyclerViewthelaoitheochude;
    Chude chude;
    DanhsachtheloaitheochudeAdapter danhsachtheloaitheochudeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtheloaitheochude);
        GetIntent();
        //GetIntent() trước init() để lấy dữ liệu sau đó mới gắn cho layout
        init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Theloai>> callback = dataservice.GetTheloaitheochude(chude.getIdChuDe());
        callback.enqueue(new Callback<List<Theloai>>() {
            @Override
            public void onResponse(Call<List<Theloai>> call, Response<List<Theloai>> response) {
                ArrayList<Theloai> mangtheloai = (ArrayList<Theloai>) response.body();
                danhsachtheloaitheochudeAdapter = new DanhsachtheloaitheochudeAdapter(DanhsachtheloaitheochudeActivity.this,mangtheloai);
                recyclerViewthelaoitheochude.setLayoutManager(new GridLayoutManager(DanhsachtheloaitheochudeActivity.this,2));
                recyclerViewthelaoitheochude.setAdapter(danhsachtheloaitheochudeAdapter);
               // Log.d("AAA",mangtheloai.get(0).getTenTheLoai());
            }

            @Override
            public void onFailure(Call<List<Theloai>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerViewthelaoitheochude = findViewById(R.id.recycleviewtheloaitheochude);
        toolbartheloaitheochude = findViewById(R.id.toobartheloaitheochude);
        setSupportActionBar(toolbartheloaitheochude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chude.getTenChuDe());
        toolbartheloaitheochude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIntent() {
        Intent intent = getIntent();
        if(intent.hasExtra("chude")){
            chude = (Chude) intent.getSerializableExtra("chude");
            Toast.makeText(this,chude.getTenChuDe(),Toast.LENGTH_SHORT).show();
        }
    }
}
