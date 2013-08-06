package eu.pulsation.alarmsandbox

import android.content.{BroadcastReceiver, Context, Intent}
import android.hardware.Sensor
import android.util.Log
import android.accounts

import org.ektorp.{ReplicationCommand, ReplicationStatus, CouchDbConnector, CouchDbInstance}
import org.ektorp.http.HttpClient
import org.ektorp.impl.StdCouchDbInstance

import com.couchbase.cblite.ektorp.CBLiteHttpClient
import com.couchbase.cblite.{CBLServer}

/**
 * The alarm receiver is triggered when a scheduled alarm is fired. This class
 * reads the information in the intent and displays this information in the
 * Android notification bar. The notification uses the default notification
 * sound and it vibrates the phone.
 * 
 */
class AlarmSandboxReceiver extends BroadcastReceiver // with SensorEventListener
{

  override def onReceive(context: Context, intent: Intent) {
    new AlarmSandboxLocator(context)
    new AlarmSandboxSensors(context)
  }

}
