package test.bluerain.youku.com.downloader.net;

import android.content.Intent;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import test.bluerain.youku.com.ThreadInfo;
import test.bluerain.youku.com.downloader.CusApplication;
import test.bluerain.youku.com.downloader.DownloadInfo;
import test.bluerain.youku.com.downloader.Profile;
import test.bluerain.youku.com.downloader.db.ThreadDAO;
import test.bluerain.youku.com.downloader.db.ThreadDAOImpl;
import test.bluerain.youku.com.downloader.utils.SystemUtils;

/**
 * Project: Downloader.
 * Data: 2016/6/1.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class DownloadTask {
    public static final String ACTION_DOWNLOAD_START = "ACTION_DOWNLOAD_START";
    public static final String ACTION_DOWNLOAD_UPDATE = "ACTION_DOWNLOAD_UPDATE";
    public static final String ACTION_DOWNLOAD_PAUSE = "ACTION_DOWNLOAD_PAUSE";
    public static final String ACTION_DOWNLOAD_FINISH = "ACTION_DOWNLOAD_FINISH";
    public static final String ACTION_DOWNLOAD_EXCEPTION_NETWORK_INVALID = "ACTION_DOWNLOAD_EXCEPTION_NETWORK_INVALID";
    public static final String ACTION_DOWNLOAD_EXCEPTION_NETWORK_TIMEOUT = "ACTION_DOWNLOAD_EXCEPTION_NETWORK_TIMEOUT";
    public static final String ACTION_DOWNLOAD_EXCEPTION_SYSTEM_NO_ROOM = "ACTION_DOWNLOAD_EXCEPTION_SYSTEM_NO_ROOM";
    public static final String ACTION_DOWNLOAD_EXCEPTION_OTHER = "ACTION_DOWNLOAD_EXCEPTION_OTHER";

    private List<ThreadInfo> mThreadInfos;
    private ThreadDAO mThreadDAO;

    private DownloadInfo mDownloadInfo;

    private ThreadPoolExecutor mThreadPoolExecutor;
    private DownloadEngine.DownloadStatusListener mDownloadStatusListener;

    private boolean isTimeout;

    private File mFile;
    private RandomAccessFile mRandomAccessFile;

    public DownloadTask(DownloadInfo downloadInfo) {
        mDownloadInfo = downloadInfo;
        mThreadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Profile.THREAD_NUM);
        mThreadDAO = ThreadDAOImpl.getInstance();
        mDownloadStatusListener = new DownloadStateListener();
    }

    public void start() {
        if (mDownloadInfo.getFileSize() == 0) {
            requestFileLengthAndStart();
        } else {
            initThreadInfo();
            startDownloadTask();
        }
    }


    public void pause() {
        sendBroadcast(ACTION_DOWNLOAD_PAUSE);
        for (ThreadInfo info : mThreadInfos) {
            DownloadEngine downloadEngine = new DownloadEngine(info);
            downloadEngine.pauseTask();
        }
    }

    public void requestFileLengthAndStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mDownloadInfo.getUrl());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        mDownloadInfo.setFileSize(connection.getContentLength());
                        connection.disconnect();
                        initThreadInfo();
                        startDownloadTask();
                    } else {
                        sendBroadcast(ACTION_DOWNLOAD_EXCEPTION_OTHER);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    sendBroadcast(ACTION_DOWNLOAD_EXCEPTION_OTHER);
                }
            }
        }).start();
    }

    private void initThreadInfo() {
        mThreadInfos = mThreadDAO.query(mDownloadInfo.getUrl());
        if (mThreadInfos.size() == 0) {
            for (int i = 0; i < mDownloadInfo.getThreadNum(); i++) {
                int fileSize = mDownloadInfo.getFileSize();
                if (fileSize == 0) {
                    sendBroadcast(ACTION_DOWNLOAD_EXCEPTION_OTHER);
                    return;
                }
                int peerSize = (int) (fileSize * 1f / mDownloadInfo.getThreadNum());
                ThreadInfo threadInfo = new ThreadInfo(null, i, mDownloadInfo.getUrl(),
                        (peerSize * i),
                        (peerSize * (i + 1)) - 1,
                        0, 0, false);
                if (i == (mDownloadInfo.getThreadNum() - 1))
                    threadInfo.setMEndPosition(fileSize - 1);
                mThreadInfos.add(threadInfo);
                mThreadDAO.insert(threadInfo);
            }
        }

    }

    private void startDownloadTask() {
        resetVariables();
        setDownloadFile();
        sendBroadcast(ACTION_DOWNLOAD_START);
        if (isDataError()) {
            sendBroadcast(ACTION_DOWNLOAD_EXCEPTION_OTHER);
            return;
        }
        if (!isHaveNetwork()) {
            sendBroadcast(ACTION_DOWNLOAD_EXCEPTION_NETWORK_INVALID);
            return;
        }
        if (!isHaveEnoughRoom()) {
            sendBroadcast(ACTION_DOWNLOAD_EXCEPTION_SYSTEM_NO_ROOM);
            return;
        }
        for (ThreadInfo info : mThreadInfos) {
            DownloadEngine downloadEngine = new DownloadEngine(info);
            downloadEngine.setFile(mFile);
            downloadEngine.setRandomAccessFile(mRandomAccessFile);
            downloadEngine.setDownloadStatusListener(mDownloadStatusListener);
            downloadEngine.setThreadDAO(ThreadDAOImpl.getInstance());
            mThreadPoolExecutor.execute(downloadEngine);
        }
    }

    private void resetVariables() {
        isTimeout = false;
    }

    private void setDownloadFile() {
        try {
            File file = new File(mDownloadInfo.getFilePath());
            if (!(file.getParentFile().exists()))
                file.getParentFile().mkdir();
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            mFile = file;
            mRandomAccessFile = new RandomAccessFile(file, "rwd");
            mRandomAccessFile.setLength(mDownloadInfo.getFileSize());
        } catch (IOException e) {
            e.printStackTrace();
            sendBroadcast(ACTION_DOWNLOAD_EXCEPTION_OTHER);
        }
    }

    public synchronized void isFinished(ThreadInfo threadInfo) {
        boolean isFinished = true;
        for (ThreadInfo info : mThreadInfos) {
            if (!info.getMIsFinished())
                isFinished = false;
        }
        if (isFinished) {
            long MaxDuration = 0;
            for (ThreadInfo info1 : mThreadInfos) {
                long currentThreadDuration = info1.getDuration();
                MaxDuration = (currentThreadDuration > MaxDuration) ? currentThreadDuration : MaxDuration;
            }
            mDownloadInfo.setTotalTime((int) (MaxDuration / 1000));
            sendBroadcast(ACTION_DOWNLOAD_FINISH);
        }
    }

    public synchronized void isTimeout() {
        if (!isTimeout) {
            sendBroadcast(ACTION_DOWNLOAD_EXCEPTION_NETWORK_TIMEOUT);
            isTimeout = true;
        }
    }

    public synchronized void updateProgress() {
        int speed = 0;
        int len = 0;
        for (ThreadInfo info : mThreadInfos) {
            len += info.getMDownloadSize();
            speed += info.getMSpeed();
        }
        speed = (int) (speed * 1f / mDownloadInfo.getThreadNum());
        mDownloadInfo.setDownloadLength(len);
        mDownloadInfo.setDownloadSpeed(speed);
        sendBroadcast(ACTION_DOWNLOAD_UPDATE);
    }

    private boolean isHaveEnoughRoom() {
        return mDownloadInfo.getFileSize() <= SystemUtils.getSDCardFreeRoom() - (10 * 1000 * 1000);
    }

    private boolean isHaveNetwork() {
        return SystemUtils.isNetworkConnected(CusApplication.getContext());
    }

    private boolean isDataError() {
        if (null == mDownloadInfo)
            return false;
        return TextUtils.isEmpty(mDownloadInfo.getUrl()) && TextUtils.isEmpty(mDownloadInfo.getFilePath());
    }

    private void sendBroadcast(String action) {
        Intent intent = new Intent(action);
        intent.putExtra("DOWNLOAD_INFO", mDownloadInfo);
        CusApplication.getContext().sendBroadcast(intent);
    }


    class DownloadStateListener implements DownloadEngine.DownloadStatusListener {
        @Override
        public void onProgress(ThreadInfo threadInfo) {
            updateProgress();
        }

        @Override
        public void onError() {
            sendBroadcast(ACTION_DOWNLOAD_EXCEPTION_OTHER);
        }

        @Override
        public void onPause() {

        }

        @Override
        public void onFinished(ThreadInfo info) {
            isFinished(info);
        }

        @Override
        public void onTimeout() {
            isTimeout();
        }
    }
}
