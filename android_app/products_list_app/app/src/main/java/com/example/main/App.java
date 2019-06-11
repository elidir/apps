package com.example.main;

import android.app.Application;

import com.example.products.R;
import com.example.products.data.objects.DaoSession;


public class App extends Application {

    public interface AppConfiguration {
        boolean isTablet();
    }

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public static AppConfiguration config() {
        return getInstance().paramsHelper;
    }


    private final AppConfiguration paramsHelper = new ConfigurationInfo();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public DaoSession getDatabaseSession() {
        return Database.getSession();
    }

    private class ConfigurationInfo implements AppConfiguration {

        @Override
        public boolean isTablet() {
            return getResources().getBoolean(R.bool.is_tablet);
        }
    }
}
