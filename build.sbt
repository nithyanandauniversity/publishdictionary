name := "publishdictionary"

version := "0.1"

scalaVersion := "2.13.3"

// https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.32.3.2"

// https://mvnrepository.com/artifact/mysql/mysql-connector-java
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.21"

// https://mvnrepository.com/artifact/com.lihaoyi/cask
libraryDependencies += "com.lihaoyi" %% "cask" % "0.7.5"

libraryDependencies += "io.getquill" %% "quill-jdbc" % "3.5.3"

libraryDependencies += "io.getquill" %% "quill-jdbc-monix" % "3.5.3"

// https://mvnrepository.com/artifact/org.scala-lang.modules/scala-xml
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0"

// https://mvnrepository.com/artifact/org.fastily/jwiki
libraryDependencies += "org.fastily" % "jwiki" % "1.8.0"

// https://mvnrepository.com/artifact/io.dropwizard.metrics/metrics-core
libraryDependencies += "io.dropwizard.metrics" % "metrics-core" % "4.1.12.1"

libraryDependencies += "io.dropwizard.metrics" % "metrics-jmx" % "4.1.12.1"

// https://mvnrepository.com/artifact/com.ibm.icu/icu4j
libraryDependencies += "com.ibm.icu" % "icu4j" % "67.1"

// https://mvnrepository.com/artifact/io.github.andrebeat/scala-pool
libraryDependencies += "io.github.andrebeat" %% "scala-pool" % "0.4.3"
