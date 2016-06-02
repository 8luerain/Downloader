package test.bluerain.youku.com;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "THREAD_INFO".
 */
public class ThreadInfo {

    private Long id;
    private int mThreadId;
    /** Not-null value. */
    private String mUrl;
    private int mStartPosition;
    private int mEndPosition;
    private int mDownloadSize;
    private int mSpeed;
    private Boolean mIsFinished;
    private long mDuration;

    public ThreadInfo() {
    }

    public ThreadInfo(Long id) {
        this.id = id;
    }

    public ThreadInfo(Long id, int mThreadId, String mUrl, int mStartPosition, int mEndPosition, int mDownloadSize, int mSpeed, Boolean mIsFinished) {
        this.id = id;
        this.mThreadId = mThreadId;
        this.mUrl = mUrl;
        this.mStartPosition = mStartPosition;
        this.mEndPosition = mEndPosition;
        this.mDownloadSize = mDownloadSize;
        this.mSpeed = mSpeed;
        this.mIsFinished = mIsFinished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMThreadId() {
        return mThreadId;
    }

    public void setMThreadId(int mThreadId) {
        this.mThreadId = mThreadId;
    }

    /** Not-null value. */
    public String getMUrl() {
        return mUrl;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public int getMStartPosition() {
        return mStartPosition;
    }

    public void setMStartPosition(int mStartPosition) {
        this.mStartPosition = mStartPosition;
    }

    public int getMEndPosition() {
        return mEndPosition;
    }

    public void setMEndPosition(int mEndPosition) {
        this.mEndPosition = mEndPosition;
    }

    public int getMDownloadSize() {
        return mDownloadSize;
    }

    public void setMDownloadSize(int mDownloadSize) {
        this.mDownloadSize = mDownloadSize;
    }

    public int getMSpeed() {
        return mSpeed;
    }

    public void setMSpeed(int mSpeed) {
        this.mSpeed = mSpeed;
    }

    public Boolean getMIsFinished() {
        return mIsFinished;
    }

    public void setMIsFinished(Boolean mIsFinished) {
        this.mIsFinished = mIsFinished;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }
}
