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
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ch.zkb.m335.parkalarm.model.ParkInfo;
import ch.zkb.m335.parkalarm.model.SerializeHelper;
import ch.zkb.m335.parkalarm.services.MyService;
import ch.zkb.m335.parkalarm.util.StopSequenceDialogFragment;

public class RunningMainActivity extends Activity {

    private ParkCountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private final long interval = 1000;

    private SerializeHelper sh = new SerializeHelper();
    private ParkInfo pi = sh.deserializeParkInfo();

    private EditText field_name;
    private EditText field_floor;
    private EditText field_lot;
    private EditText field_arrival;
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
//        if pi is not null (if there is data in the file)
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

        if(duration != 0) {
            countDownTimer = new ParkCountDownTimer(duration, interval);
        }
        else {
//            TODO: Zähler(Zeit) nach oben (Aktuelle Zeit - Ankunftszeit)
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
                .setTitle("Title")
                .setMessage("Do you really want to whatever?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(RunningMainActivity.this, "Yaay", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
//        StopSequenceDialogFragment stopSequenceDialogFragment = new StopSequenceDialogFragment();
    }

    public void startExternalMap() {
        SerializeHelper sh = new SerializeHelper();
        ParkInfo pi = sh.deserializeParkInfo();
        String latitude = String.valueOf(pi.getLatitude());
        String longitude = String.valueOf(pi.getLongitude());

        String uri = String.format(Locale.GERMAN, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void goToMap(View v) {
        Log.d("MainActivity", "Redirect to MapsActivity");
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setSmallIcon(R.drawable.ic_media_play);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(88, mBuilder.build());
    }

    public void startTimer(View v){
        startService(new Intent(getBaseContext(), MyService.class));
        if (!timerHasStarted)
        {
            Log.d("startTimer-Method", "startTimer-Method");
            countDownTimer.start();
            timerHasStarted = true;
            //stopTimer.setText("Start Timer");
        }
        else
        {
            countDownTimer.cancel();
            timerHasStarted = false;
            //stopTimer.setText("RESET");
        }
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
//            TODO: Alarm einbauen
            button_timer.setText("Zeit abgelaufen");
            stopService(new Intent(getBaseContext(), MyService.class));
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            double d;
            long minutesUntilFinished;
            d = (double)millisUntilFinished / 1000;
            minutesUntilFinished = Math.round(d) / 60;
            if(minutesUntilFinished != (duration / 60000)){
                minutesUntilFinished++;
            }
            button_timer.setText(minutesUntilFinished + "Minuten übrig");
        }
    }

//    public void startService(View v){
//        startService(new Intent(getBaseContext(), MyService.class));
//    }
//
//    public void stopService(View v){
//        stopService(new Intent(getBaseContext(), MyService.class));
//    }

}
