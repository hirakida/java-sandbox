package com.example;

import static com.example.config.Constants.CREDENTIALS_META_FILE;
import static com.example.config.Constants.GITHUB_REPOSITORY;
import static com.example.config.Constants.META_REPOSITORY;
import static com.example.config.Constants.MIRRORS_META_FILE;
import static com.example.config.Constants.MY_FILE_PATH;
import static com.example.config.Constants.MY_PROJECT;
import static com.example.config.Constants.MY_REPOSITORY;

import java.util.Map;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.linecorp.centraldogma.client.CentralDogma;
import com.linecorp.centraldogma.common.Entry;
import com.linecorp.centraldogma.common.Revision;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventListener {
    private final CentralDogma dogma;
    private final CentralDogmaService service;
    private final CentralDogmaWatcher watcher;

    @EventListener(ApplicationReadyEvent.class)
    public void readyEvent() {
        dogma.listProjects()
             .thenAccept(projects -> {
                 log.info("projects: {}", projects);
                 if (!projects.contains(MY_PROJECT)) {
                     dogma.createProject(MY_PROJECT).join();
                 }
             }).join();

        dogma.listRepositories(MY_PROJECT)
             .thenAccept(repositories -> {
                 log.info("repositories: {}", repositories);
                 if (!repositories.containsKey(MY_REPOSITORY)) {
                     dogma.createRepository(MY_PROJECT, MY_REPOSITORY).join();
                 }
                 if (!repositories.containsKey(GITHUB_REPOSITORY)) {
                     dogma.createRepository(MY_PROJECT, GITHUB_REPOSITORY).join();
                 }
             }).join();

        dogma.listFiles(MY_PROJECT, MY_REPOSITORY, Revision.HEAD, "/**")
             .thenAccept(files -> {
                 log.info("files: {}", files);
                 if (!files.containsKey(MY_FILE_PATH)) {
                     Map<String, Object> value = Map.of("key1", "value1", "key2", "value2");
                     service.createFile(MY_PROJECT, MY_REPOSITORY, MY_FILE_PATH, "Add " + MY_FILE_PATH, value);
                 }

                 Entry<?> file = dogma.getFile(MY_PROJECT, MY_REPOSITORY, Revision.HEAD, MY_FILE_PATH).join();
                 log.info("file: {}", file);
             }).join();

        service.createMetaFile(MY_PROJECT, CREDENTIALS_META_FILE, "Add " + CREDENTIALS_META_FILE);
        service.createMetaFile(MY_PROJECT, MIRRORS_META_FILE, "Add " + MIRRORS_META_FILE);

        watcher.repositoryWatcher(MY_PROJECT, MY_REPOSITORY, "/**");
        watcher.repositoryWatcher(MY_PROJECT, GITHUB_REPOSITORY, "/**");
        watcher.repositoryWatcher(MY_PROJECT, META_REPOSITORY, "/**");
        watcher.fileWatcher(MY_PROJECT, MY_REPOSITORY, MY_FILE_PATH);
    }
}
