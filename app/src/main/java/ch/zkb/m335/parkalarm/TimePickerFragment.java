package ch.zkb.m335.parkalarm;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private ParkActivity parkActivity = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int hour;
        int minute;

        if(parkActivity == null) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }
        else {
            hour = Integer.parseInt(parkActivity.getArrivalTime().substring(0, 2));
            minute = Integer.parseInt(parkActivity.getArrivalTime().substring(3, 5));
        }

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        parkActivity.setArrivalInView(hourOfDay + ":" + minute);
    }

    public void setParkActivity(ParkActivity parkActivity) {
        this.parkActivity = parkActivity;
    }
}
