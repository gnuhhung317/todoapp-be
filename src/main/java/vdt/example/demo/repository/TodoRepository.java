package vdt.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vdt.example.demo.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}