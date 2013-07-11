package eu.pulsation.alarmsandbox;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.Date;

import android.content.BroadcastReceiver;

import android.content.IntentFilter;

/**
 * Helper class for the LocalNotification plugin. This class is reused by the
 * AlarmRestoreOnBoot.
 * 
 * @see LocalNotification
 * @see AlarmRestoreOnBoot
 * 
 * @author dvtoever
 */
public class AlarmSandboxHelper {

    private Context ctx;

    public AlarmSandboxHelper(Context context) {
	this.ctx = context;
    }

    /**
     * @see LocalNotification#add(boolean, String, String, String, int,
     *      Calendar)
     */
    public boolean addAlarm() {

	final AlarmManager am = getAlarmManager();

  final BroadcastReceiver br = new AlarmSandboxReceiver();

  this.ctx.registerReceiver (br,new IntentFilter ("my.alarm.action"));

  Intent i = new Intent();
  i.setAction("my.alarm.action");
  
	final PendingIntent pi = PendingIntent.getBroadcast(this.ctx, 0, i, /*PendingIntent.FLAG_CANCEL_CURRENT*/ 0);

	    am.setRepeating(/*AlarmManager.RTC_WAKEUP*/0, new Date().getTime() , 5000,  pi);

		    Log.i("AlarmSandboxActivity", "===> Alarm added! <===");

	return true;
    }

    /**
     * @see LocalNotification#cancelNotification(int)
     */
    public boolean cancelAlarm(/*String notificationId*/) {
	/*
	 * Create an intent that looks similar, to the one that was registered
	 * using add. Making sure the notification id in the action is the same.
	 * Now we can search for such an intent using the 'getService' method
	 * and cancel it.
	 */
	final Intent intent = new Intent(this.ctx, AlarmSandboxReceiver.class);
	return true;
    }

    private AlarmManager getAlarmManager() {
	final AlarmManager am = (AlarmManager) this.ctx.getSystemService(Context.ALARM_SERVICE);

	return am;
    }
}
