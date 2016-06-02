package test.bluerain.youku.com.downloader;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import test.bluerain.youku.com.DaoMaster;

/**
 * Project: Downloader.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class CusApplication extends Application {
    private static DaoMaster mDaoMaster;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initDao();
    }

    private void initDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, Profile.DB_NAME_THREAD, null);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(writableDatabase);
    }

    public static DaoMaster getThreadDaoMaster() {
        return mDaoMaster;
    }

    public static Context getContext() {
        return sContext;
    }
}
