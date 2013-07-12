package eu.pulsation.alarmsandbox

import android.location.{LocationManager, Location, LocationListener}
import android.util.Log
import java.util.Date
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
    // We don't care if the date is not accurate.
    val date = new Date()

    Log.i("AlarmSandboxLocator", "===> Got location update at " + date + " <===")
    Log.i("AlarmSandboxLocator", "===> Latitude: " + location.getLatitude()+ " <===")
    Log.i("AlarmSandboxLocator", "===> Longitude: " + location.getLongitude()+ " <===")
    Log.i("AlarmSandboxLocator", "===> Altitude: " + location.getAltitude()+ " <===")
    Log.i("AlarmSandboxLocator", "===> Speed: " + location.getSpeed()+ " <===")
  }

  def onProviderDisabled (provider: String) = {}

  def onProviderEnabled (provider: String) = {}
  
  def onStatusChanged (provider: String, status: Int, extras: Bundle)  = {}

  val locManager:LocationManager = getLocManager()
  locManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null)
}
