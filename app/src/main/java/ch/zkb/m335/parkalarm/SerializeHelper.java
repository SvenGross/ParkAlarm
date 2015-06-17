package ch.zkb.m335.parkalarm;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class SerializeHelper {

    public void serializeParkInfo(String name,
                                  String floor,
                                  String lot,
                                  Date arrivalTime,
                                  long duration,
                                  double longitude,
                                  double latitude) {

        ParkInfo pi = new ParkInfo();
        pi.setName(name);
        pi.setFloor(floor);
        pi.setLot(lot);
        pi.setArrivalTime(arrivalTime);
        pi.setDuration(duration);
        pi.setLatitude(longitude);
        pi.setLatitude(latitude);

        try {

            FileOutputStream fout = MainActivity.mContext.openFileOutput("gabriel.xml", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(pi);
            oos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ParkInfo deserializeParkInfo() {

        ParkInfo parkInfo;
        try {
            FileInputStream fin = MainActivity.mContext.openFileInput("gabriel.xml");
            ObjectInputStream ois = new ObjectInputStream(fin);
            parkInfo = (ParkInfo) ois.readObject();
            ois.close();

            return parkInfo;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}



