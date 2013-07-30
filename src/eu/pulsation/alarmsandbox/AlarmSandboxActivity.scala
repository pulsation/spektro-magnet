package eu.pulsation.alarmsandbox

// TODO: test http://stackoverflow.com/questions/4459058/alarm-manager-example

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.{Button, TextView}
import android.view.View
import android.view.View.OnClickListener

import com.couchbase.cblite.router.CBLURLStreamHandlerFactory

class AlarmSandboxActivity extends Activity {
  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle) {

    lazy val alarmSandboxHelper = {
      new AlarmSandboxHelper(this)
    }

    lazy val statusTxt : TextView = {
      this.findViewById(R.id.StatusTxt) match {
        case txt: TextView => txt
        case _ => throw new ClassCastException
      }
    } 

    lazy val startServiceBtn = {
      this.findViewById(R.id.StartServiceBtn)
    }

    lazy val stopServiceBtn = {
      this.findViewById(R.id.StopServiceBtn)
    }
    super.onCreate(savedInstanceState)

    // c.f. https://github.com/couchbase/couchbase-lite-android/wiki/FAQ-Android#q-why-do-i-see-a-message-like-javanetmalformedurlexception-unknown-protocol-cblite
    CBLURLStreamHandlerFactory.registerSelfIgnoreError()

    setContentView(R.layout.main)

    // Bind buttons
    startServiceBtn.setOnClickListener(new OnClickListener() {
      def onClick(v : View) {
        alarmSandboxHelper.startAlarm()
        statusTxt.append("\nService started.");
     }
   })

    stopServiceBtn.setOnClickListener(new OnClickListener() {
      def onClick(v : View) {
        alarmSandboxHelper.stopAlarm()
        statusTxt.append("\nService stopped.");
     }
   })
 }
}

