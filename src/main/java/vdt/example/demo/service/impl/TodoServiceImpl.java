package vdt.example.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vdt.example.demo.dto.TodoRequest;
import vdt.example.demo.dto.TodoResponse;
import vdt.example.demo.model.Todo;
import vdt.example.demo.repository.TodoRepository;
import vdt.example.demo.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(TodoResponse::fromTodo)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponse getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
        return TodoResponse.fromTodo(todo);
    }

    @Override
    public TodoResponse createTodo(TodoRequest todoRequest) {
        Todo todo = new Todo();
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setCompleted(todoRequest.getCompleted());

        Todo savedTodo = todoRepository.save(todo);
        return TodoResponse.fromTodo(savedTodo);
    }

    @Override
    public TodoResponse updateTodo(Long id, TodoRequest todoRequest) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setCompleted(todoRequest.getCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return TodoResponse.fromTodo(updatedTodo);
    }

    @Override
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }
}