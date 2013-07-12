package eu.pulsation.alarmsandbox

import java.util.Calendar

import android.app.{Notification, NotificationManager, PendingIntent}
import android.content.{BroadcastReceiver, Context, Intent}
import android.os.Bundle
import android.util.Log
import android.location.LocationManager
import android.location.Location

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
      
    def getLastLocation() = {

      def getLocManager() = {
        context.getSystemService(Context.LOCATION_SERVICE) match {
          case lm: LocationManager => lm
          case _ => throw new ClassCastException
        }
      }

      val locManager:LocationManager = getLocManager()
      locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
    }

    val lastLoc: Location = getLastLocation ()
    Log.i("AlarmSandboxReceiver", "===> AlarmSandboxReceiver invoked! " + lastLoc + " <===")
  }


}
