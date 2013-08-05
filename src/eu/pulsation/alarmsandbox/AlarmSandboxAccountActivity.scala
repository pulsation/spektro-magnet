package eu.pulsation.alarmsandbox

import android.accounts.{AccountAuthenticatorActivity, AccountManager, Account}
import android.os.Bundle
import android.app.Activity
import android.widget.{Button, EditText}
import android.view.View.OnClickListener
import android.view.View

import android.content.ContentResolver

class AlarmSandboxAccountActivity extends AccountAuthenticatorActivity { self =>
  val AccountType : String = "eu.pulsation.alarmsandbox.couchdb"

  override def onCreate(savedInstanceState: Bundle) {

    lazy val proceedBtn = {
      this.findViewById(R.id.proceed_btn) match {
        case btn : Button => btn
        case _ => throw new ClassCastException
      }
    }

    lazy val password = {
      this.findViewById(R.id.password_field) match {
        case txt : EditText => txt.getText().toString()
        case _ => throw new ClassCastException
      }
    }

    lazy val login = { 
      this.findViewById(R.id.login_field) match {
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
        
        val extras : Bundle = new Bundle()
        ContentResolver.addPeriodicSync(account, "eu.pulsation.alarmsandbox.content", extras, 900)
        ContentResolver.setIsSyncable(account, "eu.pulsation.alarmsandbox.content", 1)
        ContentResolver.setSyncAutomatically(account, "eu.pulsation.alarmsandbox.content", false)
      
        AccountManager.get(self).addAccountExplicitly(account, password, accountInfo)
        self.finish()
      }
    })
  }
}
