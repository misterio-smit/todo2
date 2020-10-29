package com.todo.smart.worldssp.todo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность дела
 */
@Entity
@Data
@Table(name = "todo")

public class EntityTodo {

    /**
     * Пустой конструктор
     */
    public EntityTodo() {
    }

    /**
     * @param id UUID  идентификатор дела
     * @param shortDescription краткое описание дела
     * @param name название дела
     * @param priority срочность
     */
    public EntityTodo(UUID id, String shortDescription, String name, Short priority) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.name = name;
        this.priority = priority;
    }

    @Id
    @Column(name = "id")
    private UUID id;

    private ListEntityTodo listEntityTodo;

    @Column(name = "shortDescription")
    private String shortDescription;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "createDate", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @Column(name = "updateDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    @Column(name = "done")
    private int done;

    @Column(name = "priority")
    private Short priority;



    public void markAsDone(){
        if (done == 0) {
            done = 1;
        } else
            done = 0;
    }



}
