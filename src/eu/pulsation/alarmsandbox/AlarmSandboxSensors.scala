package eu.pulsation.alarmsandbox

import android.hardware.{SensorEventListener, SensorManager, SensorEvent, Sensor}
import android.content.{Context}
import android.util.Log
import java.util.Date

import scala.collection.JavaConversions._

class AlarmSandboxSensors(override val context: Context) extends SensorEventListener with AlarmSandboxSensorDataProducer {

  def getSensorManager() = {
    context.getSystemService(Context.SENSOR_SERVICE) match {
      case sm: SensorManager => sm
      case _ => throw new ClassCastException
    }
  }

  val sensorManager = getSensorManager()
  val allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL).toSet

  def subscribeSensor(s: Sensor) = {
    Log.i("AlarmSandboxSensors", "===> Available sensor: " + s.getType() + "(" + s.getName() + ") <===")
    val sensor = sensorManager.getDefaultSensor(s.getType())
    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
  }

  allSensors.foreach( s => subscribeSensor (s))


  def onSensorChanged(event: SensorEvent) {
    // We don't care if the date is not accurate.
/*    
    val date = new Date()
    val values = event.values.toSet
    Log.i("AlarmSandboxSensors", "===> " + event.sensor.getName() + " values received at " + date + " ! <===")
    values.foreach(v => Log.i("AlarmSandboxSensors", "===> " + event.sensor.getName() + ": " + v + " <==="))*/
    this.insertSensorData(event)
    sensorManager.unregisterListener(this)
  }

  def onAccuracyChanged(sensor: Sensor, accuracy: Int) = {}
}


