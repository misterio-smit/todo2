package com.todo.smart.worldssp.todo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность списка дел
 */
@Entity
@Table(name = "todoList")
@Data

public class ListEntityTodo {

    /**
     * Пустой конструктор
     */
    public ListEntityTodo() {
    }

    /**
     * Конструктор списка дел
     * @param name имя списка дел
     */
    public ListEntityTodo(String name) {
        this.name = name;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID listId;

    @Column(nullable = false)
    private String name;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime listCreationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime listUpdateDate;

}
