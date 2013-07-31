package eu.pulsation.alarmsandbox

import android.accounts.{AccountAuthenticatorActivity, AccountManager}
import android.os.Bundle
import android.app.Activity
import android.widget.{Button, EditText}
import android.view.View.OnClickListener
import android.view.View
import android.accounts.Account

class AlarmSandboxAccountActivity extends AccountAuthenticatorActivity { self =>
  val AccountType : String = "eu.pulsation.alarmsandbox.couchdb"

  override def onCreate(savedInstanceState: Bundle) {

    lazy val proceedBtn = {
      this.findViewById(R.id.ProceedBtn) match {
        case btn : Button => btn
        case _ => throw new ClassCastException
      }
    }

    lazy val password = {
      this.findViewById(R.id.PasswordField) match {
        case txt : EditText => txt.getText().toString()
        case _ => throw new ClassCastException
      }
    }

    lazy val login = { 
      this.findViewById(R.id.LoginField) match {
        case txt : EditText => txt.getText().toString()
        case _ => throw new ClassCastException
      }
    }

    super.onCreate(savedInstanceState)
    setContentView(R.layout.account)

    // Bind buttons
    proceedBtn.setOnClickListener(new OnClickListener() {
      def onClick(v: View) {
        val account : Account = new Account(login, AccountType)
        val accountInfo : Bundle = new Bundle()

        accountInfo.putCharSequence("server", "www.pulsation.eu")
        accountInfo.putCharSequence("database", "alarmsandbox")

        AccountManager.get(self).addAccountExplicitly(account, password, accountInfo)
        self.finish()
      }
    })
  }
}
