package eu.pulsation.alarmsandbox

import android.accounts.{AccountAuthenticatorActivity, AccountManager}
import android.os.Bundle
import android.app.Activity
import android.widget.{Button, EditText}
import android.view.View.OnClickListener
import android.view.View
import android.accounts.Account

class AuthenticatorAccountActivity extends AccountAuthenticatorActivity {
  val AccountType : String = "eu.pulsation.alarmsandbox.couchdb"
  val that = this

  override def onCreate(savedInstanceState: Bundle) {

    lazy val proceedBtn = {
      this.findViewById(R.id.ProceedBtn) match {
        case btn : Button => btn
        case _ => throw new ClassCastException
      }
    }

    lazy val loginField = {
      this.findViewById(R.id.LoginField) match {
          case txt : EditText => txt
          case _ => throw new ClassCastException
      }
    }

    lazy val passwordField = {
      this.findViewById(R.id.PasswordField) match {
          case txt : EditText => txt
          case _ => throw new ClassCastException
      }
    }

    lazy val login = loginField.getText().toString()
    lazy val password = passwordField.getText().toString()

    val that = this

    super.onCreate(savedInstanceState)
    setContentView(R.layout.account)

    // Bind buttons
    proceedBtn.setOnClickListener(new OnClickListener() {
      def onClick(v: View) {
        val account : Account = new Account(login, AccountType)

        AccountManager.get(that).addAccountExplicitly(account, password, null)
        that.finish()
      }
    })
  }
}
