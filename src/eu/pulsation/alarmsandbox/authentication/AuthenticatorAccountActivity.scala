package eu.pulsation.alarmsandbox

import android.accounts.AccountAuthenticatorActivity
import android.os.Bundle
import android.app.Activity

class AuthenticatorAccountActivity extends AccountAuthenticatorActivity {
  val AccountType : String = "eu.pulsation.alarmsandbox.couchdb"

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.account)
  }

}
