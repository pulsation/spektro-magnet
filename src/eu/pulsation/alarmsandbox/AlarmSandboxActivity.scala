package eu.pulsation.alarmsandbox
// TODO: test http://stackoverflow.com/questions/4459058/alarm-manager-example
import android.app.Activity
import android.os.Bundle
import android.util.Log

import org.codehaus.jackson.map.ObjectMapper
import com.couchbase.cblite.CBLServer

class AlarmSandboxActivity extends Activity
{
  /** Called when the activity is first created. */
   override def onCreate(savedInstanceState: Bundle)
   {
     super.onCreate(savedInstanceState)

     // test couchbase lite
     val filesDir:String = this.getFilesDir().getAbsolutePath()
//     val sblserver:CBLServer = new CBLServer(filesDir) 

     new AlarmSandboxHelper(this).addAlarm()
     setContentView(R.layout.main)
   }
}

