## Akka HTTP quickstart in Java

Please see the [quickstart guide](https://developer.lightbend.com/guides/akka-http-quickstart-java/) for a
walk through the code.

You can use [Giter8][g8] to create your own project from the quickstart. Or, download and extract the zip file(https://example.lightbend.com/v1/download/akka-quickstart-scala?name=akka-quickstart-scala) to a convenient location.

Prerequisites:
- JDK 8
- [sbt][sbt] 1.4.5 or higher ([download here][sbt_download])

Open a console and run the following command to apply this template:
 ```
sbt -Dsbt.version=1.4.5 new akka/akka-http-java-seed.g8
 ```

This template will prompt for the following parameters. Press `Enter` if the default values suit you:
- `name`: Becomes the name of the project.
- `organisation`: Provides an organisation name for the project.
- `akka-http-version`: Specifies which version of Akka HTTP should be used for this project.
- `akka-version`: Specifies which version of Akka should be used for this project.

This template comes with example for an Akka HTTP server on Java `QuickstartServer`, along with their respective tests.

Once inside the project folder, to run this code, you can following command to run server:
```
sbt run
```

This template also provides build descriptors for maven and gradle. You can use any of the following commands to run 
the application:
```
mvn compile
mvn exec:java
```
or
```
gradle run
```
Both commands run `com.lightbend.akka.http.sample.QuickstartServer` by default.


Template license
----------------
Written in 2017 by Lightbend, Inc.

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.

[g8]: http://www.foundweekends.org/giter8/
[sbt]: http://www.scala-sbt.org/
[sbt_download]: http://www.scala-sbt.org/download.html
