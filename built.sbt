name := "sbt-git-info"

organization := "net.leifwarner"

version := "0.1-SNAPSHOT"

description := "An sbt plugin to embed git status into a .properties file."

libraryDependencies += "org.eclipse.jgit" % "org.eclipse.jgit" % "2.2.0.201212191850-r"

sbtPlugin := true
