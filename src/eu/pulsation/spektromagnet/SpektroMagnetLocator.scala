package eu.pulsation.spektromagnet

import android.location.{LocationManager, Location, LocationListener}
import android.util.Log
import android.content.Context
import android.os.Bundle

class SpektroMagnetLocator(override val context: Context) extends LocationListener with SpektroMagnetLocationDataProducer {

  def getLocManager() = {
    context.getSystemService(Context.LOCATION_SERVICE) match {
      case lm: LocationManager => lm
      case _ => throw new ClassCastException
    }
  }

  def onLocationChanged (location: Location) = {
    // We don't care if the date is not accurate.
    // val date = new Date()

    Log.i("SpektroMagnetLocator", "===> Got location update <===")
    this.insertLocationData(location)
  }

  def onProviderDisabled (provider: String) = {}

  def onProviderEnabled (provider: String) = {}
  
  def onStatusChanged (provider: String, status: Int, extras: Bundle)  = {}

  val locManager:LocationManager = getLocManager()
  locManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null)
  //locManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null)
}
