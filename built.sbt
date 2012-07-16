name := "sbt-git-info"

organization := "net.leifwarner"

version := "0.1-SNAPSHOT"

description := "An sbt plugin to embed git status into a .properties file."

libraryDependencies += "org.eclipse.jgit" % "org.eclipse.jgit" % "1.3.0.201202151440-r"

sbtPlugin := true
