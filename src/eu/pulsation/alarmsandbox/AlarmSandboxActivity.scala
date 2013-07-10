package eu.pulsation.alarmsandbox

import android.app.{Activity, AlarmManager}
import android.os.Bundle
import android.content.Intent
import android.content.Context._
import android.util.Log

class AlarmSandboxActivity extends Activity
{
    /** Called when the activity is first created. */
    override def onCreate(savedInstanceState: Bundle)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        Log.v("AlarmSandbox", "=> Activity created")
//        val test = new AlarmSandboxService_Service()

// Create an IntentSender that will launch our service, to be scheduled
    // with the alarm manager.
    val intent = new Intent(AlarmService.this, classOf[AlarmService_Service])
    mAlarmSender = PendingIntent.getService(AlarmService.this, 0, intent, 0)

      // Schedule the alarm!
      val am = getSystemService(ALARM_SERVICE).asInstanceOf[AlarmManager]
      am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 30*1000, mAlarmSender)

    }
}

