/* 
 * Copyright (c) 2013, Philippe Sam-Long aka pulsation
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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


