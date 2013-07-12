package eu.pulsation.alarmsandbox

import java.util.Calendar

import android.content.{BroadcastReceiver, Context, Intent}
import android.util.Log
import android.location.{LocationManager, Location, LocationListener}

import android.os.Bundle

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
      
    class AlarmSandboxLocator extends LocationListener {
  
      def getLocManager() = {
        context.getSystemService(Context.LOCATION_SERVICE) match {
          case lm: LocationManager => lm
          case _ => throw new ClassCastException
        }
      }
  
      def onLocationChanged (location: Location) = {
        Log.i("AlarmSandboxReceiver", "===> Got location update! " + location + " <===")
      }

      def onProviderDisabled (provider: String) = {}

      def onProviderEnabled (provider: String) = {}
      
      def onStatusChanged (provider: String, status: Int, extras: Bundle)  = {}

      val locManager:LocationManager = getLocManager()
      locManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null)
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

    new AlarmSandboxLocator()
    new AlarmSandboxAccelerometer().getAccelerometer()
  }


}
