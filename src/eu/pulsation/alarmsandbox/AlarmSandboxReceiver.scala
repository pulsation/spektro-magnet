package eu.pulsation.alarmsandbox

import android.content.{BroadcastReceiver, Context, Intent}
import android.hardware.Sensor
import android.util.Log

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

    /**
     * Trigger replication to online database 
     */
    def triggerReplication() : ReplicationStatus = {

      Log.v("AlarmSandboxReceiver", "===> Triggering replication <===")

      val filesDir : String = context.getFilesDir().getAbsolutePath();
      val tdserver : CBLServer = new CBLServer(filesDir);

      val httpClient : HttpClient = new CBLiteHttpClient(tdserver);
      val server : CouchDbInstance = new StdCouchDbInstance(httpClient);

      // create a local database
      val db : CouchDbConnector = server.createConnector("alarmsandbox", true);

      val pushCommand  : ReplicationCommand= new ReplicationCommand.Builder()
          .source("alarmsandbox")
          .target("https://alarmsandbox:WhyejBild0@pulsation.iriscouch.com:6984" + "/alarmsandbox")
          .continuous(false)
          .build();

      server.replicate(pushCommand);
    
    }

    Log.v("AlarmSandboxReceiver", "===> Replication status: " + triggerReplication() + " <===")
    /* TODO: Empty local database from time to time */
  }

}
