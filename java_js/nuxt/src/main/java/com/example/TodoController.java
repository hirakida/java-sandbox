package com.example;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.net.URI;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoRepository todoRepository;

    @GetMapping
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Validated TodoRequest request) {
        Todo todo = new Todo();
        todo.setText(request.getText());
        todoRepository.save(todo);
        return ResponseEntity.created(URI.create("/api/todos/" + todo.getId()))
                             .build();
    }

    @PutMapping("/{id}")
    public Todo update(@PathVariable int id, @RequestBody TodoRequest request) {
        Todo todo = todoRepository.findById(id)
                                  .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        todo.setDone(request.isDone());
        return todoRepository.save(todo);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void deleteAll() {
        List<Todo> todos = todoRepository.findByDoneTrue();
        todoRepository.deleteAll(todos);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        todoRepository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return new ResponseEntity<>(null, null, NOT_FOUND);
    }

    @Data
    public static class TodoRequest {
        @NotEmpty
        private String text;
        private boolean done;
    }
}
