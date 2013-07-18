package eu.pulsation.alarmsandbox

import java.text.SimpleDateFormat
import java.util.{UUID, Date}

import org.codehaus.jackson.node.ObjectNode
import org.codehaus.jackson.node.JsonNodeFactory

import com.couchbase.cblite.{CBLServer, CBLDatabase}
import com.couchbase.cblite.ektorp.CBLiteHttpClient

import org.ektorp.CouchDbInstance 
import org.ektorp.http.HttpClient
import org.ektorp.impl.StdCouchDbInstance

import android.hardware.SensorEvent
import android.content.Context

import android.util.Log

trait AlarmSandboxDataProducer {

  // To be overriden
  val context : Context = null

  lazy val dbConnector = {
    val filesDir:String = context.getFilesDir().getAbsolutePath()
    val server:CBLServer = new CBLServer(filesDir) 
    val httpClient:HttpClient = new CBLiteHttpClient(server)
    val dbInstance:CouchDbInstance = new StdCouchDbInstance(httpClient)
    dbInstance.createConnector("AlarmSandbox", true)
  }
  
  // Proceed to data insertion.
//  def insertData() 
}

import android.location.Location

trait AlarmSandboxLocationDataProducer extends AlarmSandboxDataProducer {
  def insertData(locationData: Location) {

  val item:ObjectNode = JsonNodeFactory.instance.objectNode()

    val dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
    val uuid = UUID.randomUUID()

    val now = dateFormatter.format(new Date())

    item.put("_id", now + '-' + uuid)
    item.put("sensor", "GPS")
    item.put("latitude", locationData.getLatitude())
    item.put("longitude", locationData.getLongitude())
    item.put("altitude", locationData.getAltitude())
    item.put("speed", locationData.getSpeed())
    item.put("timestamp", now)

    // FIXME: ugly untrapped exception
    // clue: https://github.com/couchbaselabs/TouchDB-Android/issues/100
    dbConnector.create(item)
  }

}

trait AlarmSandboxSensorDataProducer extends AlarmSandboxDataProducer {
  def insertData(sensorData: SensorEvent) {
  }
}

