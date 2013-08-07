package eu.pulsation.spektromagnet

import android.hardware.{SensorEvent, Sensor}

import org.codehaus.jackson.node.{ObjectNode, ArrayNode}
import scala.collection.immutable.HashMap

trait SpektroMagnetSensorDataProducer extends SpektroMagnetDataProducer {

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
    val jsonValues: ArrayNode = document.putArray("values")

    // Sensor characteristics
    try {
      document.put("sensorType", sensorTypes(sensorData.sensor.getType()))
    } catch {
      case e: Exception => { 
        document.put("sensorType", "Unknown")
      }
    }
    document.put("androidType", sensorData.sensor.getType())
    document.put("sensorName", sensorData.sensor.getName())

    // Sensor values
    values.foreach{ v => jsonValues.add(v)}

    document
  }

  def insertSensorData(sensor: SensorEvent) {
    sensorData = sensor
    insertDocument()
  }
}


