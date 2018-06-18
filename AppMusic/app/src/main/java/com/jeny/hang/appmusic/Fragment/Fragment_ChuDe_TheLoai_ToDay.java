package com.jeny.hang.appmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeny.hang.appmusic.Activity.DanhsachbaihatActivity;
import com.jeny.hang.appmusic.Activity.DanhsachtatcachudeActivity;
import com.jeny.hang.appmusic.Activity.DanhsachtheloaitheochudeActivity;
import com.jeny.hang.appmusic.Model.ChuDevaTheLoaiTrongNgay;
import com.jeny.hang.appmusic.Model.Chude;
import com.jeny.hang.appmusic.Model.Theloai;
import com.jeny.hang.appmusic.R;
import com.jeny.hang.appmusic.Service.APIService;
import com.jeny.hang.appmusic.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_ChuDe_TheLoai_ToDay extends Fragment{
    View view;
    HorizontalScrollView horizontalScrollView;
    TextView txtxemthemchudetheloai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chude_theloai_today,container,false);
        horizontalScrollView = view.findViewById(R.id.horizontalScrollView);
        txtxemthemchudetheloai = view.findViewById(R.id.textviewxemthem);
        txtxemthemchudetheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhsachtatcachudeActivity.class);
                startActivity(intent);
            }
        });
        GetData();
        return view;
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<ChuDevaTheLoaiTrongNgay> callback = dataservice.GetCategoryMusic();
        callback.enqueue(new Callback<ChuDevaTheLoaiTrongNgay>() {
            @Override
            public void onResponse(Call<ChuDevaTheLoaiTrongNgay> call, Response<ChuDevaTheLoaiTrongNgay> response) {
                ChuDevaTheLoaiTrongNgay chuDevaTheLoaiTrongNgay = response.body();

                final ArrayList<Chude> chudeArrayList = new ArrayList<>();
                chudeArrayList.addAll(chuDevaTheLoaiTrongNgay.getChude());

                final ArrayList<Theloai> theloaiArrayList = new ArrayList<>();
                theloaiArrayList.addAll(chuDevaTheLoaiTrongNgay.getTheloai());

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                //xét kích thước hình chủ đề nếu quá lớn
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(580,250);
                layout.setMargins(10,20,10,30);
                //load với chủ đề
                for (int i = 0;i < (chudeArrayList.size());i++)
                {
                    CardView cardView = new CardView(getActivity());
                     cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(chudeArrayList.get(i).getHinhChuDe() != null){
                        Picasso.with(getActivity()).load(chudeArrayList.get(i).getHinhChuDe()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DanhsachtheloaitheochudeActivity.class);
                            intent.putExtra("chude",chudeArrayList.get(finalI));
                            //chuyển màn hình
                            startActivity(intent);
                        }
                    });
                }
                //xét với thể loại
                for (int j = 0;j < (chudeArrayList.size());j++)
                {
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(theloaiArrayList.get(j).getHinhTheLoai() != null){
                        Picasso.with(getActivity()).load(theloaiArrayList.get(j).getHinhTheLoai()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);

                    final int finalJ = j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DanhsachbaihatActivity.class);
                            //kế thừa implements Serializable trong Model Theloai để gọi đc theloaiArraylist
                            intent.putExtra("idtheloai",theloaiArrayList.get(finalJ));
                            startActivity(intent);
                        }
                    });
                }
                horizontalScrollView.addView(linearLayout);
//                Log.d("AAA",chuDevaTheLoaiTrongNgay.getTheloai().get(0).getTenTheLoai());
            }

            @Override
            public void onFailure(Call<ChuDevaTheLoaiTrongNgay> call, Throwable t) {

            }
        });
    }
}
