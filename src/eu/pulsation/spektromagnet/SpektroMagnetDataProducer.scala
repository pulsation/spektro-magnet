package eu.pulsation.spektromagnet

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
import android.telephony.TelephonyManager
//import android.content.res.Resources

import android.util.Log

/**
 *
 * Ability to write data in a CouchDB database.
 *
 */
trait SpektroMagnetDataProducer {

  // To be overriden
  val context : Context = null

  lazy val server : CBLServer = {
    val filesDir:String = context.getFilesDir().getAbsolutePath()
    new CBLServer(filesDir) 
  }

  lazy val httpClient : HttpClient = new CBLiteHttpClient(server)  

  lazy val dbInstance : CouchDbInstance = new StdCouchDbInstance(httpClient)

  lazy val dbConnector = dbInstance.createConnector(context.getString(R.string.local_db_name), true)

  /**
   * Returns the document to be inserted in the database.
   */
  def getDocument() : ObjectNode = {

    val document:ObjectNode = JsonNodeFactory.instance.objectNode()

    val dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
    val now = dateFormatter.format(new Date())
    val uuid = UUID.randomUUID()
    lazy val tManager : TelephonyManager = {
      context.getSystemService(Context.TELEPHONY_SERVICE) match {
        case tm : TelephonyManager => tm
        case _ => throw new ClassCastException
      }
    }
    val phoneId = tManager.getDeviceId()

    document.put("_id", now + '-' + uuid)
    document.put("timestamp", now)
    document.put("phoneId", phoneId)
    document.put("phoneModel", android.os.Build.MODEL)
   
    document
  }

  /**
   * Inserts document in the local database.
   */
  def insertDocument() = {
    dbConnector.create(getDocument())
  }
}
