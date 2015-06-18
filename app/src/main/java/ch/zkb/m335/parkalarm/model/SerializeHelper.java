/*
 * @author: Gabriel Daw
 * @date:   16. Juni 2015
 */
package ch.zkb.m335.parkalarm.model;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import ch.zkb.m335.parkalarm.MainActivity;
import ch.zkb.m335.parkalarm.R;

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
        pi.setLongitude(longitude);
        pi.setLatitude(latitude);
        try {
            FileOutputStream fout = MainActivity.mContext.openFileOutput(MainActivity.mContext.getString(R.string.filename)
                    , Context.MODE_PRIVATE);
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
            FileInputStream fin = MainActivity.mContext.openFileInput(MainActivity.mContext.getString(R.string.filename));
            ObjectInputStream ois = new ObjectInputStream(fin);
            parkInfo = (ParkInfo) ois.readObject();
            ois.close();

            return parkInfo;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void deleteParkInfo() {
        File dir = MainActivity.mContext.getFilesDir();
        File file = new File(dir, MainActivity.mContext.getString(R.string.filename));
        file.delete();
    }
}




