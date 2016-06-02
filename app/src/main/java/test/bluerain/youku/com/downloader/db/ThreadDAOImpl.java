package test.bluerain.youku.com.downloader.db;

import java.util.Iterator;
import java.util.List;

import de.greenrobot.dao.query.Query;
import test.bluerain.youku.com.ThreadInfo;
import test.bluerain.youku.com.ThreadInfoDao;
import test.bluerain.youku.com.downloader.CusApplication;

/**
 * Project: Downloader.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class ThreadDAOImpl implements ThreadDAO {
    private static ThreadDAOImpl ourInstance = new ThreadDAOImpl();

    private ThreadInfoDao mThreadInfoDao;

    public static ThreadDAOImpl getInstance() {
        return ourInstance;
    }

    private ThreadDAOImpl() {
        mThreadInfoDao = CusApplication.getThreadDaoMaster().newSession().getThreadInfoDao();
    }


    @Override
    public void insert(ThreadInfo info) {

        mThreadInfoDao.insert(info);

    }

    @Override
    public void remove(ThreadInfo info) {
        Query<ThreadInfo> build = mThreadInfoDao.queryBuilder()
                .where(ThreadInfoDao.Properties.MUrl.eq(info.getMUrl()), ThreadInfoDao.Properties.MThreadId.eq(info.getMThreadId()))
                .build();
        List<ThreadInfo> list = build.list();
        Iterator<ThreadInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            mThreadInfoDao.delete(iterator.next());
        }
    }

    @Override
    public void update(ThreadInfo info) {
        mThreadInfoDao.update(info);

    }


    @Override
    public List<ThreadInfo> query(String url) {
        Query<ThreadInfo> build = mThreadInfoDao.queryBuilder()
                .where(ThreadInfoDao.Properties.MUrl.eq(url))
                .orderAsc(ThreadInfoDao.Properties.Id)
                .build();
        return build.list();
    }
}
