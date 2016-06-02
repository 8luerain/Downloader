package test.bluerain.youku.com.downloader.utils;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Project: Downloader.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    public static void getContentLength(final String url, final INetwrok<String> iNetwrok) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL u = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(10000);
                    connection.setConnectTimeout(10000);
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        String contentLength = connection.getHeaderField("Content-Length");
                        if (null != iNetwrok)
                            iNetwrok.onSuccess(contentLength);
                        Log.d(TAG, "run: contentLength [" + contentLength + "]");
                    }
                } catch (Exception e) {
                    if (null != iNetwrok)
                        iNetwrok.onFail();
                }
            }
        }).start();
    }


    public interface INetwrok<T> {
        void onSuccess(T t);

        void onFail();
    }
}
