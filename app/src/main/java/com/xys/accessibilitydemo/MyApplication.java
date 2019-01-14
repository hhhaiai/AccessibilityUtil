package com.xys.accessibilitydemo;

import android.app.Application;
import android.content.Context;

import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;

/**
 * @Copyright © 2018 EGuan Inc. All rights reserved.
 * @Description: TODO
 * @Version: 1.0
 * @Create: 18/2/27 18:01
 * @Author: sanbo
 */
public class MyApplication extends Application {
    private static Context mContext = null;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .trackAllFragments()
                .setChannel("XXX应用商店"));

    }
}
