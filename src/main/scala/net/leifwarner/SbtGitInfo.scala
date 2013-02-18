package net.leifwarner

import sbt._
import java.util.Properties
// For accessing git version info
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.api.Git

object SbtGitInfo extends sbt.Plugin {
  import Keys._
  import Project.Initialize

  // Add this to the list of resources being generated.
  lazy val setting: Project.Setting[_] =
    resourceGenerators in Compile <+= (version, resourceManaged, streams) map gitProps

  implicit def map2props(m:Map[String, String]): Properties = {
    val props = new Properties
    m foreach { case (k,v) => props.setProperty(k,v) }
    props
  }

  def gitProps(version: String, resources:File, s: TaskStreams): Seq[File] = {
    val repo = (new FileRepositoryBuilder).findGitDir.build
    val git = new Git(repo)
    val commit = git.log.call.iterator.next
    val author = commit.getAuthorIdent
    val commitInfo = Map(
      "project.version" -> version,
      "git.branch" -> repo.getBranch,
      "git.commit.id" -> commit.name,
      "git.commit.time" -> author.getWhen.toString,
      "git.commit.user.name" -> author.getName,
      "git.commit.user.email" -> author.getEmailAddress,
      "git.commit.message.short" -> commit.getShortMessage,
      "git.commit.message.full" -> commit.getFullMessage
    )
    IO.createDirectory(resources)
    val gitPropFile = resources / "git.properties"
    IO.write(commitInfo, "Build info", gitPropFile)
    s.log.info("Recording last commit: "+commit.getShortMessage)
    Seq(gitPropFile)
  }
}
