package com.example;

import static com.example.repository.DocumentRepository.KEY1_FIELD;
import static com.example.repository.DocumentRepository.KEY2_FIELD;
import static com.example.repository.DocumentRepository.KEY3_FIELD;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

import org.bson.Document;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.CappedDocumentRepository;
import com.example.repository.DocumentRepository;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationEvent {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final CappedDocumentRepository cappedDocumentRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void readyEvent() {
        roleRepository.insertOne(new Role(0, "user"));
        roleRepository.insertOne(new Role(1, "admin"));

        List<User> users = IntStream.rangeClosed(1, 5)
                                    .mapToObj(i -> new User(i, "user" + i, i % 2, 20 + i))
                                    .collect(toList());
        userRepository.bulkWrite(users);

        List<Document> documents = IntStream.rangeClosed(1, 5)
                                            .mapToObj(i -> {
                                                Document document = new Document();
                                                document.append(KEY1_FIELD, i);
                                                document.append(KEY2_FIELD, i * 2);
                                                document.append(KEY3_FIELD, "value" + i % 2);
                                                return document;
                                            })
                                            .collect(toList());
        documentRepository.insertMany(documents);

        IntStream.rangeClosed(1, 10)
                 .mapToObj(i -> {
                     Document document = new Document();
                     document.append("key1", i);
                     document.append("value1", "value" + i);
                     return document;
                 }).forEach(cappedDocumentRepository::insertOne);

        log.info("countDocuments: {}", userRepository.countDocuments());
        log.info("countDocuments: {}", documentRepository.countDocuments());
        log.info("countDocuments: {}", cappedDocumentRepository.countDocuments());

        for (Document document : documentRepository.find()) {
            log.info("{}", document);
        }
        for (Document document : cappedDocumentRepository.find()) {
            log.info("{}", document);
        }

        for (Document document : userRepository.max()) {
            log.info("max: {}", document);
        }
        for (Document document : userRepository.min()) {
            log.info("min: {}", document);
        }
        for (Document document : userRepository.avg()) {
            log.info("avg: {}", document);
        }
        for (Document document : userRepository.sum()) {
            log.info("mum: {}", document);
        }
        for (Document document : userRepository.lookup()) {
            log.info("lookup: {}", document);
        }
    }
}
