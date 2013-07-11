package eu.pulsation.alarmsandbox

import java.util.Calendar

import android.app.{Notification, NotificationManager, PendingIntent}
import android.content.{BroadcastReceiver, Context, Intent}
import android.os.Bundle
import android.util.Log

/**
 * The alarm receiver is triggered when a scheduled alarm is fired. This class
 * reads the information in the intent and displays this information in the
 * Android notification bar. The notification uses the default notification
 * sound and it vibrates the phone.
 * 
 */
class AlarmSandboxReceiver extends BroadcastReceiver 
{

  override def onReceive(context: Context, intent: Intent) {
      Log.i("AlarmSandboxReceiver", "===> AlarmSandboxReceiver invoked! <===")
      return;
    }

}
