package com.jeny.hang.appmusic.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jeny.hang.appmusic.Adapter.ViewPagerPlaylistNhac;
import com.jeny.hang.appmusic.Fragment.Fragment_Dia_Nhac;
import com.jeny.hang.appmusic.Fragment.Fragment_Play_Danh_Sach_Cac_Bai_Hat;
import com.jeny.hang.appmusic.Model.BaiHat;
import com.jeny.hang.appmusic.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity {

    Toolbar toolbarplaylist;
    TextView txttimesong,txtTotaltimesong;
    SeekBar seekBarsong;
    ImageButton imgshuffle,imgpreview,imgplay,imgnext,imgrepeat;
    ViewPager viewPagerplaynhac;
    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();
    public static ViewPagerPlaylistNhac adapternhac;
    Fragment_Dia_Nhac fragment_dia_nhac;
    Fragment_Play_Danh_Sach_Cac_Bai_Hat fragment_play_danh_sach_cac_bai_hat;
    //Khởi tạo biến phát nhạc
    MediaPlayer mediaPlayer;
    int position = 0;
    boolean repeat = false;
    boolean checkrandom = false;
    boolean next = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GetDataFromIntent();
        init();
        eventClick();
}

    private void eventClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(adapternhac.getItem(1) != null){
                    if(mangbaihat.size() > 0){
                        fragment_dia_nhac.getActivity();
                        handler.removeCallbacks(this);
                    }else{
                        handler.postDelayed(this,300);
                    }
                }
            }
        },500);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgplay.setImageResource(R.drawable.iconplay);
                }else{
                    mediaPlayer.start();
                    imgplay.setImageResource(R.drawable.iconpause);
                }
            }
        });
        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false){
                    if (checkrandom == true){
                        checkrandom = false;
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                        imgshuffle.setImageResource(R.drawable.iconsuffle);
                    }
                    imgrepeat.setImageResource(R.drawable.iconsyned);
                    repeat = true;
                }else{
                    imgrepeat.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
            }
        });
        imgshuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkrandom == false){
                    if (repeat == true){
                        repeat = false;
                        imgshuffle.setImageResource(R.drawable.iconshuffled);
                        imgrepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    imgshuffle.setImageResource(R.drawable.iconshuffled);
                    checkrandom = true;
                }else{
                    imgshuffle.setImageResource(R.drawable.iconsuffle);
                    checkrandom = true;
                }
            }
        });
        seekBarsong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.size() > 0 ){
                    //nếu tồn tại 1 bài hát đang phát thì tắt đi
                    if (mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    //
                    if (position < (mangbaihat.size())){
                        imgplay.setImageResource(R.drawable.iconpause);
                        position++;
                        if (repeat == true){
                            if (position == 0){
                                position = mangbaihat.size();
                            }
                            position -= 1;
                        }
                        if (checkrandom == true){
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position){
                                position = index - 1;
                            }
                            position = index;
                        }
                        if (position > (mangbaihat.size()- 1)){
                            position = 0;
                        }
                        new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
                       // fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                        Updatetime();
                    }
                }
                imgpreview.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpreview.setClickable(true);
                        imgnext.setClickable(true);
                    }
                },5000);
            }
        });
        imgpreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.size() > 0 ) {
                    //nếu tồn tại 1 bài hát đang phát thì tắt đi
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    //
                    if (position < (mangbaihat.size())) {
                        imgplay.setImageResource(R.drawable.iconpause);
                        position--;
                        if (position < 0){
                            position = mangbaihat.size() - 1;
                        }
                        if (repeat == true) {
                            position += 1;
                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == position) {
                                position = index - 1;
                            }
                            position = index;
                        }
                        new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
                         //fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());
                        Updatetime();
                    }
                }
                imgpreview.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgpreview.setClickable(true);
                        imgnext.setClickable(true);
                    }
                },5000);
            }
        });
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        //xóa dữ liệu cũ
        mangbaihat.clear();
        //kiểm tra điều kiện
        if(intent != null){
            if(intent.hasExtra("cakhuc")){
                BaiHat baihat = intent.getParcelableExtra("cakhuc");
                mangbaihat.add(baihat);
                //  Toast.makeText(this,baihat.getTenBaiHat(),Toast.LENGTH_SHORT).show();
            }
            if(intent.hasExtra("cacbaihat")){
                ArrayList<BaiHat> baiHatArrayList = intent.getParcelableArrayListExtra("cacbaihat");
                mangbaihat =baiHatArrayList;
//            for (int i= 0; i< mangbaihat.size();i++){
//                Log.d("AAA",mangbaihat.get(i).getTenBaiHat());
//            }
            }
        }

    }

    private void init() {
        toolbarplaylist = findViewById(R.id.toolbarplaynhac);
        txttimesong = findViewById(R.id.textviewtimesong);
        txtTotaltimesong = findViewById(R.id.textviewalltimesong);
        seekBarsong = findViewById(R.id.seekbarsong);
        imgplay = findViewById(R.id.imagebuttonplay);
        imgshuffle = findViewById(R.id.imagebuttonshuffle);
        imgnext = findViewById(R.id.imagebuttonnext);
        imgpreview = findViewById(R.id.imagebuttonpreview);
        imgrepeat = findViewById(R.id.imagebuttonrepeat);
        viewPagerplaynhac = findViewById(R.id.viewpagerplaynhac);
        setSupportActionBar(toolbarplaylist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaylist.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mediaPlayer.stop();
                mangbaihat.clear();
            }
        });
        toolbarplaylist.setTitleTextColor(Color.WHITE);
        fragment_dia_nhac = new Fragment_Dia_Nhac();
        fragment_play_danh_sach_cac_bai_hat = new Fragment_Play_Danh_Sach_Cac_Bai_Hat();
        adapternhac = new ViewPagerPlaylistNhac(getSupportFragmentManager());
        adapternhac.AddFragment(fragment_play_danh_sach_cac_bai_hat);
        adapternhac.AddFragment(fragment_dia_nhac);
        viewPagerplaynhac.setAdapter(adapternhac);
        fragment_dia_nhac = (Fragment_Dia_Nhac) adapternhac.getItem(1);
        if (mangbaihat.size() >0){
            getSupportActionBar().setTitle(mangbaihat.get(0).getTenBaiHat());
            new PlayMp3().execute(mangbaihat.get(0).getLinkBaiHat());
            imgplay.setImageResource(R.drawable.iconpause);
        }
    }
    //thực hiện thứ tự để hát 1 ca phúc
    class PlayMp3 extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            return strings[0];
        }
        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            Timesong();
            Updatetime();

        }
    }

    private void Timesong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotaltimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBarsong.setMax(mediaPlayer.getDuration());
    }
    private void Updatetime(){
        final Handler handler =  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    seekBarsong.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txttimesong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        },300);
        final Handler handler1 =  new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true){
                        if (position < (mangbaihat.size())) {
                            imgplay.setImageResource(R.drawable.iconpause);
                            position++;
                            if (repeat == true) {
                                if (position == 0) {
                                    position = mangbaihat.size();
                                }
                                position -= 1;
                            }
                            if (checkrandom == true) {
                                Random random = new Random();
                                int index = random.nextInt(mangbaihat.size());
                                if (index == position) {
                                    position = index - 1;
                                }
                                position = index;
                            }
                            if (position > (mangbaihat.size() - 1)) {
                                position = 0;
                            }
                            new PlayMp3().execute(mangbaihat.get(position).getLinkBaiHat());
                            // fragment_dia_nhac.PlayNhac(mangbaihat.get(position).getHinhBaiHat());
                            getSupportActionBar().setTitle(mangbaihat.get(position).getTenBaiHat());

                        }
                        imgpreview.setClickable(false);
                        imgnext.setClickable(false);
                        Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imgpreview.setClickable(true);
                                imgnext.setClickable(true);
                            }
                        },5000);
                        next = false;
                        handler1.removeCallbacks(this);
                    }else {
                        handler1.postDelayed(this,1000);
                    }
                }
            },1000);
    }
}
