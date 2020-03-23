package com.example.todolist;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskList;
import com.example.todolist.persistence.ListRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodolistApplicationTests {

    @Autowired
    private ListRepository listRepository;

    @Test
    public void contextLoads() {
    }

    private static final String API_ROOT = "http://localhost:8080/";
    private TaskList randomTaskList = createRandomTaskList();


    @Test
    public void createTaskList() {
        System.out.println("NOME : " + randomTaskList.getNome());
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post(API_ROOT + "list/create?nome=" + randomTaskList.getNome());

        response.getBody().prettyPrint();
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    public void createTaskList(TaskList list) {
        System.out.println("NOME LISTA: " + list.getNome());
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post(API_ROOT + "list/create?nome=" + list.getNome());
    }

    @Test
    public void createTask() {
        Task task = createRandomTask();
        createTaskList(randomTaskList);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post(API_ROOT + "task/create?nome=" + task.getNome() + "&idList=" + listRepository.findByNome(randomTaskList.getNome()).getId());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void getTask() {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get(API_ROOT + "task/getAll");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
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
