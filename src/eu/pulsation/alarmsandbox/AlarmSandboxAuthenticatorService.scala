package eu.pulsation.alarmsandbox

import android.os.IBinder
import android.accounts.AccountManager
import android.app.Service
import android.content.Intent

/**
 * Authenticator service that returns a subclass of AbstractAccountAuthenticator in onBind()
 */
class AlarmSandboxAuthenticatorService extends Service {

  def onBind(intent: Intent) : IBinder = {
    if (intent.getAction().equals(android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT)) {
      (new AlarmSandboxAuthenticator(this)).getIBinder()
    } else {
      null
    }
  }
}
