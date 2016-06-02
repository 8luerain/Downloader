package test.bluerain.youku.com.downloader.db;

import java.util.List;

import test.bluerain.youku.com.ThreadInfo;

/**
 * Project: Downloader.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public interface ThreadDAO {

    void insert(ThreadInfo info);

    void remove(ThreadInfo info);

    void update(ThreadInfo info);

    List<ThreadInfo> query(String url);
}
