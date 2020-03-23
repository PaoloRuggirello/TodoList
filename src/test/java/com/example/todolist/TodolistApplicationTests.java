package com.example.todolist;

import com.example.todolist.model.Task;
import com.example.todolist.model.TaskList;
import com.example.todolist.persistence.ListRepository;
import com.example.todolist.persistence.TaskRepository;
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
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodolistApplicationTests {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private TaskRepository taskRepository;

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
    public void changeList(){
        //Getting a task
        Task task = gettingTask();

        //Getting a list different from the task's one
        long newIdList = gettingList(task.getListId());

        //Changing the list
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post(API_ROOT + "task/changeList?idTask=" + task.getId() + "&idList=" + newIdList);

        response.getBody().prettyPrint();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void getTask() {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get(API_ROOT + "task/getAll");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }


    @Test
    public void doubleCreation(){
        Task task = createRandomTask();

        createTaskList(randomTaskList);

        //Creating the first task, it should be ok
        Response firstCreation = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post(API_ROOT + "task/create?nome=" + task.getNome() + "&idList=" + listRepository.findByNome(randomTaskList.getNome()).getId());

        //Should return an error
        Response secondCreation = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post(API_ROOT + "task/create?nome=" + task.getNome() + "&idList=" + listRepository.findByNome(randomTaskList.getNome()).getId());

        assertEquals(500, secondCreation.getStatusCode());

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

    private void createTaskList(TaskList list) {
        System.out.println("NOME LISTA: " + list.getNome());
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post(API_ROOT + "list/create?nome=" + list.getNome());
    }

    private Task gettingTask(){
        boolean found = false;
        long index = (long)(Math.random() * taskRepository.count()) + 1;
        Task toReturn = null;


        while (!found && index <= taskRepository.count()){
            try{
                toReturn = taskRepository.findById(index).orElseThrow(Exception::new);
                found = true;
            }catch (Exception e ){
                index=(long)(Math.random() * listRepository.count()) + 1;
            }
        }
        return toReturn;
    }

    private Long gettingList(long indexToAvoid){
        boolean found = false;
        TaskList list = null;
        long indexToReturn = -1;
        long index = (long)(Math.random() * listRepository.count()) + 1;


        while(!found && index <= listRepository.count()){
            try{
                if(index != indexToAvoid) {
                    list = listRepository.findById(index).orElseThrow(Exception::new);
                    indexToReturn = list.getId();
                    found = true;
                } else {
                    throw new Exception();
                }
            }catch (Exception e){
                index = (long)(Math.random() * listRepository.count()) + 1;
            }

        }
        return indexToReturn;
    }

}

