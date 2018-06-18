package com.jeny.hang.appmusic.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeny.hang.appmusic.Activity.PlayNhacActivity;
import com.jeny.hang.appmusic.Adapter.PlaynhacAdapter;
import com.jeny.hang.appmusic.R;

public class Fragment_Play_Danh_Sach_Cac_Bai_Hat extends Fragment{
    View view;
    RecyclerView recyclerViewPlaynhac;
    PlaynhacAdapter playnhacAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_danh_sach_cac_bai_hat,container,false);
        recyclerViewPlaynhac  = view.findViewById(R.id.recycleviewPlaybaihat);
        if (PlayNhacActivity.mangbaihat.size() > 0){
            playnhacAdapter = new PlaynhacAdapter(getActivity(),PlayNhacActivity.mangbaihat);
            recyclerViewPlaynhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewPlaynhac.setAdapter(playnhacAdapter);
        }
        return view;
    }
}
