
```
java -XX:StartFlightRecording -XX:FlightRecorderOptions=repository=repo src/main/java/com/example/HelloMain.java

java -XX:StartFlightRecording=duration=10s,filename=hello.jfr src/main/java/com/example/HelloMain.java
```

```
java src/main/java/com/example/EventStreamMain.java repo/2020_xx_xx_xx_xx_xx_xxxxx 
```
