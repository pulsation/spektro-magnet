package eu.pulsation.alarmsandbox

import java.util.Calendar

import android.content.{BroadcastReceiver, Context, Intent}
import android.hardware.Sensor

/**
 * The alarm receiver is triggered when a scheduled alarm is fired. This class
 * reads the information in the intent and displays this information in the
 * Android notification bar. The notification uses the default notification
 * sound and it vibrates the phone.
 * 
 */
class AlarmSandboxReceiver extends BroadcastReceiver // with SensorEventListener
{

  override def onReceive(context: Context, intent: Intent) {
    new AlarmSandboxLocator(context)
    new AlarmSandboxSensors(context)
  }
}
