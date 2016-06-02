package test.bluerain.youku.com.downloader;

import java.io.Serializable;

/**
 * Project: Downloader.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class DownloadInfo implements Serializable {

    private String mUrl;
    private int mThreadNum;
    private String mFilePath;
    private int mFileSize;
    private int mDownloadSpeed;
    private int mDownloadLength;
    private int mTotalTime;

    public DownloadInfo(String url, int threadNum, String filePath) {
        mUrl = url;
        mThreadNum = threadNum;
        mFilePath = filePath;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }

    public int getThreadNum() {
        return mThreadNum;
    }

    public void setThreadNum(int threadNum) {
        mThreadNum = threadNum;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public int getFileSize() {
        return mFileSize;
    }

    public void setFileSize(int fileSize) {
        mFileSize = fileSize;
    }

    public int getDownloadSpeed() {
        return mDownloadSpeed;
    }

    public void setDownloadSpeed(int downloadSpeed) {
        mDownloadSpeed = downloadSpeed;
    }

    public int getDownloadLength() {
        return mDownloadLength;
    }

    public void setDownloadLength(int downloadLength) {
        mDownloadLength = downloadLength;
    }

    public int getTotalTime() {
        return mTotalTime;
    }

    public void setTotalTime(int totalTime) {
        mTotalTime = totalTime;
    }
}
