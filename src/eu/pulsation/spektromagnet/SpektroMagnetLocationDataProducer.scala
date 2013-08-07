package eu.pulsation.spektromagnet

import org.codehaus.jackson.node.ObjectNode
import android.location.Location

trait SpektroMagnetLocationDataProducer extends SpektroMagnetDataProducer {

  var locationData : Location = null

  override def getDocument() : ObjectNode = {
    val document = super.getDocument()

    document.put("sensorType", "GPS")
    document.put("latitude", locationData.getLatitude())
    document.put("longitude", locationData.getLongitude())
    if (locationData.hasAltitude()) {
      document.put("altitude", locationData.getAltitude())
    }
    if (locationData.hasSpeed()) {
      document.put("speed", locationData.getSpeed())
    }

    document
  }

  /**
   * Insert location data in the database.
   */
  def insertLocationData(location: Location) {
    locationData = location
    insertDocument()

  }

}

