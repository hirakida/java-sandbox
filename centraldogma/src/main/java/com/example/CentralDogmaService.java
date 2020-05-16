package com.example;

import static com.example.config.Constants.META_REPOSITORY;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.centraldogma.client.CentralDogma;
import com.linecorp.centraldogma.common.Change;
import com.linecorp.centraldogma.common.Revision;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CentralDogmaService {
    private final CentralDogma dogma;
    private final ObjectMapper objectMapper;

    public void createMetaFile(String projectName, String fileName, String summary) {
        String path = '/' + fileName;
        String jsonText = readText("meta/" + fileName);
        createFile(projectName, META_REPOSITORY, path, summary, jsonText);
    }

    public void createFile(String projectName, String repositoryName, String path, String summary,
                           Map<String, Object> value) {
        createFile(projectName, repositoryName, path, summary, writeValueAsString(value));
    }

    public void createFile(String projectName, String repositoryName, String path, String summary,
                           String jsonText) {
        dogma.listFiles(projectName, repositoryName, Revision.HEAD, path)
             .thenAccept(files -> {
                 if (!files.containsKey(path)) {
                     Change<JsonNode> change = Change.ofJsonUpsert(path, jsonText);
                     push(projectName, repositoryName, summary, change);
                 }
             }).join();
    }

    public void push(String projectName, String repositoryName, String summary, Change<JsonNode> change) {
        dogma.push(projectName, repositoryName, Revision.HEAD, summary, change)
             .thenAccept(result -> {
                 log.info("revision:{} whenAsText:{}", result.revision(), result.whenAsText());
             }).join();
    }

    private String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String readText(String fileName) {
        try {
            URI uri = ClassLoader.getSystemResource(fileName).toURI();
            return Files.readString(Paths.get(uri));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
