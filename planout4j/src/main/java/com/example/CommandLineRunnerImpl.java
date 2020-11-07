package com.example;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.glassdoor.planout4j.Namespace;
import com.glassdoor.planout4j.NamespaceFactory;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static final String NAMESPACE = "namespace";
    private static final Logger log = LoggerFactory.getLogger(CommandLineRunnerImpl.class);
    private final NamespaceFactory namespaceFactory;

    public CommandLineRunnerImpl(NamespaceFactory namespaceFactory) {
        this.namespaceFactory = namespaceFactory;
    }

    @Override
    public void run(String... args) {
        IntStream.rangeClosed(0, 20)
                 .forEach(i -> {
                     Map<String, ?> overrides = Map.of("datetime", LocalDateTime.now());
                     Namespace namespace = namespaceFactory.getNamespace(NAMESPACE,
                                                                         Map.of("userid", i),
                                                                         overrides)
                                                           .get();
                     log.info("{}", namespace.getParams());
                 });
    }
}
