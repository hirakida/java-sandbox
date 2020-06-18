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

import com.example.model.User;
import com.example.repository.DocumentRepository;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventListener {
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void readyEvent() {
        List<User> users = IntStream.rangeClosed(1, 10)
                                    .mapToObj(i -> new User(i, "user" + i, 20 + i))
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

        log.info("countDocuments: {}", userRepository.countDocuments());
        log.info("countDocuments: {}", documentRepository.countDocuments());

        for (Document document : documentRepository.find()) {
            log.info("{}", document);
        }
    }
}
