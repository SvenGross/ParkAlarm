/*
 * @author: Dennis Gehrig, Sven Gross, Gabriel Daw
 * @date:   16. Juni 2015
 */
package ch.zkb.m335.parkalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ch.zkb.m335.parkalarm.model.ParkInfo;
import ch.zkb.m335.parkalarm.model.SerializeHelper;


public class MainActivity extends ActionBarActivity {
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        //Falls bereits eine Sequenz aktiv, weiterleiten auf RunningMainActivity
        SerializeHelper sh = new SerializeHelper();
        ParkInfo pi = sh.deserializeParkInfo();
        if (pi != null && pi.getName() != null) {
            Intent i = new Intent(this, RunningMainActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SETTINGS) {
            return false;
        }
        else {
            return true;
        }
    }

    //Weiterleiten auf ParkActivity
    public void park(View v) {
        Intent i = new Intent(this, ParkActivity.class);
        startActivity(i);
    }
}