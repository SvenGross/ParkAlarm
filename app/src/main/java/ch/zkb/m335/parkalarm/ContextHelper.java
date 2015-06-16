package ch.zkb.m335.parkalarm;

import android.app.Application;
import android.content.Context;

/**
 * Created by m335b04.it2015 on 16.06.2015.
 */
public class ContextHelper extends Application {

        private static Context context;

        public void onCreate(){
            super.onCreate();
            ContextHelper.context = getApplicationContext();
        }

        public static Context getAppContext() {
            return ContextHelper.context;
        }
    }
}
