package test.bluerain.youku.com.downloader.net;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import test.bluerain.youku.com.ThreadInfo;
import test.bluerain.youku.com.downloader.db.ThreadDAOImpl;
import test.bluerain.youku.com.downloader.utils.CommonUtils;

/**
 * Project: Downloader.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class DownloadEngine implements Runnable {
    private static final String TAG = "DownloadEngine";

    private static final int DOWNLOAD_UPDATE = 246;
    private static final int DOWNLOAD_PAUSE = 247;
    private static final int DOWNLOAD_FINISHED = 248;
    private static final int DOWNLOAD_TIMEOUT = 249;
    private static final int DOWNLOAD_ERROR = 250;

    private final static int BUF_SIZE = 1024 * 4;
    private byte[] buf = new byte[BUF_SIZE];

    private ThreadInfo mThreadInfo;

    private ThreadDAOImpl mThreadDAO;

    private DownloadStatusListener mDownloadStatusListener;

    private boolean isPause;

    private int mFinishedLength = 0;
    private int mUsingTime = 0;

    private RandomAccessFile mRandomAccessFile;
    private File mFile;

    private static final int RETRY_TIME = 3;
    private int mRetryTime;

    private BufferedInputStream mConnectionBIS = null;
    private BufferedOutputStream mFileBOS = null;

    public DownloadEngine(ThreadInfo threadInfo) {
        mThreadInfo = threadInfo;
    }


    public void pauseTask() {
        isPause = true;
    }

    /**
     *
     */
    @Override
    public void run() {
        int peerReadLength = -1;
        long lastUpdateTime;
        long sendMessageTimeInterval = System.currentTimeMillis(); //发送消息的时间间隔

        int offSet = mThreadInfo.getMStartPosition() + mThreadInfo.getMDownloadSize();
        try {
            URL url = new URL(mThreadInfo.getMUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Range", "bytes=" + offSet + "-" + mThreadInfo.getMEndPosition());

//            mFileBOS =  new BufferedOutputStream(new FileOutputStream(mFile));
//            mRandomAccessFile.seek(offSet);
//            FileChannel channel = mRandomAccessFile.getChannel();
//            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, connection.getContentLength());

            if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL || connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                mConnectionBIS = new BufferedInputStream(connection.getInputStream());
                lastUpdateTime = System.currentTimeMillis();
                while ((peerReadLength = mConnectionBIS.read(buf)) != -1) {
//                    mFileBOS.write(buf,0,len);
//                    mRandomAccessFile.write(buf,0,len);
//                    map.put(buf,0,len);
//                    map.force();
                    long now = System.currentTimeMillis();
                    long duration = now - lastUpdateTime;//每次网络读取的时间间隔
                    lastUpdateTime = now;
                    /*下载总时间*/
                    mUsingTime += duration;
                    mThreadInfo.setDuration(mUsingTime);
                    /*下载总长度*/
                    mFinishedLength += peerReadLength;
                    mThreadInfo.setMDownloadSize(mFinishedLength);
                    /*下载速度*/
                    int downloadSpeed = getDownloadSpeed(duration, peerReadLength);
                    mThreadInfo.setMSpeed(downloadSpeed);
                    /*发送消息*/
                    if (now - sendMessageTimeInterval > 1000) {
                        sendMessage(DOWNLOAD_UPDATE);
                        sendMessageTimeInterval = now; //间隔1s发送一次
                    }
                    if (isPause) {
                        sendMessage(DOWNLOAD_PAUSE);
                        mThreadDAO.insert(mThreadInfo);
                        return;
                    }
                }
//                mFileBOS.flush();
                //下载完毕
                Log.d(TAG, "run: 线程[" + mThreadInfo.getMThreadId() + "]下载完毕，文件大小[" + (mThreadInfo
                        .getMEndPosition() - mThreadInfo.getMStartPosition()) + "]，已下载大小[" + mThreadInfo.getMDownloadSize() + "]");
                mThreadInfo.setMIsFinished(true);
                sendMessage(DOWNLOAD_FINISHED);
                mThreadDAO.remove(mThreadInfo);
            } else {
                sendMessage(DOWNLOAD_ERROR);
                Log.e(TAG, "run: 服务器返回错误信息！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            if ((mRetryTime++) < RETRY_TIME) {
                Log.d(TAG, "run: timeout retry times[" + mRetryTime + "]");
                run();
            } else {
                sendMessage(DOWNLOAD_TIMEOUT);
            }
        } finally {
            CommonUtils.closeQuiltly(mConnectionBIS);
        }

    }

    private int getDownloadSpeed(long duretion, int size) {
        int speed = (int) (size / (duretion / 1000f));
        return speed;
    }


    private void sendMessage(int what) {
        if (!isSetListener())
            return;
        switch (what) {
            case DOWNLOAD_UPDATE:
                mDownloadStatusListener.onProgress(mThreadInfo);// TODO: 2016/5/31
                break;
            case DOWNLOAD_PAUSE:
                mDownloadStatusListener.onPause();
                break;
            case DOWNLOAD_FINISHED:
                mDownloadStatusListener.onFinished(mThreadInfo);
                break;
            case DOWNLOAD_TIMEOUT:
                mDownloadStatusListener.onTimeout();
                break;
            case DOWNLOAD_ERROR:
                mDownloadStatusListener.onError();// TODO: 2016/5/31
                break;
        }
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(File file) {
        mFile = file;
    }

    public void setRandomAccessFile(RandomAccessFile randomAccessFile) {
        mRandomAccessFile = randomAccessFile;
    }

    private boolean isSetListener() {
        return null != mDownloadStatusListener;
    }

    public void setThreadDAO(ThreadDAOImpl threadDAO) {
        mThreadDAO = threadDAO;
    }

    public void setDownloadStatusListener(DownloadStatusListener downloadStatusListener) {
        mDownloadStatusListener = downloadStatusListener;
    }

    interface DownloadStatusListener {

        void onProgress(ThreadInfo threadInfo);

        void onPause();

        void onFinished(ThreadInfo threadInfo);

        void onTimeout();

        void onError();

    }
}
