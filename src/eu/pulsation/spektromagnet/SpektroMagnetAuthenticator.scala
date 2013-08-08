/* 
 * Copyright (c) 2013, Philippe Sam-Long aka pulsation
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
