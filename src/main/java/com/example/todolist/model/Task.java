package com.example.todolist.model;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Table(name = "TASK")
@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "NOME", unique = true)
    private String nome;

    @Column(name = "DESCRIZIONE")
    private String descrizione;

    @Column(name = "DATA")
    private Date data;

    @Column(name = "DONE")
    private boolean done;

    public Task() {
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
=======
    @GeneratedValue(strategy = GenerationType.IDENTITY)////PAOLOOOOOOOOOOO
>>>>>>> d84b8a7ad7a3360dfc72c60b8d5108c5902acdd8
}
