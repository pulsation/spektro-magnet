package eu.pulsation.spektromagnet

import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri
import android.database.Cursor

class SpektroMagnetContentProvider extends ContentProvider {

    def delete(uri: Uri, selection: String, selectionArgs: Array[String]) : Int = {
      0
    }

    def getType(uri: Uri) : String = {
      null
    }

    def insert(uri : Uri, values: ContentValues) : Uri = {
        null
    }

    def onCreate : Boolean = {
        false
    }

     def query(uri: Uri, projection: Array[String], selection: String,
               selectionArgs : Array[String] , sortOrder : String) : Cursor = {
        null
    }

    def update(uri: Uri, values: ContentValues, selection: String,
                   selectionArgs : Array[String] ) : Int = {
        0
    }
}
