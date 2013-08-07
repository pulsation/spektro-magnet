package eu.pulsation.spektromagnet

import android.content.{BroadcastReceiver, Context, Intent}

/**
 * The alarm receiver is triggered when a scheduled alarm is fired. This class
 * reads the information in the intent and displays this information in the
 * Android notification bar. The notification uses the default notification
 * sound and it vibrates the phone.
 * 
 */
class SpektroMagnetReceiver extends BroadcastReceiver // with SensorEventListener
{

  override def onReceive(context: Context, intent: Intent) {
    new SpektroMagnetLocator(context)
    new SpektroMagnetSensors(context)
  }

}
