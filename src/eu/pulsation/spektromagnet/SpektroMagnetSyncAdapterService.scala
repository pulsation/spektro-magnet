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

/**
 * Implemented from example http://www.c99.org/2010/01/23/writing-an-android-sync-provider-part-2/
 */

import android.content.Context
//import android.content.res.Resources
import android.app.Service
import android.os.{Bundle, IBinder}
import android.content.{ContentProviderClient, AbstractThreadedSyncAdapter, SyncResult, Intent}
import android.accounts.{Account, AccountManager}

import android.util.Log

import org.ektorp.{ReplicationCommand, ReplicationStatus, CouchDbConnector, CouchDbInstance}
import org.ektorp.http.HttpClient
import org.ektorp.impl.StdCouchDbInstance

import com.couchbase.cblite.router.CBLURLStreamHandlerFactory
import com.couchbase.cblite.ektorp.CBLiteHttpClient
import com.couchbase.cblite.{CBLServer}

class SpektroMagnetSyncAdapterService extends Service { self =>

  def onBind(intent: Intent) : IBinder = {

    // Install the cblite URL handler, c.f. https://github.com/couchbase/couchbase-lite-android/wiki/FAQ-Android#q-why-do-i-see-a-message-like-javanetmalformedurlexception-unknown-protocol-cblite
    CBLURLStreamHandlerFactory.registerSelfIgnoreError()

    lazy val syncAdapter = { 
      new AbstractThreadedSyncAdapter(this, true) {
        def onPerformSync(account: Account, extras: Bundle, authority: String, provider: ContentProviderClient, syncResult: SyncResult) {
          self.performSync(self, account, extras, authority, provider, syncResult)
        }
      }
    }
    syncAdapter.getSyncAdapterBinder()
  }

  private def performSync(context: Context, account: Account, extras: Bundle, authority: String, provider: ContentProviderClient,
    syncResult: SyncResult) {

    lazy val accountManager : AccountManager = {
      this.getSystemService(Context.ACCOUNT_SERVICE) match {
        case am : AccountManager => am
        case _ => throw new ClassCastException
      }
    }

    lazy val login = accountManager.getUserData(account, "login")
    lazy val password = accountManager.getPassword(account)
    lazy val server = accountManager.getUserData(account, "server")
    lazy val database = accountManager.getUserData(account, "database")

    def doReplicate() : ReplicationStatus = {

      // Connection to database
      lazy val filesDir : String = context.getFilesDir().getAbsolutePath()
      lazy val tdserver : CBLServer = new CBLServer(filesDir)
      lazy val httpClient : HttpClient = new CBLiteHttpClient(tdserver)
      lazy val serverInstance : CouchDbInstance = new StdCouchDbInstance(httpClient)

      val pushCommand  : ReplicationCommand= new ReplicationCommand.Builder()
          .source(context.getString(R.string.local_db_name))
          .target(  context.getString(R.string.remote_db_protocol) + "://" 
                    + login + ":" + password 
                    + "@" + server + ":" + context.getString(R.string.remote_db_port) 
                    + "/" + database)
          .continuous(false)
          .build();

      serverInstance.replicate(pushCommand);
      // TODO: Remove replicated data
    }

    doReplicate()
  }
}

