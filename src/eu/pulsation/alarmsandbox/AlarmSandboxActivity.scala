package eu.pulsation.alarmsandbox
// TODO: test http://stackoverflow.com/questions/4459058/alarm-manager-example
import android.app.Activity
import android.os.Bundle

class AlarmSandboxActivity extends Activity
{
    /** Called when the activity is first created. */
    override def onCreate(savedInstanceState: Bundle)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
    }
}

