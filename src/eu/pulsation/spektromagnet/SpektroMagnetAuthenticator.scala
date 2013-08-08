package eu.pulsation.spektromagnet

import android.content.{Context, Intent}
import android.os.Bundle

import android.accounts.{AbstractAccountAuthenticator, AccountAuthenticatorResponse, Account, AccountManager}

import android.util.Log

/**
 * Implementation based on http://www.c99.org/2010/01/23/writing-an-android-sync-provider-part-1/
 */
class SpektroMagnetAuthenticator (context : Context) extends AbstractAccountAuthenticator (context: Context) {

  def hasFeatures(response : AccountAuthenticatorResponse, account: Account , features: Array [String]) : Bundle = { null }

  def updateCredentials(response : AccountAuthenticatorResponse , account: Account , authTokenType: String , options: Bundle) : Bundle = { null }
  
  def confirmCredentials(response : AccountAuthenticatorResponse, account: Account, options: Bundle) : Bundle = { null }
  
  def editProperties(response : AccountAuthenticatorResponse, accountType : String) : Bundle = { null }

  def getAuthTokenLabel(authTokenType : String) : String =  { null }

  def getAuthToken(response : AccountAuthenticatorResponse, account: Account, authTokenType: String, options : Bundle) : Bundle = { null }

  def addAccount(response : AccountAuthenticatorResponse, accountType : String, authTokenType : String, requiredFeatures: Array[String],
    options : Bundle) : Bundle = {
      val bundle: Bundle = new Bundle()
      val intent : Intent = new Intent(context, classOf[SpektroMagnetAccountActivity])

      Log.i("SpektroMagnetAuthenticator", "Entered addAccount()")

      intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
      bundle.putParcelable(AccountManager.KEY_INTENT, intent)
      bundle
  }
}
