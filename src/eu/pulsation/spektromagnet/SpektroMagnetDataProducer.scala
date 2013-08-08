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
