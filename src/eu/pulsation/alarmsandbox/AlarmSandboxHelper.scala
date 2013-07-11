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

    val am:AlarmManager = getAlarmManager()

    val br:BroadcastReceiver = new AlarmSandboxReceiver()

    context.registerReceiver (br, new IntentFilter ("my.alarm.action"))

    val i:Intent = new Intent()
    i.setAction("my.alarm.action")

    val pi:PendingIntent = PendingIntent.getBroadcast(this.context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT)

    am.setRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime() , 5000,  pi)

    Log.i("AlarmSandboxActivity", "===> Alarm added! <===")
  }

  /**
   * @see LocalNotification#cancelNotification(int)
   */
  def cancelAlarm() {
    // TODO
  }

  def getAlarmManager() : AlarmManager = {
    context.getSystemService(Context.ALARM_SERVICE) match {
      case am: AlarmManager => am
      case _ => throw new ClassCastException
    }
  }
}
