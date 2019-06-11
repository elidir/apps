package com.example.main;

import com.example.products.data.objects.DaoMaster;
import com.example.products.data.objects.DaoSession;

class Database {

    static DaoSession getSession() {
        return SingletonHolder.INSTANCE.getDaoSession();
    }

    private static class SingletonHolder {
        static final Database INSTANCE = new Database();
    }

    private final DaoSession daoSession;

    private Database() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(App.getInstance(), "goods-db");
        org.greenrobot.greendao.database.Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    private DaoSession getDaoSession() {
        return daoSession;
    }
}
