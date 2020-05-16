package com.example;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.centraldogma.client.CentralDogma;
import com.linecorp.centraldogma.client.Watcher;
import com.linecorp.centraldogma.common.Query;
import com.linecorp.centraldogma.common.Revision;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CentralDogmaWatcher {
    private final CentralDogma dogma;
    private final List<Watcher<Revision>> repositoryWatchers = new ArrayList<>();
    private final List<Watcher<JsonNode>> fileWatchers = new ArrayList<>();

    @PreDestroy
    public void close() {
        repositoryWatchers.forEach(Watcher::close);
        fileWatchers.forEach(Watcher::close);
    }

    public void repositoryWatcher(String projectName, String repositoryName, String pathPattern) {
        Watcher<Revision> watcher = dogma.repositoryWatcher(projectName, repositoryName, pathPattern);
        watcher.watch(value -> {
            log.info("watch {} {} {}: {}", projectName, repositoryName, pathPattern, value);
        });
        repositoryWatchers.add(watcher);
    }

    public void fileWatcher(String projectName, String repositoryName, String path) {
        Watcher<JsonNode> watcher = dogma.fileWatcher(projectName, repositoryName, Query.ofJsonPath(path));
        watcher.watch(value -> {
            log.info("watch {} {} {}: {}", projectName, repositoryName, path, value);
        });
        fileWatchers.add(watcher);
    }
}
