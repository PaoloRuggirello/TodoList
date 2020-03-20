package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskList;
import com.example.todolist.persistence.ListRepository;
import com.example.todolist.persistence.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/list")
public class ListController {

    @Autowired
    ListRepository listrep;
    @Autowired
    TaskRepository taskrepo;

    @GetMapping("/get")
    public Optional<TaskList> findById(@RequestParam( name = "id") long id){
        return listrep.findById(id);
    }

    @GetMapping("/getAll")
    public List<TaskList> findAll(){
        return listrep.findAll();
    }

    @PostMapping("/create")
    public TaskList createList(@RequestParam(value = "nome") String nome){
        TaskList tskL = new TaskList(nome);
        return listrep.save(tskL);
    }





}
