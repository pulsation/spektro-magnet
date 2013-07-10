package eu.pulsation.alarmsandbox

import android.widget.Toast
import android.app.Service
import android.os.{Binder, IBinder, Parcel, RemoteException}
import android.content.Intent
import android.util.Log

class AlarmSandboxService extends Service {

  override def onCreate() {
    Log.v("AlarmSandbox", "=> Service created")
    Toast.makeText(this, "Service created",
                   Toast.LENGTH_SHORT).show()
  }

  override def onBind(intent: Intent): IBinder = {
    mBinder
  }

  /**
   * This is the object that receives interactions from clients.
   * See RemoteService for a more complete example.
   */
  private final val mBinder: IBinder = new Binder {
    @throws(classOf[RemoteException])
    override protected def onTransact(code: Int, data: Parcel,
                                      reply: Parcel, flags: Int): Boolean = {
      super.onTransact(code, data, reply, flags)
    }
  }

}
