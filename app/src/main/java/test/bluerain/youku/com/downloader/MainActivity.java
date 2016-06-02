package test.bluerain.youku.com.downloader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import test.bluerain.youku.com.downloader.entities.ViewItemInfo;
import test.bluerain.youku.com.downloader.net.DownloadTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText mEditTextCache;
    private EditText mEditTextConunt;
    private EditText mEditTextThread;
    private Button mIdBtnStart;
    private Button mIdBtnRestart;
    private ListView mIdListViewMain;
    private List<ViewItemInfo> mViewItemInfos;


    /*视频*/
    //    private static String URL1 = "http://106.38.249.110/youku/67745984B58347900582C41AE/03000207005707C308F78C316C87FE58D3A134-0A81-E768-B473
    // -151C0E335182.flv";
    private static String URL2 = "http://36.110.176.41/youku/6775131A60D3E81BDAB7C86089/03000207015707C308F78C316C87FE58D3A134-0A81-E768-B473" +
            "-151C0E335182.flv";

    /*apk*/
//    private static String URL = "http://test.gamex.mobile.youku.com/apkdowload/1390298687_youdao_433.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(new DownloadStateBroadcastReceiver(), getMainInentFilter());
        initData();
        initView();
        initEvent();
    }


    private void initData() {
        mViewItemInfos = new ArrayList<>();
    }

    private void initView() {
        mEditTextCache = (EditText) findViewById(R.id.editText_cache);
        mEditTextConunt = (EditText) findViewById(R.id.editText_conunt);
        mEditTextThread = (EditText) findViewById(R.id.editText_thread);
        mIdBtnStart = (Button) findViewById(R.id.id_btn_start);
        mIdBtnRestart = (Button) findViewById(R.id.id_btn_restart);
        mIdListViewMain = (ListView) findViewById(R.id.id_list_view_main);

    }

    private void initEvent() {
        mIdBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadTest();
            }
        });

    }

    private IntentFilter getMainInentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadTask.ACTION_DOWNLOAD_UPDATE);
        intentFilter.addAction(DownloadTask.ACTION_DOWNLOAD_FINISH);
        intentFilter.addAction(DownloadTask.ACTION_DOWNLOAD_PAUSE);
        intentFilter.addAction(DownloadTask.ACTION_DOWNLOAD_START);
        intentFilter.addAction(DownloadTask.ACTION_DOWNLOAD_EXCEPTION_NETWORK_TIMEOUT);
        intentFilter.addAction(DownloadTask.ACTION_DOWNLOAD_EXCEPTION_NETWORK_INVALID);
        intentFilter.addAction(DownloadTask.ACTION_DOWNLOAD_EXCEPTION_OTHER);
        intentFilter.addAction(DownloadTask.ACTION_DOWNLOAD_EXCEPTION_SYSTEM_NO_ROOM);
        return intentFilter;
    }


    private void downloadTest() {
        DownloadInfo info = new DownloadInfo(URL2, 2, Environment.getExternalStorageDirectory() + "/download_test/aaa.flv");
        DownloadTask task = new DownloadTask(info);
        task.start();
    }


    class DownloadStateBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case DownloadTask.ACTION_DOWNLOAD_START:
                    Log.d(TAG, "onReceive: ACTION_DOWNLOAD_START");
                    break;
                case DownloadTask.ACTION_DOWNLOAD_PAUSE:
                    Log.d(TAG, "onReceive: ACTION_DOWNLOAD_PAUSE");
                    break;
                case DownloadTask.ACTION_DOWNLOAD_UPDATE:
                    DownloadInfo info = (DownloadInfo) intent.getSerializableExtra("DOWNLOAD_INFO");
                    int peer = (int) ((info.getDownloadLength() * 1f / info.getFileSize()) * 100);
                    int speed = info.getDownloadSpeed();
                    Log.d(TAG, "onReceive: ACTION_DOWNLOAD_UPDATE" + "下载进度[" + peer + "%]" + "下载速度：[" + speed + "]");
                    break;
                case DownloadTask.ACTION_DOWNLOAD_FINISH:
                    DownloadInfo info1 = (DownloadInfo) intent.getSerializableExtra("DOWNLOAD_INFO");
                    Log.d(TAG, "onReceive: ACTION_DOWNLOAD_FINISH" + "下载总用时[" + info1.getTotalTime() + "s]");
                    break;
                case DownloadTask.ACTION_DOWNLOAD_EXCEPTION_SYSTEM_NO_ROOM:
                    Log.d(TAG, "onReceive: ACTION_DOWNLOAD_EXCEPTION_SYSTEM_NO_ROOM");
                    break;
                case DownloadTask.ACTION_DOWNLOAD_EXCEPTION_NETWORK_TIMEOUT:
                    Log.d(TAG, "onReceive: ACTION_DOWNLOAD_EXCEPTION_NETWORK_TIMEOUT");
                    break;
                case DownloadTask.ACTION_DOWNLOAD_EXCEPTION_NETWORK_INVALID:
                    Log.d(TAG, "onReceive: ACTION_DOWNLOAD_EXCEPTION_NETWORK_INVALID");
                    break;
                case DownloadTask.ACTION_DOWNLOAD_EXCEPTION_OTHER:
                    Log.d(TAG, "onReceive: ACTION_DOWNLOAD_EXCEPTION_OTHER");
                    break;
            }
        }
    }
}
