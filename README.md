To use:

`git clone git://github.com:LeifW/sbt-git-info.git`

`sbt publish-local`

Add this to your project/plugins.sbt:

`addSbtPlugin("net.leifwarner" % "sbt-git-info" % "0.1-SNAPSHOT")`

And this to your build.sbt:

`net.leifwarner.SbtGitInfo.setting`

Alternatively, you could add it as a dep by referencing the git repo as a subproject, like so: https://github.com/guardian/sbt-version-info-plugin

I'll work on putting this in a maven repo in a little bit, so won't need those first two steps.

This will generate a `git.properties` file in the classpath of the generated .jar (same thing as putting it in `src/main/resources`.  You can unzip it out of the jar, or, to access it at runtime, read the properties file off the classpath like so:

```scala
// Properties files are widely documented elsewhere, and accesible from any langauge on the JVM
val properties = new java.util.Properties
// Open an InputStream of the file off the classpath
val file = getClass.getClassLoader.getResourceAsStream("git.properties")
// if the file exists
if (file != null ) { 
  // Parse / read the file in
  properties.load(file)
  file.close
}
// From this point, you can say properties.get("git.branch") to read attributes one at a time, 
//or do something like the following to convert it into a Scala Map:
import collection.JavaConversions.enumerationAsScalaIterator
val props = properties.propertyNames map {case k:String => k -> properties.get(k).asInstanceOf[String] } toMap

// From there you can say things like props("git.branch")
```
