package eu.pulsation.spektromagnet

import android.hardware.{SensorEventListener, SensorManager, SensorEvent, Sensor}
import android.content.Context

import scala.collection.JavaConversions._

class SpektroMagnetSensors(override val context: Context) extends SensorEventListener with SpektroMagnetSensorDataProducer {

  lazy val sensorManager : SensorManager = {
    context.getSystemService(Context.SENSOR_SERVICE) match {
      case sm: SensorManager => sm
      case _ => throw new ClassCastException
    }
  }

  val allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL).toSet

  def subscribeSensor(s: Sensor) : Boolean = {
    val sensor = sensorManager.getDefaultSensor(s.getType())
    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
  }

  allSensors.foreach( s => subscribeSensor (s))

  def onSensorChanged(event: SensorEvent) {
    sensorManager.unregisterListener(this)
    this.insertSensorData(event)
  }

  def onAccuracyChanged(sensor: Sensor, accuracy: Int) = {}
}


