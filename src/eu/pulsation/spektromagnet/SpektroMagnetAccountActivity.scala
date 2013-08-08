package eu.pulsation.spektromagnet

import android.accounts.{AccountAuthenticatorActivity, AccountManager, Account}
import android.os.Bundle
import android.app.Activity
import android.widget.{Button, EditText, TextView}
import android.view.View.OnClickListener
import android.view.View
import android.text.method.LinkMovementMethod

import android.content.ContentResolver

class SpektroMagnetAccountActivity extends AccountAuthenticatorActivity { self =>
  val AccountType : String = "eu.pulsation.spektromagnet.couchdb"

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

    lazy val database = { 
      this.findViewById(R.id.database_field) match {
        case txt : EditText => txt.getText().toString()
        case _ => throw new ClassCastException
      }
    }

    lazy val server = { 
      this.findViewById(R.id.server_field) match {
        case txt : EditText => txt.getText().toString()
        case _ => throw new ClassCastException
      }
    }

    lazy val accntCommentTxt : TextView = {
      this.findViewById(R.id.accnt_comment_txt) match {
        case txt: TextView => txt
        case _ => throw new ClassCastException
      }
    } 

    super.onCreate(savedInstanceState)
    setContentView(R.layout.account)

    // Activate href link (c.f. http://stackoverflow.com/questions/2734270/how-do-i-make-links-in-a-textview-clickable/2746708#2746708 )
    accntCommentTxt.setMovementMethod(LinkMovementMethod.getInstance())

    // Bind buttons
    proceedBtn.setOnClickListener(new OnClickListener() {
      def onClick(v: View) {
        val account : Account = new Account(login, AccountType)
        val accountInfo : Bundle = new Bundle()

        accountInfo.putCharSequence("login", login)
        accountInfo.putCharSequence("server", server)
        accountInfo.putCharSequence("database", database)
        
        val extras : Bundle = new Bundle()
        ContentResolver.addPeriodicSync(account, "eu.pulsation.spektromagnet.content", extras, 120)
        ContentResolver.setIsSyncable(account, "eu.pulsation.spektromagnet.content", 1)
        ContentResolver.setSyncAutomatically(account, "eu.pulsation.spektromagnet.content", true)
      
        AccountManager.get(self).addAccountExplicitly(account, password, accountInfo)
        self.finish()
      }
    })
  }
}
