package com.todo.smart.worldssp.todo.controller;

import com.todo.smart.worldssp.todo.entity.EntityTodo;
import com.todo.smart.worldssp.todo.errors.*;
import com.todo.smart.worldssp.todo.repo.TodoRepo;
import com.todo.smart.worldssp.todo.service.TodoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Контроллер дела
 */
@RestController

public class TodoController {
    private final TodoService todoService;




    /**Конструктор контроллера дела
     * @param todoService сервис работы с делом
     */
    @Autowired
    public TodoController( TodoService todoService) {
        this.todoService = todoService;

    }



    @GetMapping("{id}")
    public EntityTodo getOne(@PathVariable("id") TodoService todoService) {
        return getOne(todoService);
    }

    @PostMapping(value = "/todo/{listId}")
    public EntityTodo createTodo(@PathVariable(value = "listId")UUID listId, @RequestBody @Valid EntityTodo entityTodo) {
        entityTodo.setCreationDate(LocalDateTime.now());
        return todoService.createEntity(listId,entityTodo);
    }

    @PutMapping("{id}")
    @PatchMapping(value = "/todo/{id}")
    public EntityTodo changeTodo(@PathVariable(value = "id") UUID id, @RequestBody Map<String, Object> updateDate){
        return todoService.changeTodo(id, updateDate);
    }

    @DeleteMapping(value = "/todo/{id}")
    public void delete(@PathVariable(value = "id")UUID id) {
        todoService.delete(id);
    }

    @PostMapping(value = "/todo/{id}/markDone")
    public EntityTodo markAsDone(@PathVariable(value = "id") UUID id) {
        return todoService.markTodoAsDone(id);
    }




}
