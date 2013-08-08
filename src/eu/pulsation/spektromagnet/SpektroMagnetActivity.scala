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

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.{Button, TextView}
import android.view.View
import android.view.View.OnClickListener
import android.accounts.AccountManager

import com.couchbase.cblite.router.CBLURLStreamHandlerFactory

class SpektroMagnetActivity extends Activity { self =>
  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle) {

    lazy val spektroMagnetHelper = {
      new SpektroMagnetHelper(this)
    }

    lazy val statusTxt : TextView = {
      this.findViewById(R.id.status_txt) match {
        case txt: TextView => txt
        case _ => throw new ClassCastException
      }
    } 

    lazy val startServiceBtn = {
      this.findViewById(R.id.start_service_btn)
    }

    lazy val stopServiceBtn = {
      this.findViewById(R.id.stop_service_btn)
    }

    lazy val addAccountBtn = {
      this.findViewById(R.id.add_account_btn)
    }

    super.onCreate(savedInstanceState)

    // c.f. https://github.com/couchbase/couchbase-lite-android/wiki/FAQ-Android#q-why-do-i-see-a-message-like-javanetmalformedurlexception-unknown-protocol-cblite
    CBLURLStreamHandlerFactory.registerSelfIgnoreError()

    setContentView(R.layout.main)

    // Bind buttons
    startServiceBtn.setOnClickListener(new OnClickListener() {
      def onClick(v : View) {
        spektroMagnetHelper.startAlarm()
        statusTxt.append("\nService started.");
     }
   })

    stopServiceBtn.setOnClickListener(new OnClickListener() {
      def onClick(v : View) {
        spektroMagnetHelper.stopAlarm()
        statusTxt.append("\nService stopped.");
     }
   })

  addAccountBtn.setOnClickListener(new OnClickListener() {
      def onClick(v : View) {
        val am : AccountManager = AccountManager.get(self)
        am.addAccount(  "eu.pulsation.spektromagnet.couchdb", 
                        null,
                        null,
                        null,
                        self,
                        null,
                        null)

     }
  })

 }
}

