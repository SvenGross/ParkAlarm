/*
 * @author: Dennis Gehrig
 * @date:   17. Juni 2015
 */
package ch.zkb.m335.parkalarm;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ch.zkb.m335.parkalarm.model.ParkInfo;
import ch.zkb.m335.parkalarm.model.SerializeHelper;
import ch.zkb.m335.parkalarm.services.MyService;

public class RunningMainActivity extends Activity {

    private ParkCountDownTimer countDownTimer = null;
    private boolean timerCanceled = false;
    private final long interval = 1000;

    private SerializeHelper sh = new SerializeHelper();
    private ParkInfo pi = sh.deserializeParkInfo();

    private TextView field_name;
    private TextView field_floor;
    private TextView field_lot;
    private TextView field_arrival;
    private Button button_timer;

    private String name = null;
    private String floor = null;
    private String lot = null;
    private Date arrivalTime = new Date();
    private long duration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_main);

        field_name = (TextView) findViewById(R.id.textfield_name);
        field_floor = (TextView) findViewById(R.id.textfield_floor);
        field_lot = (TextView) findViewById(R.id.textfield_lot);
        field_arrival = (TextView) findViewById(R.id.textfield_arrival);
        button_timer = (Button) findViewById(R.id.button_timer);

//        if null there is no data in the file
        if(pi != null){
            name = pi.getName();
            floor = pi.getFloor();
            lot = pi.getLot();
            arrivalTime = pi.getArrivalTime();
            duration = pi.getDuration();
        }

        field_name.setText(name);
        field_floor.setText(floor);
        field_lot.setText(lot);
        field_arrival.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(arrivalTime));
        button_timer = (Button) this.findViewById(R.id.button_timer);

        startService(new Intent(getBaseContext(), MyService.class));
        if(duration != 0) {
            countDownTimer = new ParkCountDownTimer(duration, interval);
            countDownTimer.start();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, getString(R.string.error_button_return), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_running_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void stopSequence(View v) {
        new AlertDialog.Builder(this)
            .setTitle(getString(R.string.stop_timer_popup_title))
            .setMessage(getString(R.string.stop_timer_popup_message))
                .setIcon(R.mipmap.ic_parking)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    goToMain();
                    sh.deleteParkInfo();
                    if (countDownTimer != null) {
                        timerCanceled = true;
                        countDownTimer.cancel();
                    }

                }
            })
            .setNegativeButton(android.R.string.no, null).show();
    }

    public void goToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void goToMap(View v) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    // CountDownTimer class
    public class ParkCountDownTimer extends CountDownTimer
    {

        public ParkCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            button_timer.setText(getString(R.string.timer_message_finish));
            stopService(new Intent(getBaseContext(), MyService.class));

            if(!timerCanceled) {
                //Notification, wenn ParkTimer abgelaufen
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.mContext.getApplicationContext());

                mBuilder.setSmallIcon(R.mipmap.ic_parking);
                mBuilder.setContentTitle(getString(R.string.notification_title));
                mBuilder.setContentText(getString(R.string.notification_message));

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // notificationID allows you to update the notification later on.
                mNotificationManager.notify(88, mBuilder.build());
            }
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            double d;
            long minutesUntilFinished;
            d = (double)millisUntilFinished / 1000;
            minutesUntilFinished = Math.round(d) / 60;
            if(minutesUntilFinished != (duration / 60000)) {
                minutesUntilFinished++;
            }
            button_timer.setText(minutesUntilFinished + getString(R.string.timer_message_remaining));
        }
    }
}
