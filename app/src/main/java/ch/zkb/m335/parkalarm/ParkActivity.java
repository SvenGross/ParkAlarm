package ch.zkb.m335.parkalarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ParkActivity extends FragmentActivity {

    private String name;
    private String floor;
    private String lot;
    private String arrivalTime;
    private String duration;

    private EditText field_name;
    private EditText field_floor;
    private EditText field_lot;
    private EditText field_arrival;
    private EditText field_duration;
    private ToggleButton button_alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);

        field_name = (EditText) findViewById(R.id.editText_name);
        field_floor = (EditText) findViewById(R.id.editText_floor);
        field_lot = (EditText) findViewById(R.id.editText_lot);
        field_arrival = (EditText) findViewById(R.id.editText_arrival);
        field_duration = (EditText) findViewById(R.id.editText_timer);
        button_alarm = (ToggleButton) findViewById(R.id.toggleButton_alarm);

        field_arrival.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_park, menu);
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

    public void timePicker(View v) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(), "parkActivityTimePicker");
        timePickerFragment.setParkActivity(this);
    }

    public void saveParkInfo(View v) {
        name = field_name.getText().toString();
        floor = field_floor.getText().toString();
        lot = field_lot.getText().toString();
        arrivalTime = field_arrival.getText().toString();
        duration = field_duration.getText().toString();

        if(name.isEmpty()) {
            field_name.setError(getString(R.string.error_field_name));
        }
        else {
            if (button_alarm.isChecked() && duration.isEmpty()) {
                field_duration.setError(getString(R.string.error_field_timer));
            } else {
                Date arrivalDatetime = new Date();
                if(!arrivalTime.isEmpty()) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    try {
                        arrivalDatetime = simpleDateFormat.parse(arrivalTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("ParkActivity", "saveParkInfo start");
                SerializeHelper sh = new SerializeHelper();
                sh.serializeParkInfo("Super-Parkplatz", "15a", "5", arrivalDatetime, 12345, getApplicationContext());
                ParkInfo pi = sh.deserializeParkInfo(getApplicationContext());

                Intent i = new Intent(this, RunningMainActivity.class);
                startActivity(i);
            }
        }
    }

    public void setArrivalInView(String arrivalTime) {
        field_arrival.setText(arrivalTime);
    }
}