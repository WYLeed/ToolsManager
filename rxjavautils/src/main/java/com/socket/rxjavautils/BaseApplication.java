package com.socket.rxjavautils;

import android.app.Application;

/**
 * Created by wangyu on 2018/12/12 0012.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
