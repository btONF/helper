package com.bt.helper.helper.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bt.helper.helper.BuildConfig;
import com.bt.helper.helper.Model.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Created by 18030693 on 2018/8/10.
 */

public class AppUtil extends Application{
    private static Context context;

    private static BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        if (context == null) {
            context = this;
        }
        if (boxStore == null) {
            boxStore = MyObjectBox.builder().androidContext(AppUtil.this).build();
        }
        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(boxStore).start(this);
            Log.i("ObjectBrowser", "Started: " + started);
        }
    }

    public static Context getInstance(){
        return context;
    }

    public static BoxStore getBoxStore() {
        return boxStore;
    }
}
