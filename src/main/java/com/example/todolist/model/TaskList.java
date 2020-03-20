package com.example.todolist.model;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasklist")
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(unique = true, length = 50)
    private String nome;

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;


    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(String nome){
        this();
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getTasks() {
        List<String> result = new ArrayList<>();
        for(Task t : tasks)
            result.add(t.toString());
        return result;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean addTask(Task task){
        return this.tasks.add(task);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("nome", nome)
                .toString();
    }
}
