package test.bluerain.youku.com.downloader.entities;

import java.util.List;

import test.bluerain.youku.com.ThreadInfo;

/**
 * Project: Downloader.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class DownloadTaskInfo {

    private String mDownloadUrl;
    private String mFileSavePath;
    private long mFileSize;

    private List<ThreadInfo> mThreadInfos;

    public DownloadTaskInfo() {
    }

    public DownloadTaskInfo(String downloadUrl, String fileSavePath, List<ThreadInfo> threadInfos) {
        mDownloadUrl = downloadUrl;
        mFileSavePath = fileSavePath;
        mThreadInfos = threadInfos;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public String getFileSavePath() {
        return mFileSavePath;
    }

    public void setFileSavePath(String fileSavePath) {
        mFileSavePath = fileSavePath;
    }

    public List<ThreadInfo> getThreadInfos() {
        return mThreadInfos;
    }

    public void setThreadInfos(List<ThreadInfo> threadInfos) {
        mThreadInfos = threadInfos;
    }

    public long getFileSize() {
        return mFileSize;
    }

    public void setFileSize(long fileSize) {
        mFileSize = fileSize;
    }
}
