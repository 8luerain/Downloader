package test.bluerain.youku.com.downloader.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Project: Downloader.
 * Data: 2016/6/1.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class CommonUtils {

    public static int getInt(String s) {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static void closeQuiltly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
