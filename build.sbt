name := "scala-bloch-sphere"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.102-R11"

libraryDependencies += "org.scalanlp" % "breeze_2.12" % "0.13.1"
libraryDependencies += "org.scalanlp" % "breeze-viz_2.12" % "0.13.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"//resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
//fork := trueinvert qubit