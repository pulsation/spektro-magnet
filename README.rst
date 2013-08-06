=====================
Introspection Project
=====================

Goals
=====

This project aims at providing a working example of the combination of
scala and Couchbase Lite running on android devices. In particular, Couchbase Lite 
replication is done using a 
`SyncAdapter<https://developer.android.com/reference/android/content/AbstractThreadedSyncAdapter.html>`_ 
implementation through a dummy 
`content provider<https://developer.android.com/guide/topics/providers/content-providers.html>`_.

What it does
============
The application can start a service that collects periodically as much sensor data as possible,
and store it in a local database. It replicates data to an online database if a 
`CouchDB<https://couchdb.apache.org/>`_ (typically, an `Iris Couch<https://www.iriscouch.com/>`_ instance) 
or a `Couchbase Sync Gateway<https://github.com/couchbaselabs/sync_gateway>`_ 
account is provided.

Requirements
============

* sbt - http://www.scala-sbt.org/
* Android SDK - https://developer.android.com/sdk/index.html
* A CouchDB compatible database

Installation from source
========================

* Install the android support repository component
* In the project path, run::

  android update project -p .
  sbt android:run

Replication
===========
It is possible to configure an account on which you can replicate your data in the standard android
account settings.

TODO
====

* Possibility to delete account
* Frequency tuning
* Empty database from time to time

Bugs
====

* Broadcast leak?

