package test.bluerain.youku.com.downloader;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Project: Downloader.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class DownloadManager {

    private static final DownloadManager INSTANCE = new DownloadManager();

    private ThreadPoolExecutor mThreadPoolExecutor;

    public static DownloadManager getInstance() {
        return INSTANCE;
    }

    private DownloadManager() {
        //no instance
        mThreadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Profile.THREAD_NUM);

    }


    class InitState implements IDownloadInterface {
        @Override
        public void start(DownloadInfo info) {

        }

        @Override
        public void pause(DownloadInfo info) {

        }

        @Override
        public void cancle(DownloadInfo info) {

        }
    }

    class DownloadIngState implements IDownloadInterface {
        @Override
        public void start(DownloadInfo info) {

        }

        @Override
        public void pause(DownloadInfo info) {

        }

        @Override
        public void cancle(DownloadInfo info) {

        }
    }

    class PauseState implements IDownloadInterface {
        @Override
        public void start(DownloadInfo info) {

        }

        @Override
        public void pause(DownloadInfo info) {

        }

        @Override
        public void cancle(DownloadInfo info) {

        }
    }


    class ErrorState implements IDownloadInterface {
        @Override
        public void start(DownloadInfo info) {

        }

        @Override
        public void pause(DownloadInfo info) {

        }

        @Override
        public void cancle(DownloadInfo info) {

        }
    }

    interface IDownloadInterface {

        void start(DownloadInfo info);

        void pause(DownloadInfo info);

        void cancle(DownloadInfo info);
    }


}
