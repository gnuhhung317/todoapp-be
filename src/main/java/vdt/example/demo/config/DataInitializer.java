package vdt.example.demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vdt.example.demo.model.Todo;
import vdt.example.demo.repository.TodoRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final TodoRepository todoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Clear existing data
        todoRepository.deleteAll();

        // Add sample data
        Todo todo1 = new Todo();
        todo1.setTitle("Learn Spring Boot");
        todo1.setDescription("Study Spring Boot framework and create a simple REST API");
        todo1.setCompleted(false);

        Todo todo2 = new Todo();
        todo2.setTitle("Build Todo Application");
        todo2.setDescription("Create a complete Todo list application with CRUD operations");
        todo2.setCompleted(false);

        Todo todo3 = new Todo();
        todo3.setTitle("Deploy to Production");
        todo3.setDescription("Deploy the application to a cloud platform");
        todo3.setCompleted(true);

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        log.info("Sample data initialized successfully!");
    }
}