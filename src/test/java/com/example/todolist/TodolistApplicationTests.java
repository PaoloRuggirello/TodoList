package com.example.todolist;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskList;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TodolistApplicationTests {

    @Test
    public void contextLoads() {
    }

    private static final String API_ROOT = "http://localhost:8080/";
    private TaskList randomTaskList = createRandomTaskList();


    @Test
    public void insertByPost() {
        Task task = createRandomTask();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(task)
                .post(API_ROOT + "task/create");

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    private Task createRandomTask() {
        Task task = new Task();
        task.setNome(randomAlphabetic(10));
        task.setDescrizione(randomAlphabetic(15));
        task.setList(randomTaskList);
        return task;
    }

    private TaskList createRandomTaskList() {
        TaskList taskList = new TaskList();
        taskList.setNome(randomAlphabetic(10));
        return taskList;
    }
}
