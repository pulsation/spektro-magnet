package eu.pulsation.alarmsandbox
// TODO: test http://stackoverflow.com/questions/4459058/alarm-manager-example
import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.couchbase.cblite.router.CBLURLStreamHandlerFactory
import com.couchbase.touchdb.TDCollateJSON

class AlarmSandboxActivity extends Activity
{
  /** Called when the activity is first created. */
   override def onCreate(savedInstanceState: Bundle)
   {
     super.onCreate(savedInstanceState)

     // FIXME: This explicit code looks like mandatory for now to avoid proguard filter compareStringsUnicode out.
     TDCollateJSON.compareStringsUnicode("toto", "toto")

     // c.f. https://github.com/couchbase/couchbase-lite-android/wiki/FAQ-Android#q-why-do-i-see-a-message-like-javanetmalformedurlexception-unknown-protocol-cblite
     CBLURLStreamHandlerFactory.registerSelfIgnoreError()

     new AlarmSandboxHelper(this).addAlarm()
     setContentView(R.layout.main)
   }
}

