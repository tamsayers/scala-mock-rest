import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "scalaMockRest"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.mockito" % "mockito-all" % "1.9.5" % "test",
    jdbc,
    anorm)

  val main = play.Project(appName, appVersion, appDependencies).settings( // Add your own project settings here      
  )

}
