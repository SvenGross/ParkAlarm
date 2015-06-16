package ch.zkb.m335.parkalarm;


import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class SaveHandler {

    public void test() {
        ParkInfo parkInfo = new ParkInfo();
        //parkInfo.setAlarm(394829389);
        parkInfo.setName("Testname");
        parkInfo.setEtage("5");
        Date anZeit = new Date();
        anZeit.setTime(123456789);
        parkInfo.setAnZeit(anZeit);
    }

    String filename = "ParkInfo.xml";
    FileOutputStream outputStream;

    public void saveToXml(ParkInfo pi) {
        File file = new File(ContextHelper.getAppContext().getFilesDir(), filename);








        }


    }

