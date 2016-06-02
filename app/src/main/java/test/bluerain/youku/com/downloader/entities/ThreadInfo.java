package test.bluerain.youku.com.downloader.entities;

/**
 * Project: Downloader.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class ThreadInfo {
    public int mId;
    public String mUrl;
    public int mTotalSize; //任务总大小
    public int mDownloadSize; //已下载大小
    public int mSpeed;//下载速度


    public ThreadInfo() {
    }

    public ThreadInfo(int id, String url, int count, int currentPositon, int speed) {
        mId = id;
        mUrl = url;
        mTotalSize = count;
        mDownloadSize = currentPositon;
        mSpeed = speed;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public int getTotalSize() {
        return mTotalSize;
    }

    public void setTotalSize(int totalSize) {
        mTotalSize = totalSize;
    }

    public int getDownloadSize() {
        return mDownloadSize;
    }

    public void setDownloadSize(int downloadSize) {
        mDownloadSize = downloadSize;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
    }
}
