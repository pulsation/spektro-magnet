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
    Log.i("AlarmSandboxDataProducer", "===> filesDir: " + filesDir + " <===")
    new CBLServer(filesDir) 
  }

  lazy val database : CBLDatabase = {
    Log.i("AlarmSandboxDataProducer", "Before database")
    server.getDatabaseNamed("alarmsandbox", true)
  }

  lazy val httpClient : HttpClient = {
    Log.i("AlarmSandboxDataProducer", "Before httpclient")
    new CBLiteHttpClient(server)  
  }

  lazy val dbInstance : CouchDbInstance = {
/*    
    database.open()
    database.close()
*/
    Log.i("AlarmSandboxDataProducer", "Before dbInstance")
    new StdCouchDbInstance(httpClient)
  }

  lazy val dbConnector = {
    Log.i("AlarmSandboxProducer", "===> Before createConnector <===")
    var inst = dbInstance
    Log.i("AlarmSandboxProducer", "dbInstance fetched")
    val cn = inst.createConnector("alarmsandbox", true)
    Log.i("AlarmSandboxProducer", "===> After createConnector <===")
    cn
  }
}


import android.location.Location

trait AlarmSandboxLocationDataProducer extends AlarmSandboxDataProducer {

  /**
   * Insert location data in the database.
   */
  def insertData(locationData: Location) {

    Log.i("AlarmSandboxDataProducer", "===> About to insert location data <===");

    val item:ObjectNode = JsonNodeFactory.instance.objectNode()

    val dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
    val uuid = UUID.randomUUID()

    val now = dateFormatter.format(new Date())

    Log.i("AlarmSandboxDataProducer", "===> Entered onSuccess!!! <===");
    item.put("_id", now + '-' + uuid)
    item.put("sensor", "GPS")
    item.put("latitude", locationData.getLatitude())
    item.put("longitude", locationData.getLongitude())
    item.put("altitude", locationData.getAltitude())
    item.put("speed", locationData.getSpeed())
    item.put("timestamp", now)

    dbConnector.create(item)

  }

}

trait AlarmSandboxSensorDataProducer extends AlarmSandboxDataProducer {
  def insertData(sensorData: SensorEvent) {
  }
}

