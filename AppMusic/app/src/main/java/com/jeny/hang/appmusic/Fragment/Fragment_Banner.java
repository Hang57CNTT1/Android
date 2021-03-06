package com.jeny.hang.appmusic.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeny.hang.appmusic.Adapter.BannerAdapter;
import com.jeny.hang.appmusic.Model.Quangcao;
import com.jeny.hang.appmusic.R;
import com.jeny.hang.appmusic.Service.APIService;
import com.jeny.hang.appmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment{
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currentitem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner,container,false);
        anhxa();
        GetData();
        return view;
    }

    private void anhxa() {
        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.indicatordefault);
    }

    private void GetData() {

        Dataservice dataservice = APIService.getService();
        Call<List<Quangcao>> callback = dataservice.GetDataBanner();
        callback.enqueue(new Callback<List<Quangcao>>() {
            @Override
            public void onResponse(Call<List<Quangcao>> call, Response<List<Quangcao>> response) {
                ArrayList<Quangcao> bannners = (ArrayList<Quangcao>) response.body();
                bannerAdapter = new BannerAdapter(getActivity(),bannners);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentitem = viewPager.getCurrentItem();
                        currentitem++;
                        //tự động xét về vị trí ban đầu
                        if(currentitem >= viewPager.getAdapter().getCount()){
                            currentitem = 0;
                        }
                        viewPager.setCurrentItem(currentitem, true);
                        handler.postDelayed(runnable,4500);
                    }
                };
                handler.postDelayed(runnable,4500);
                //Log.d("AAA",bannners.get(0).getTenBaiHat());
            }

            @Override
            public void onFailure(Call<List<Quangcao>> call, Throwable t) {

            }
        });
    }
}
