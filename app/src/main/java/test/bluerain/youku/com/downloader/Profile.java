package test.bluerain.youku.com.downloader;

import test.bluerain.youku.com.downloader.utils.SystemUtils;

/**
 * Project: Downloader.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class Profile {
    public static final String DB_NAME_THREAD = "THREAD.DB";

    public static int THREAD_NUM = SystemUtils.getCPUCoreNum();
}
