/*
 * @author: Dennis Gehrig
 * @date:   16. Juni 2015
 */
package ch.zkb.m335.parkalarm.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int falgs, int startId){
//        We want this service to continue running until it is explicitly
//        stopped, so return sticky
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
