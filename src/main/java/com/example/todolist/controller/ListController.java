package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskList;
import com.example.todolist.persistence.ListRepository;
import com.example.todolist.persistence.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/list")
public class ListController {

    @Autowired
    ListRepository listrep;
    @Autowired
    TaskRepository taskrepo;

    @GetMapping("/get/{id}")
    public Optional<TaskList> findById(@PathVariable( name = "id") long id){
        return listrep.findById(id);
    }

    @PostMapping("/create")
    public TaskList createList(@RequestParam(value = "nome") String nome){
        TaskList tskL = new TaskList(nome);
        return listrep.save(tskL);
    }

    @PostMapping("/bindTaskWithList")
    public TaskList bindList(@RequestParam(value = "idTask") long idTask, @RequestParam(value = "idList") long idList){
        TaskList taskList = listrep.findById(idList).orElse(null);
        if(taskList != null){
            System.out.println("Tasklist Name: " + taskList.getNome());
            Task task = taskrepo.findById(idTask).orElse(null);
            if(task != null ){
                taskList.addTask(task);
                listrep.save(taskList);
                } else {
                System.out.println("Errore nell'inserimento del nuovo task");
            }
        } else {
            System.out.println("List non esistente");
        }
        return taskList;
    }



}
