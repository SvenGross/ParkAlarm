package ch.zkb.m335.parkalarm.ch.zkb.m335.parkalarm.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by dsghrg on 17.06.15.
 */
public class MyService extends Service {

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int falgs, int startId){
//        We want this service to continue running until it is explicitly
//        stopped, so return sticky
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}
