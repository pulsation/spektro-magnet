package eu.pulsation.alarmsandbox

/**
 * Implemented from example http://www.c99.org/2010/01/23/writing-an-android-sync-provider-part-2/
 */

import android.content.Context
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

class AlarmSandboxSyncAdapterService extends Service { self =>

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
          .source(R.string.local_db_name)
//          .target("https://alarmsandbox:WhyejBild0@www.pulsation.eu:6984" + "/alarmsandbox")
          .target("https://" + login + ":" + password + "@" + server + ":6984" + "/" + database)
          .continuous(false)
          .build();

      serverInstance.replicate(pushCommand);
      // TODO: Remove replicated data
    }

    lazy val accountManager : AccountManager = {
      this.getSystemService(Context.ACCOUNT_SERVICE) match {
        case am : AccountManager => am
        case _ => throw new ClassCastException
      }
    }

    doReplicate()
  }
}

