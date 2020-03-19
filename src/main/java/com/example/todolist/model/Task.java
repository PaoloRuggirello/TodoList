package com.example.todolist.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Task")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
}
