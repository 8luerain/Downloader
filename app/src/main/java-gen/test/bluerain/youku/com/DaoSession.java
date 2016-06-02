package test.bluerain.youku.com;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import test.bluerain.youku.com.ThreadInfo;

import test.bluerain.youku.com.ThreadInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig threadInfoDaoConfig;

    private final ThreadInfoDao threadInfoDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        threadInfoDaoConfig = daoConfigMap.get(ThreadInfoDao.class).clone();
        threadInfoDaoConfig.initIdentityScope(type);

        threadInfoDao = new ThreadInfoDao(threadInfoDaoConfig, this);

        registerDao(ThreadInfo.class, threadInfoDao);
    }
    
    public void clear() {
        threadInfoDaoConfig.getIdentityScope().clear();
    }

    public ThreadInfoDao getThreadInfoDao() {
        return threadInfoDao;
    }

}