package eu.pulsation.alarmsandbox

/*
import org.codehaus.jackson.JsonNodeFactory
import org.codehaus.jackson.ObjectNode
*/

// import org.codehaus.jackson.map.ObjectMapper
import com.couchbase.cblite.{CBLServer, CBLDatabase}
import com.couchbase.cblite.ektorp.CBLiteHttpClient

import org.ektorp.CouchDbInstance 
import org.ektorp.http.HttpClient
import org.ektorp.impl.StdCouchDbInstance


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

  def insertLocation(/*location: Location*/) {
/*
 * val item:ObjectNode = JsonNodeFactory.instance.objectNode()

    item.put("_id", date)
    item.put("sensor", sensor)
    item.put("val1", val1)
    item.put("val2", val2)
    */
  }

  def insertSensorData(/*sensorData: SensorEvent*/) {
  }

  /*
  */
}
