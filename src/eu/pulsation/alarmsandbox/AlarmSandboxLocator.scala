package eu.pulsation.alarmsandbox

import android.location.{LocationManager, Location, LocationListener}
import android.util.Log
import android.content.Context
import android.os.Bundle

class AlarmSandboxLocator(override val context: Context) extends LocationListener with AlarmSandboxLocationDataProducer {

  def getLocManager() = {
    context.getSystemService(Context.LOCATION_SERVICE) match {
      case lm: LocationManager => lm
      case _ => throw new ClassCastException
    }
  }

  def onLocationChanged (location: Location) = {
    // We don't care if the date is not accurate.
    // val date = new Date()

    Log.i("AlarmSandboxLocator", "===> Got location update <===")
    this.insertData(location)
  }

  def onProviderDisabled (provider: String) = {}

  def onProviderEnabled (provider: String) = {}
  
  def onStatusChanged (provider: String, status: Int, extras: Bundle)  = {}

  val locManager:LocationManager = getLocManager()
  locManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null)
}
