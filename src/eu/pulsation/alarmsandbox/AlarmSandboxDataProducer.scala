package eu.pulsation.alarmsandbox

import java.text.SimpleDateFormat
import java.util.{UUID, Date}

import org.codehaus.jackson.node.ObjectNode
import org.codehaus.jackson.node.JsonNodeFactory

import com.couchbase.cblite.{CBLServer, CBLDatabase}
import com.couchbase.cblite.ektorp.CBLiteHttpClient
import com.couchbase.cblite.router.CBLURLConnection

import org.ektorp.{CouchDbInstance, CouchDbConnector}
import org.ektorp.http.HttpClient
import org.ektorp.impl.StdCouchDbInstance
import org.ektorp.android.util.EktorpAsyncTask
import org.ektorp.DbAccessException

import android.hardware.SensorEvent
import android.content.Context

import android.util.Log

import com.couchbase.touchdb.TDCollateJSON

trait AlarmSandboxDataProducer {

  // To be overriden
  val context : Context = null

  lazy val server : CBLServer = {
    val filesDir:String = context.getFilesDir().getAbsolutePath()
    new CBLServer(filesDir) 
  }

  lazy val httpClient : HttpClient = new CBLiteHttpClient(server)  

  lazy val dbInstance : CouchDbInstance = new StdCouchDbInstance(httpClient)

  lazy val dbConnector = dbInstance.createConnector("alarmsandbox", true)


  def getDocument() : ObjectNode = {

    Log.v("AlarmSandboxDataProducer", "===> Initializing document <===")

    val document:ObjectNode = JsonNodeFactory.instance.objectNode()

    val dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
    val uuid = UUID.randomUUID()
    val now = dateFormatter.format(new Date())

    document.put("_id", now + '-' + uuid)
    document.put("timestamp", now)
   
    document
  }

  def insertDocument() = {
    dbConnector.create(getDocument())
  }
}


import android.location.Location

trait AlarmSandboxLocationDataProducer extends AlarmSandboxDataProducer {

  var locationData : Location = null

  override def getDocument() : ObjectNode = {
    val document = super.getDocument()

    document.put("sensor", "GPS")
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

trait AlarmSandboxSensorDataProducer extends AlarmSandboxDataProducer {

  override def getDocument() : ObjectNode = {
    val document = super.getDocument()

    document
  }

  var sensorData : SensorEvent = null

  def insertSensorData(sensor: SensorEvent) {
    sensorData = sensor
    insertDocument()
  }
}

