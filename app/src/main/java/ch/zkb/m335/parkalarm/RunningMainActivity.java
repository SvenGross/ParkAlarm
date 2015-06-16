/*
 * @author: Dennis Gehrig
 * @date:   16. Juni 2015
 *
 */
package ch.zkb.m335.parkalarm;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


public class RunningMainActivity extends Activity {
    private ParkCountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private long timeElapsed;
    private TextView text;
    private TextView timeElapsedView;
    private Button stopTimer;

    //startTime bekomme ich durch das ParkInfo Objekt in Anzahl Minuten
    //man muss dann noch diese Minuten * 60000 rechnen
    private final long startTime = 180000;
    private long       secondsToGo = 0;
    private final long interval  = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_main);
        countDownTimer = new ParkCountDownTimer(startTime, interval);
        stopTimer = (Button) this.findViewById(R.id.startTimer);

        text = (TextView) this.findViewById(R.id.timer);
        timeElapsedView = (TextView) this.findViewById(R.id.timeElapsed);
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

    public void startTimer(View v){

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
            //Alarm einbauen
            stopTimer.setText("Time remain:" + 0);
            text.setText("Time's up!");
            timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTime));
        }

//        noch ändern! weil input ist in minuten
        @Override
        public void onTick(long millisUntilFinished)
        {
            double d;
            long minutesUntilFinished;
            d = (double)millisUntilFinished / 1000;
            minutesUntilFinished = Math.round(d) / 60;
            if(minutesUntilFinished != (startTime/60000)){
                minutesUntilFinished++;
            }
            stopTimer.setText("Time remain:" + minutesUntilFinished);
            timeElapsed = startTime - millisUntilFinished;
            timeElapsedView.setText("Time Elapsed: " + String.valueOf(timeElapsed));
        }
    }

}
