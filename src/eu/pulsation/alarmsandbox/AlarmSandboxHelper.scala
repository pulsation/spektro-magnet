package eu.pulsation.alarmsandbox

import java.util.{Calendar, Map, Set, Date}

import android.app.{AlarmManager, PendingIntent}
import android.content.{Context, Intent, SharedPreferences, BroadcastReceiver, IntentFilter}
import android.util.Log

/**
 * Helper class for the LocalNotification plugin. This class is reused by the
 * AlarmRestoreOnBoot.
 * 
 * @see LocalNotification
 * @see AlarmRestoreOnBoot
 * 
 * @author dvtoever
 */
class AlarmSandboxHelper(context: Context) {

  def addAlarm() {

    def getAlarmManager() : AlarmManager = {
      context.getSystemService(Context.ALARM_SERVICE) match {
        case am: AlarmManager => am
        case _ => throw new ClassCastException
      }
    }

    val am:AlarmManager = getAlarmManager()

    val sandboxBroadcastReceiver:BroadcastReceiver = new AlarmSandboxReceiver()

    context.registerReceiver (sandboxBroadcastReceiver, new IntentFilter ("my.alarm.action"))

    val receiverIntent:Intent = new Intent()
    receiverIntent.setAction("my.alarm.action")

    val scheduledReceiver:PendingIntent = PendingIntent.getBroadcast(this.context, 0, receiverIntent, PendingIntent.FLAG_CANCEL_CURRENT)

    am.setRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime() , 60 * 1000,  scheduledReceiver)

    Log.i("AlarmSandboxActivity", "===> Alarm added! <===")
  }

  /**
   * @see LocalNotification#cancelNotification(int)
   */
  def cancelAlarm() {
    // TODO
  }

}
