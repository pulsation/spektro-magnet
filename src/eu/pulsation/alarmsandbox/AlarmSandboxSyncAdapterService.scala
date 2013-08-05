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

class AlarmSandboxSyncAdapterService extends Service { self =>

  def onBind(intent: Intent) : IBinder = {
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

    Log.i("AlarmSandboxSyncAdapterService", "Synchronization stub.")
    Log.i("AlarmSandboxSyncAdapterService", accountManager.getPassword(account))
    Log.i("AlarmSandboxSyncAdapterService", accountManager.getUserData(account, "server"))
    Log.i("AlarmSandboxSyncAdapterService", accountManager.getUserData(account, "login"))
  }
}

