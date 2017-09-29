A [Giter8][g8] template for Akka HTTP in Java

Prerequisites:
- JDK 8
- [sbt][sbt] 0.13.13 or higher ([download here][sbt_download])

Open a console and run the following command to apply this template:
 ```
sbt -Dsbt.version=0.13.15 new https://github.com/akka/akka-http-java-seed.g8
 ```

This template will prompt for the following parameters. Press `Enter` if the default values suit you:
- `name`: Becomes the name of the project.
- `organisation`: Provides an organisation name for the project.
- `akka-http-version`: Specifies which version of Akka HTTP should be used for this project.
- `akka-version`: Specifies which version of Akka should be used for this project.

This template comes with example for an Akka HTTP server on Java `QuickstartServer`, along with their respective tests.

Once inside the project folder, to run this code, you can use any of the following commands:
```
sbt run
```
This will prompt you with the 2 different servers, so you can pick the one you prefer.

Alternatively, you can run:

```
sbt runMain com.lightbend.akka.http.sample.QuickstartServer
```

to directly run the specified file.

This template also provides build descriptors for maven and gradle. You can use any of the following commands to run 
the application:
```
mvn exec:exec
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
