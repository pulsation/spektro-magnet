package eu.pulsation.alarmsandbox

import java.util.Calendar

import android.content.{BroadcastReceiver, Context, Intent}
import android.util.Log
import android.location.LocationManager
import android.location.Location

import android.hardware.{SensorEventListener, SensorManager, SensorEvent, Sensor}

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

    class AlarmSandboxAccelerometer extends SensorEventListener {
      def getSensorManager() = {
        context.getSystemService(Context.SENSOR_SERVICE) match {
          case sm: SensorManager => sm
          case _ => throw new ClassCastException
        }
      }

      val sensorManager = getSensorManager()

      def getAccelerometer() = {
        val accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL)
      }

      def onSensorChanged(event: SensorEvent) {
        Log.i("AlarmSandboxReceiver", "===> Accelerator value received!" + event.values(0) + " <===")
        sensorManager.unregisterListener(this)
      }

      def onAccuracyChanged(sensor: Sensor, accuracy: Int) = {

      }
    }

    val lastLoc: Location = getLastLocation ()
    Log.i("AlarmSandboxReceiver", "===> AlarmSandboxReceiver invoked! " + lastLoc + " <===")

    new AlarmSandboxAccelerometer().getAccelerometer()
  }


}
