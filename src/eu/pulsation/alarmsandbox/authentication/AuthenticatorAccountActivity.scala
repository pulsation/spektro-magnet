package eu.pulsation.alarmsandbox

import android.accounts.AccountAuthenticatorActivity
import android.os.Bundle
import android.app.Activity
import android.widget.Button
import android.view.View.OnClickListener
import android.view.View

class AuthenticatorAccountActivity extends AccountAuthenticatorActivity {
  val AccountType : String = "eu.pulsation.alarmsandbox.couchdb"

  override def onCreate(savedInstanceState: Bundle) {

    lazy val proceedBtn = {
      this.findViewById(R.id.ProceedBtn) match {
        case btn : Button => btn
        case _ => throw new ClassCastException
      }
    }

    super.onCreate(savedInstanceState)
    setContentView(R.layout.account)

    // Bind buttons
    proceedBtn.setOnClickListener(new OnClickListener() {
      def onClick(v: View) {
        // TODO
      }
    })
  }

}
