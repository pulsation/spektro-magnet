package eu.pulsation.alarmsandbox

import java.text.SimpleDateFormat
import java.util.{UUID, Date}

import scala.collection.immutable.HashMap

import org.codehaus.jackson.node.{JsonNodeFactory, ObjectNode}

import com.couchbase.cblite.CBLServer
import com.couchbase.cblite.ektorp.CBLiteHttpClient
import com.couchbase.cblite.router.CBLURLConnection

import org.ektorp.{CouchDbInstance, CouchDbConnector}
import org.ektorp.http.HttpClient
import org.ektorp.impl.StdCouchDbInstance

import android.content.Context

import android.util.Log

/**
 *
 * Ability to write data in a CouchDB database.
 *
 */
trait AlarmSandboxDataProducer {

  // To be overriden
  val context : Context = null

  lazy val server : CBLServer = {
    val filesDir:String = context.getFilesDir().getAbsolutePath()
    new CBLServer(filesDir) 
  }

  lazy val httpClient : HttpClient = new CBLiteHttpClient(server)  

  lazy val dbInstance : CouchDbInstance = new StdCouchDbInstance(httpClient)

  lazy val dbConnector = dbInstance.createConnector(R.string.local_db_name, true)

  /**
   * Returns the document to be inserted in the database.
   */
  def getDocument() : ObjectNode = {

    val document:ObjectNode = JsonNodeFactory.instance.objectNode()

    val dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
    val uuid = UUID.randomUUID()
    val now = dateFormatter.format(new Date())

    document.put("_id", now + '-' + uuid)
    document.put("timestamp", now)
   
    document
  }

  /**
   * Inserts document in the local database.
   */
  def insertDocument() = {
    Log.v("AlarmSandboxDataProducer", "===> Writing data to local database <===")
    dbConnector.create(getDocument())
  }
}
