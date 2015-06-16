package ch.zkb.m335.parkalarm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class SerializeHelper {

    public void serializeParkInfo(String name
            , String etage
            , String parkNr
            , Date anZeit
            , long dauer) {

        ParkInfo pi = new ParkInfo();
        pi.setName(name);
        pi.setFloor(etage);
        pi.setLot(parkNr);
        pi.setArrivalTime(anZeit);
        pi.setDuration(dauer);

        try {

            FileOutputStream fout = new FileOutputStream(ContextHelper.getAppContext().getFilesDir());
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
            FileInputStream fin = new FileInputStream(ContextHelper.getAppContext().getFilesDir());
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



