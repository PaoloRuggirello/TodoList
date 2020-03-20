package com.example.todolist.controller;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskList;
import com.example.todolist.persistence.ListRepository;
import com.example.todolist.persistence.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskRepository tskrep;
    @Autowired
    ListRepository lstrep;

    @GetMapping("/getAll")
    public List<Task> findAllTasks() {
        return tskrep.findAll();
    }

    @PostMapping("/create")
    public Task create(@RequestParam(value = "nome") String nome,
                       @RequestParam(value = "idList") long idList,
                       @RequestParam(value = "descrizione", defaultValue = "") String descrizione) {
        Date date = new Date();
        TaskList list = lstrep.findById(idList).orElse(null);
        if (list != null) {
            Task t = new Task(nome, list, date, descrizione);
            return tskrep.save(t);
        }
        return null;
    }

    @PostMapping("/changeList")
    public TaskList bindList(@RequestParam(value = "idTask") long idTask, @RequestParam(value = "idList") long idList) {
        Task task = tskrep.findById(idTask).orElse(null);
        if (task != null) {
            TaskList taskList = lstrep.findById(idList).orElse(null);
            if (taskList != null) {
                task.setList(taskList);
                tskrep.save(task);
                return taskList;
            } else {
                System.out.println("List non esistente");
            }
        } else {
            System.out.println("Task non esistente");
        }
        return null;
    }

    @DeleteMapping("delete")
    public Task deleteTask(@RequestParam(value = "id", defaultValue = "") long id) {
        Task toDelete = tskrep.findByNome(id);
        if (toDelete != null) {
            tskrep.delete(toDelete);
            return toDelete;
        } else {
            return null;
        }

    }


}
