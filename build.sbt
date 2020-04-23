name := "CouchCirceSDK0422NEW"

version := "0.1"

scalaVersion := "2.12.10"
resolvers += "Couchbase Repository" at "http://files.couchbase.com/maven2/"

libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.12.1"
libraryDependencies += "com.couchbase.client" %% "scala-client" % "1.0.3"
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.29"
//libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.6"
//libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.5.29"
libraryDependencies += "io.circe" %% "circe-parser" % "0.13.0"
libraryDependencies += "io.circe" %% "circe-core" % "0.13.0"
libraryDependencies += "io.circe" %% "circe-generic" % "0.13.0"
libraryDependencies += "io.circe" %% "circe-literal" % "0.13.0" % Test
libraryDependencies += "io.circe" %% "circe-optics" % "0.13.0"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.1.1"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.1.1"


libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.5"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.5"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.5"
libraryDependencies += "org.apache.spark" %% "spark-graphx" % "2.4.5"
//libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.3"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.5"
//libraryDependencies += "org.apache.spark" %% "spark-mllib" % "3.0.0-preview2" % "runtime"


//libraryDependencies += "org.scalanlp" %% "breeze" % "1.0"
libraryDependencies += "org.plotly-scala" %% "plotly-core" % "0.7.3"
libraryDependencies += "org.plotly-scala" %% "plotly-render" % "0.7.3"
