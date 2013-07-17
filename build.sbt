import android.Keys._                                                                                                                                                                                              

android.Plugin.androidBuild

logLevel := Level.Debug

name := "AlarmSandbox"

// proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize", "-ignorewarnings")
proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize", "-dontwarn com.couchbase.cblite.**",
  "-dontwarn org.codehaus.jackson.map.ext.**")

resolvers += "Couchbase" at "http://maven.hq.couchbase.com/nexus/content/repositories/releases/"

libraryDependencies ++= Seq(
    "com.android.support" % "support-v4" % "13.0.0",
    "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.2",
    "org.codehaus.jackson" % "jackson-core-asl" % "1.9.2",
    "com.couchbase.cblite" % "CBLite" % "0.7",
    "com.couchbase.cblite" % "CBLiteEktorp" % "0.7.2"
 )
