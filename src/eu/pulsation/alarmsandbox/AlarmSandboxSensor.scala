package eu.pulsation.alarmsandbox

import android.hardware.{SensorEventListener, SensorManager, SensorEvent, Sensor}
import android.content.{Context}
import android.util.Log

class AlarmSandboxSensor(context: Context, sensorType: Int) extends SensorEventListener {

  def getSensorManager() = {
    context.getSystemService(Context.SENSOR_SERVICE) match {
      case sm: SensorManager => sm
      case _ => throw new ClassCastException
    }
  }

  val sensorManager = getSensorManager()

  val sensor = sensorManager.getDefaultSensor(sensorType)
  sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

  def onSensorChanged(event: SensorEvent) {
    Log.i("AlarmSandboxReceiver", "===> Accelerator value received!" + event.values(0) + " <===")
    sensorManager.unregisterListener(this)
  }

  def onAccuracyChanged(sensor: Sensor, accuracy: Int) = {}
}


