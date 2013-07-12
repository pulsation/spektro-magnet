package eu.pulsation.alarmsandbox

import android.location.{LocationManager, Location, LocationListener}
import android.util.Log
import android.content.Context
import android.os.Bundle

class AlarmSandboxLocator(context: Context) extends LocationListener {

  def getLocManager() = {
    context.getSystemService(Context.LOCATION_SERVICE) match {
      case lm: LocationManager => lm
      case _ => throw new ClassCastException
    }
  }

  def onLocationChanged (location: Location) = {
    Log.i("AlarmSandboxReceiver", "===> Got location update! " + location + " <===")
  }

  def onProviderDisabled (provider: String) = {}

  def onProviderEnabled (provider: String) = {}
  
  def onStatusChanged (provider: String, status: Int, extras: Bundle)  = {}

  val locManager:LocationManager = getLocManager()
  locManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null)
}
