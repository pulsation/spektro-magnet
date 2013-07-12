package eu.pulsation.alarmsandbox

import android.hardware.{SensorEventListener, SensorManager, SensorEvent, Sensor}
import android.content.{Context}
import android.util.Log
import scala.collection.JavaConversions._

class AlarmSandboxSensors(context: Context) extends SensorEventListener {

  def getSensorManager() = {
    context.getSystemService(Context.SENSOR_SERVICE) match {
      case sm: SensorManager => sm
      case _ => throw new ClassCastException
    }
  }

  val sensorManager = getSensorManager()
  val allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL).toSet

  def subscribeSensor(s: Sensor) = {
    Log.i("AlarmSandboxSensor", "===> Available sensor: " + s.getType() + "(" + s.getName() + ") <===")
    val sensor = sensorManager.getDefaultSensor(s.getType())
    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
  }

  allSensors.foreach( s => subscribeSensor (s))


  def onSensorChanged(event: SensorEvent) {
    Log.i("AlarmSandboxReceiver", "===> " + event.sensor.getName() + " value received!" + event.values(0) + " <===")
    sensorManager.unregisterListener(this)
  }

  def onAccuracyChanged(sensor: Sensor, accuracy: Int) = {}
}


