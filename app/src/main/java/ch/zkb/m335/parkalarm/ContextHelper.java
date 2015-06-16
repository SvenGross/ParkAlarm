package ch.zkb.m335.parkalarm;

import android.app.Application;
import android.content.Context;

public class ContextHelper extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ContextHelper.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ContextHelper.context;
    }
}

