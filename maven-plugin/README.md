
http://maven.apache.org/plugin-tools/maven-plugin-plugin/examples/using-annotations.html

```
$ ./mvnw clean install

$ ./mvnw com.github.hirakida.mojo:hello-maven-plugin:hello -Dhello.message='hello maven-plugin!'
$ ./mvnw com.github.hirakida.mojo:hello-maven-plugin:help

$ ./mvnw compile [-debug] -f example/pom.xml
```
