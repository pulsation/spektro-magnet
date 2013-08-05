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

  private def getAlarmManager() : AlarmManager = {
    context.getSystemService(Context.ALARM_SERVICE) match {
      case am: AlarmManager => am
      case _ => throw new ClassCastException
    }
  }

  def startAlarm() {

    val am:AlarmManager = this.getAlarmManager()

    val sandboxBroadcastReceiver:BroadcastReceiver = new AlarmSandboxReceiver()

    context.registerReceiver (sandboxBroadcastReceiver, new IntentFilter ("my.alarm.action"))

    val receiverIntent:Intent = new Intent()
    receiverIntent.setAction("my.alarm.action")

    val scheduledReceiver:PendingIntent = PendingIntent.getBroadcast(this.context, 0, receiverIntent, PendingIntent.FLAG_CANCEL_CURRENT)

    am.setRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime() , 60 * 1000,  scheduledReceiver)

  }

  /**
   * @see LocalNotification#cancelNotification(int)
   */
  def stopAlarm() = {
    // TODO

    val intent : Intent = new Intent(context, classOf[AlarmSandboxReceiver])
    intent.setAction("my.alarm.action")

    val pi : PendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    val am : AlarmManager = this.getAlarmManager()

    am.cancel(pi)
  }

}
