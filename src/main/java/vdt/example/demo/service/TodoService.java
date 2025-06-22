package vdt.example.demo.service;

import vdt.example.demo.dto.TodoRequest;
import vdt.example.demo.dto.TodoResponse;

import java.util.List;

public interface TodoService {

    List<TodoResponse> getAllTodos();

    TodoResponse getTodoById(Long id);

    TodoResponse createTodo(TodoRequest todoRequest);

    TodoResponse updateTodo(Long id, TodoRequest todoRequest);

    void deleteTodo(Long id);
}