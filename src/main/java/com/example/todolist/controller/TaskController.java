package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.persistence.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskRepository tskrep;

    @GetMapping("/findAllTasks")
    public List<Task> findAllTasks(){
        List<Task> tasks = tskrep.findAll();
        return tasks;
    }



}
