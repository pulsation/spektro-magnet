import android.Keys._                                                                                                                                                                                              

android.Plugin.androidBuild

// logLevel := Level.Debug

name := "SpektroMagnet"

scalacOptions ++= Seq("-deprecation", "-Xlint")

proguardOptions in Android ++= Seq(
    "-keepattributes *Annotation*,EnclosingMethod",
    "-keep public enum * { public static **[] values(); public static ** valueOf(java.lang.String); }",
    "-keepnames class com.codehaus.jackson.** { *; }",
    "-keep class org.ektorp.** { *; }",
    "-keep class com.couchbase.cblite.router.CBLRouter { *; }",
    "-keep class com.couchbase.touchdb.TDCollateJSON { *; }",
    "-keepclasseswithmembers class * { native <methods>; }",
    "-keep class eu.pulsation.spektromagnet.SpektroMagnetAuthenticator { *; }",
    "-keep class eu.pulsation.spektromagnet.SpektroMagnetSyncAdapterService { *; }",
    "-keep class eu.pulsation.spektromagnet.SpektroMagnetContentProvider { *; }"
)

proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize",
 "-dontwarn org.apache.http.**",
 "-dontwarn org.joda.time.**",
 "-dontwarn javax.servlet.**",
 "-dontwarn org.apache.log.**",
 "-dontwarn org.apache.log4j.**",
 "-dontwarn org.apache.avalon.**",
 "-dontwarn org.w3c.dom.bootstrap.**",
 "-dontnote java.util.**",
 "-dontnote org.apache.commons.logging.**",
 "-dontnote org.apache.http.**")

resolvers += "Couchbase" at "http://maven.hq.couchbase.com/nexus/content/repositories/releases/"

libraryDependencies ++= Seq(
    "com.android.support" % "support-v4" % "13.0.0",
    "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.2",
    "org.codehaus.jackson" % "jackson-core-asl" % "1.9.2",
    "com.couchbase.cblite" % "CBLite" % "0.7",
    "com.couchbase.cblite" % "CBLiteEktorp" % "0.7.2"
 )

org.scalastyle.sbt.ScalastylePlugin.Settings
