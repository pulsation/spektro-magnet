package eu.pulsation.alarmsandbox;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * The alarm receiver is triggered when a scheduled alarm is fired. This class
 * reads the information in the intent and displays this information in the
 * Android notification bar. The notification uses the default notification
 * sound and it vibrates the phone.
 * 
 */
public class AlarmSandboxReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
    //Toast.makeText(context, "Rise and Shine!", 1000).show();
		Log.i("AlarmSandboxReceiver", "===> AlarmSandboxReceiver invoked! <===");
			return;
		}

}
