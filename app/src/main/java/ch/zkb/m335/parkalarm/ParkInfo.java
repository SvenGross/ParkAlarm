package ch.zkb.m335.parkalarm;

import android.util.TimeUtils;

import java.util.Date;


public class ParkInfo {
    String name;
    String etage;
    String parkNr;
    Date anZeit;
    long alarm;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEtage() {
        return etage;
    }

    public void setEtage(String etage) {
        this.etage = etage;
    }

    public Date getAnZeit() {
        return anZeit;
    }

    public void setAnZeit(Date anZeit) {
        this.anZeit = anZeit;
    }

    public long getAlarm() {
        return alarm;
    }

    public void setAlarm(long alarm) {
        this.alarm = alarm;
    }
}

