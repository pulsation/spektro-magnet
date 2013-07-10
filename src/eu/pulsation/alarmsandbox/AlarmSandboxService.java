// TODO: import code from http://stackoverflow.com/questions/4459058/alarm-manager-example

package eu.pulsation.alarmsandbox;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class AlarmSandboxService extends Service
{
    AlarmSandbox alarm = new AlarmSandbox();
    public void onCreate()
    {
        super.onCreate();       
    }

    public void onStart(Context context,Intent intent, int startId)
    {
        alarm.SetAlarm(context);
    }

    @Override
    public IBinder onBind(Intent intent) 
    {
        return null;
    }
    
}
