package ch.zkb.m335.parkalarm;

import android.util.TimeUtils;

import java.util.Date;


public class ParkInfo {
    String name;
    String etage;
    String parkNr;
    Date anZeit;
    long dauer;

    public String getParkNr() {
        return parkNr;
    }

    public void setParkNr(String parkNr) {
        this.parkNr = parkNr;
    }


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

    public long getDauer() {
        return dauer;
    }

    public void setDauer(long dauer) {
        this.dauer = dauer;
    }
}

