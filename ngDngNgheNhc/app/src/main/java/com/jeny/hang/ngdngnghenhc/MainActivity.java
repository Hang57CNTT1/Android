package com.jeny.hang.ngdngnghenhc;

import android.graphics.drawable.RippleDrawable;
import android.os.Environment;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

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

    public static  final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            Log.e(TAG,"savedInstanceState is null");
        }
        else
        {
            Log.e(TAG,"savedInstanceState is not null");
        }
        setContentView(R.layout.activity_main);
        //Ánh xạ
        initViews();
        //sét sự kiện cho các nút cần click
        initListeners();
        // Thêm nội dung cho chương trình
        initComponents();

    }
    private void initComponents()
    {
        initList();
        adapter = new PlayListAdapter(App.getContext(),paths);
        lvMusic.setAdapter(adapter);
    }
    private void initList()
    {
        paths = new ArrayList<>();
        String patch = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Downloads";
        File file = new File(patch);
        File[] files = file.listFiles();// lấy tất cả các file trong thư mục Download
        for(int i=0;i<file.length();i++)
        {
            // Đọc tất cả các file có trong download them vao list nhạc
            String s = files[i].getName();
            //Kiểm tra có phải đường dẫn .mp3 hay ko?
            if(s.endsWith(".mp3") || s.endsWith(".wav"))
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
