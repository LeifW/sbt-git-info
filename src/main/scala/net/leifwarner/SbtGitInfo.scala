package net.leifwarner

import sbt._
// For accessing git version info
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.api.Git

object SbtGitInfo extends sbt.Plugin {
  import Keys._
  import Project.Initialize

  // Add this to the list of resources being generated.
  lazy val setting: Project.Setting[_] =
    resourceGenerators in Compile <+= (version, resourceManaged, streams) map gitProps

  def gitProps(version: String, resources:File, s: TaskStreams) = {
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
    val props = new java.util.Properties
    commitInfo foreach { case (k,v) => props.setProperty(k,v) }
    IO.createDirectory(resources)
    val file = resources / "git.properties"
    Using.fileWriter()(file)(props.store(_, "Build info"))
    s.log.info("Last commit: "+commit.getShortMessage)
    Seq(file)
  }
}
