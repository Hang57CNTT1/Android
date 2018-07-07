package com.jeny.hang.ngdngnghenhc;

import android.graphics.drawable.RippleDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener, MusicPlayer.OnCompletionListener {

    private ListView lvMusic;
    private TextView tvTitle;
    private TextView tvArlist;
    private SeekBar sbProcess;
    private TextView tvTimeProcess;
    private TextView tvTimeTotal;
    private ImageView ivShuffle;
    private  ImageView ivPlay;
    private ImageView ivPrevious;
    private ImageView ivNext;
    private  ImageView ivRepeat;
    private ArrayList<String> paths; // lưu tất cả đường dẫn của các bài hát
    private int timeProcess;
    private int timeTotal;
    private PlayListAdapter adapter;
    private MusicPlayer musicPlayer;
    private boolean isRunning;
    private int UPDATE_TIME = 1;
    private int timeCurrent;
    private int position;
    private boolean checkrandom,repeat;
//    public static  final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(savedInstanceState == null){
//            Log.e(TAG,"savedInstanceState is null");
//        }
//        else
//        {
//            Log.e(TAG,"savedInstanceState is not null");
//        }
        setContentView(R.layout.activity_main);
        //Ánh xạ
        initViews();
        //xét sự kiện cho các nút cần click
        initListeners();
        // Thêm nội dung cho chương trình
        initComponents();

    }
    private void initComponents()
    {
        initList();
        adapter = new PlayListAdapter(App.getContext(),paths);
        lvMusic.setAdapter(adapter);
        musicPlayer = new MusicPlayer();
        musicPlayer.setOnConpletionListener(this);
    }
    private void initList()
    {
        paths = new ArrayList<>();
        String patch = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music";
        File file = new File(patch);
        File[] files = file.listFiles();// lấy tất cả các file trong thư mục Download
        for(int i=0;i<files.length;i++)
        {
            // Đọc tất cả các file có trong download them vao list nhạc
            String s = files[i].getName();
            //Kiểm tra có phải đường dẫn .mp3 hay ko?
            if(s.endsWith(".mp3"))
            {
                paths.add(files[i].getAbsolutePath());
            }
        }
        //đọc xong danh sách nhạc
    }
    private  void initListeners()
    {
        lvMusic.setOnItemClickListener(this);
        ivShuffle.setOnClickListener(this);
        ivPrevious.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        ivRepeat.setOnClickListener(this);
        sbProcess.setOnSeekBarChangeListener(this);
    }
    private void initViews()
    {
        lvMusic = (ListView)findViewById(R.id.lv_play_list);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvArlist = (TextView)findViewById(R.id.tv_artist);
        tvTimeProcess = (TextView)findViewById(R.id.tv_time_process);
        sbProcess = (SeekBar)findViewById(R.id.sb_process);
        tvTimeTotal = (TextView)findViewById(R.id.tv_time_total);
        ivShuffle = (ImageView)findViewById(R.id.iv_shuffle);
        ivPrevious = (ImageView)findViewById(R.id.iv_back);
        ivPlay = (ImageView)findViewById(R.id.iv_play);
        ivNext = (ImageView)findViewById(R.id.iv_next);
        ivRepeat = (ImageView)findViewById(R.id.iv_repeat);
    }
    private   Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_TIME){
                timeCurrent = musicPlayer.getTimeCurrent();
                tvTimeProcess.setText(getTimeFormat(timeCurrent));
                sbProcess.setProgress(timeCurrent);
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
        String path = paths.get(position);
        playMusic(path);
    }
    public void playMusic(String path){
        //Kiểm tra và dừng app đang phát trước khi chon bài khác
        if(musicPlayer.getState() == MusicPlayer.PLAYER_PLAY){
            musicPlayer.stop();
        }
        musicPlayer.setup(path);
        musicPlayer.play();
        ivPlay.setImageResource(R.drawable.pause);
        //setup tên bài hát và tên ca sĩ
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(paths.get(position));
        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        tvArlist.setText(artist);
        tvTitle.setText(title);
        isRunning = true;
        //setup thời gian phát
        //thời gian tổng
        tvTimeTotal.setText(getTimeFormat(musicPlayer.getTimeTotal()));
        //set up seekbar
        sbProcess.setMax(musicPlayer.getTimeTotal());
        //process time, cập nhật thời gian liên tục cho seekbar(chạy thanh seekbar)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning){
                    Message message = new Message();
                    message.what = UPDATE_TIME;
                    handler.sendMessage(message);
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
    private String getTimeFormat(long time){
        String tm = "";
        int s,m,h;
        //giây
        s = (int) (time % 60);
        m = (int) ((time -  s)/60);
        if(m >= 60){
            h = m/60;
            m = m % 60;
            if(h > 0){
                if(h < 10)
                    tm +="0" + h + ":";
                else
                    tm += h + ":";
            }
        }
        if(m < 10)
            tm += "0" + m + ":";
        else
            tm += m + ":";
        if(s < 10)
            tm += "0" + s;
        else
            tm += s + "";
        return  tm;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_shuffle:
                shuffleMusic();
                break;
            case R.id.iv_next:
                nextMusic();
                break;
            case R.id.iv_play:
                if (musicPlayer.getState() == MusicPlayer.PLAYER_PLAY){
                    ivPlay.setImageResource(R.drawable.play_button);
                    musicPlayer.pause();
                }
                else
                {
                    ivPlay.setImageResource(R.drawable.pause);
                    musicPlayer.play();
                }
                break;
            case R.id.iv_back:
                previousMusic();
                break;
            case R.id.iv_repeat:
                repeatMusic();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
       // kéo thanh seekbar
        if (timeCurrent !=progress && timeCurrent != 0 )
            musicPlayer.seek(sbProcess.getProgress() * 1000);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void OnEndMusic() {
        nextMusic();
    }
    private void shuffleMusic() {

        if (checkrandom == false){
            if ( repeat == true){
                repeat = false;
                ivShuffle.setImageResource(R.drawable.iconshuffled);
                ivRepeat.setImageResource(R.drawable.ms_repeat);
            }
            ivShuffle.setImageResource(R.drawable.iconshuffled);
            checkrandom = true;
        }else {
            ivShuffle.setImageResource(R.drawable.ms_shuffle);
            checkrandom = true;
        }
    }
    private void nextMusic() {
        //xét sự kiện nhấn click vào repeat và shuffle
        if (repeat == true){
            if (position == 0){
                position = paths.size();
            }
            position -= 1;
        }
        if (checkrandom == true){
            Random random = new Random();
            int index = random.nextInt(paths.size());
            if (index == position){
                position = index - 1;
            }
            position = index;
        }
        if (position > (paths.size()- 1)){
            position = 0;
        }
        position++;
        if(position >= paths.size()){
            position = 0;
        }
        String path = paths.get(position);
        playMusic(path);
    }
    private void previousMusic() {//xét sự kiện nhấn click vào repeat và shuffle
        if (repeat == true){
            if (position == 0){
                position = paths.size();
            }
            position -= 1;
        }
        if (checkrandom == true){
            Random random = new Random();
            int index = random.nextInt(paths.size());
            if (index == position){
                position = index - 1;
            }
            position = index;
        }
        if (position > (paths.size()- 1)){
            position = 0;
        }
        position--;
        if(position < 0){
            position = (paths.size() - 1) ;
        }
        String path = paths.get(position);
        playMusic(path);
    }
    private void repeatMusic() {
        if (repeat == false){
            if (checkrandom == true){
                checkrandom = false;
                ivRepeat.setImageResource(R.drawable.iconsyned);
                ivRepeat.setImageResource(R.drawable.ms_repeat);
            }
            ivRepeat.setImageResource(R.drawable.iconsyned);
            repeat = true;
        }else{
            ivRepeat.setImageResource(R.drawable.ms_repeat);
            repeat = false;
        }
    }

}
