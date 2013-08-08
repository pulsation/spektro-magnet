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

import android.accounts.{AccountAuthenticatorActivity, AccountManager, Account}
import android.os.Bundle
import android.app.Activity
import android.widget.{Button, EditText, TextView}
import android.view.View.OnClickListener
import android.view.View
import android.text.method.LinkMovementMethod

import android.content.ContentResolver

class SpektroMagnetAccountActivity extends AccountAuthenticatorActivity { self =>
  val AccountType : String = "eu.pulsation.spektromagnet.couchdb"

  override def onCreate(savedInstanceState: Bundle) {

    lazy val proceedBtn = {
      this.findViewById(R.id.proceed_btn) match {
        case btn : Button => btn
        case _ => throw new ClassCastException
      }
    }

    lazy val password = {
      this.findViewById(R.id.password_field) match {
        case txt : EditText => txt.getText().toString()
        case _ => throw new ClassCastException
      }
    }

    lazy val login = { 
      this.findViewById(R.id.login_field) match {
        case txt : EditText => txt.getText().toString()
        case _ => throw new ClassCastException
      }
    }

    lazy val database = { 
      this.findViewById(R.id.database_field) match {
        case txt : EditText => txt.getText().toString()
        case _ => throw new ClassCastException
      }
    }

    lazy val server = { 
      this.findViewById(R.id.server_field) match {
        case txt : EditText => txt.getText().toString()
        case _ => throw new ClassCastException
      }
    }

    lazy val accntCommentTxt : TextView = {
      this.findViewById(R.id.accnt_comment_txt) match {
        case txt: TextView => txt
        case _ => throw new ClassCastException
      }
    } 

    super.onCreate(savedInstanceState)
    setContentView(R.layout.account)

    // Activate href link (c.f. http://stackoverflow.com/questions/2734270/how-do-i-make-links-in-a-textview-clickable/2746708#2746708 )
    accntCommentTxt.setMovementMethod(LinkMovementMethod.getInstance())

    // Bind buttons
    proceedBtn.setOnClickListener(new OnClickListener() {
      def onClick(v: View) {
        val account : Account = new Account(login, AccountType)
        val accountInfo : Bundle = new Bundle()

        accountInfo.putCharSequence("login", login)
        accountInfo.putCharSequence("server", server)
        accountInfo.putCharSequence("database", database)
        
        val extras : Bundle = new Bundle()
        ContentResolver.addPeriodicSync(account, "eu.pulsation.spektromagnet.content", extras, 120)
        ContentResolver.setIsSyncable(account, "eu.pulsation.spektromagnet.content", 1)
        ContentResolver.setSyncAutomatically(account, "eu.pulsation.spektromagnet.content", true)
      
        AccountManager.get(self).addAccountExplicitly(account, password, accountInfo)
        self.finish()
      }
    })
  }
}
