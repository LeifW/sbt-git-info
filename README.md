To use:

`git clone git://github.com:LeifW/sbt-git-info.git`

`sbt publish-local`

Add this to your project/plugins.sbt:

`addSbtPlugin("net.leifwarner" % "sbt-git-info" % "0.1-SNAPSHOT")`

And this to your build.sbt:

`net.leifwarner.SbtGitInfo.setting`

Or do something like this: https://github.com/guardian/sbt-version-info-plugin

I'll work on putting this in a maven repo in a little bit, so won't need those first two steps.
