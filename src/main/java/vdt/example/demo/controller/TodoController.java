package vdt.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vdt.example.demo.dto.TodoRequest;
import vdt.example.demo.dto.TodoResponse;
import vdt.example.demo.service.TodoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        return ResponseEntity.ok(Map.of("message", "Backend is working!"));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        List<TodoResponse> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        TodoResponse todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@RequestBody TodoRequest todoRequest) {
        TodoResponse createdTodo = todoService.createTodo(todoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id, @RequestBody TodoRequest todoRequest) {
        TodoResponse updatedTodo = todoService.updateTodo(id, todoRequest);
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}