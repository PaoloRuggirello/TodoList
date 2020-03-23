package com.example.todolist.persistence;

import com.example.todolist.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<TaskList, Long> {
    TaskList findByNome(String nome);
}
