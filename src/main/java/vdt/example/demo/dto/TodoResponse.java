package vdt.example.demo.dto;

import lombok.Data;
import vdt.example.demo.model.Todo;

import java.time.LocalDateTime;

@Data
public class TodoResponse {

    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TodoResponse fromTodo(Todo todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.getCompleted());
        response.setCreatedAt(todo.getCreatedAt());
        response.setUpdatedAt(todo.getUpdatedAt());
        return response;
    }
}