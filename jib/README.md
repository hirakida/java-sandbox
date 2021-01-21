
Push to Docker Hub

```
./gradlew jib
```

Build to a Docker deamon

```
./gradlew jibDockerBuild 
```

```
docker run -p 8080:8080 hirakida/jib-demo
```
