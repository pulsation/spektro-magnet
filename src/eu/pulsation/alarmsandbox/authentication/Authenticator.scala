import android.content.Context
import android.os.Bundle

import android.accounts.{AbstractAccountAuthenticator, AccountAuthenticatorResponse, Account}

/**
 * Implementation based on http://www.c99.org/2010/01/23/writing-an-android-sync-provider-part-1/
 */
class Authenticator(context : Context) extends AbstractAccountAuthenticator(context : Context) {
  
  def hasFeatures(response : AccountAuthenticatorResponse, account: Account , features: Array [String]) : Bundle = { null }

  def updateCredentials(response : AccountAuthenticatorResponse , account: Account , authTokenType: String , options: Bundle) : Bundle = { null }
  
  def confirmCredentials(response : AccountAuthenticatorResponse, account: Account, options: Bundle) : Bundle = { null }
  
  def editProperties(response : AccountAuthenticatorResponse, accountType : String) : Bundle = { null }

  def getAuthTokenLabel(authTokenType : String) : String =  { null }

  def getAuthToken(response : AccountAuthenticatorResponse, account: Account, authTokenType: String, options : Bundle) : Bundle = { null }

  /**
   * TODO: To be implemented
   */
  def addAccount(response : AccountAuthenticatorResponse, accountType : String, authTokenType : String, requiredFeatures: Array[String],
    options : Bundle) : Bundle = {
      null
  }

}
