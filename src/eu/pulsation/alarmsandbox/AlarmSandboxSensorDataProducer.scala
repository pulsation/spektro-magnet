package eu.pulsation.alarmsandbox

import android.hardware.{SensorEvent, Sensor}

import org.codehaus.jackson.node.ObjectNode
import scala.collection.immutable.HashMap

trait AlarmSandboxSensorDataProducer extends AlarmSandboxDataProducer {

  var sensorData : SensorEvent = null

  val sensorTypes = HashMap(
    Sensor.TYPE_ACCELEROMETER -> "Accelerometer",
    Sensor.TYPE_AMBIENT_TEMPERATURE -> "AmbientTemperature",
    Sensor.TYPE_GRAVITY -> "Gravity",
    Sensor.TYPE_GYROSCOPE -> "Gyroscope",
    Sensor.TYPE_LIGHT -> "Light",
    Sensor.TYPE_LINEAR_ACCELERATION -> "LinearAcceleration",
    Sensor.TYPE_MAGNETIC_FIELD -> "MagneticField",
    Sensor.TYPE_ORIENTATION -> "Orientation",
    Sensor.TYPE_PRESSURE -> "Pressure",
    Sensor.TYPE_PROXIMITY -> "Proximity",
    Sensor.TYPE_RELATIVE_HUMIDITY -> "RelativeHumidity",
    Sensor.TYPE_ROTATION_VECTOR -> "RotationVector",
    Sensor.TYPE_TEMPERATURE -> "Temperature"
  )

  override def getDocument() : ObjectNode = {
    val document = super.getDocument()
    val values = sensorData.values.toSet

    def putXyz(v : Set[Float]) = {
      document.put("x", v(1))
      document.put("y", v(2))
      document.put("z", v(3))
    }

    document.put("sensorType", sensorTypes(sensorData.sensor.getType()))
    document.put("sensorName", sensorData.sensor.getName())

    // TODO: Use an ArrayNode instance to store values
    values.zipWithIndex.foreach{ case (i, v) => document.put("value" + i, v)}

    document
  }

  def insertSensorData(sensor: SensorEvent) {
    sensorData = sensor
    insertDocument()
  }
}


