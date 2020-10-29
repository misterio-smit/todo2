package com.todo.smart.worldssp.todo.service;


import com.todo.smart.worldssp.todo.entity.*;
import com.todo.smart.worldssp.todo.errors.*;
import com.todo.smart.worldssp.todo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис работы с делами
 */
@Service
public class TodoService {

    private final TodoListRepo todoListRepo;
    private final TodoRepo todoRepo;

    /** Конструктор сервиса дел. Подключает интерфейсы репозиториев списка дел и делаю
     * @param todoListRepo репозиторий списка дел
     * @param todoRepo репозиторий дела
     */
    @Autowired
    public TodoService(TodoListRepo todoListRepo, TodoRepo todoRepo) {
        this.todoListRepo = todoListRepo;
        this.todoRepo = todoRepo;
    }




    /** Сохранение дела в репозиторий
     * @param listId идентификатор списка дел, к которому привязано дело
     * @param entityTodo данные дела
     * @return Созданное дело
     */
    public EntityTodo createEntity (UUID listId, EntityTodo entityTodo){
        ListEntityTodo listEntityTodo = todoListRepo.findById(listId).orElseThrow(() -> new NotFoundException("List", listId));
        entityTodo.setListEntityTodo(listEntityTodo);
        return todoRepo.save(entityTodo);
    }


    /**
     * Удаляет дело с данным репозиторием или вызывает "Не найдено"
     * @param id - идентификатор дела
     */
    public void delete(UUID id){
        if (todoRepo.existsById(id)){
            todoRepo.deleteById(id);
        } else {
            throw new NotFoundException("Task", id);
        }
    }

    /**Помечает дело с данным идентификатором как выполненное и сохраняет в репозиторий или вызывает "Не найдено"
     * @param id  id дела
     * @return
     */
    public EntityTodo markTodoAsDone(UUID id){
        return todoRepo.findById(id).map(todo -> {
            todo.markAsDone();
            return todoRepo.save(todo);
        }).orElseThrow(() -> new NotFoundException("Todo", id));
    }


    /**
     * Изменяет дело с указанным идентификатором
     * @param id - идентификатор дела
     * @param updateData - Пары "Свойство: Значение" для обновления дела
     * @return Изменённое дело
     */
    public EntityTodo changeTodo(UUID id, Map<String, Object> updateData) {
        if (updateData.isEmpty()) {
            throw new EmptyRequestBodyException();
        }

        // получаем поля дела, которые можно обновлять
        Set<String> taskProperties = new HashSet<>();
        for (Field field : TodoRepo.class.getDeclaredFields()){
            taskProperties.add(field.getName());
        }
        List<String> toRemove = Arrays.asList("id", "todoList", "createdAt", "updatedAt");
        taskProperties.removeAll(toRemove);

        if (taskProperties.stream().noneMatch(updateData::containsKey)){
            throw new UnsupportedFieldPatchException(updateData.keySet());
        }

        // выбираем только те поля, которые нужны
        Set<String> propsToUpdate = new HashSet<>(updateData.keySet())
                .stream()
                .filter(taskProperties::contains)
                .collect(Collectors.toSet());

        return todoRepo.findById(id).map(x -> {
            HashMap<String, String> errors = new HashMap<>();
            propsToUpdate.forEach(prop -> {
                try {
                    // получение сеттера для свойства и, если типы совпадают, вызов сеттера на конкретной таске
                    // если не совпадают, добавляем к ошибкам
                    Class type = EntityTodo.class.getDeclaredField(prop).getType();
                    if (updateData.get(prop) == null){
                        errors.put(prop, type.getSimpleName().toLowerCase());
                    } else {
                        String setterName = "set" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
                        Class typeOfUpdateProp = updateData.get(prop).getClass();
                        if (typeOfUpdateProp.equals(Integer.class)){
                            typeOfUpdateProp = int.class;
                        }
                        Method setter = EntityTodo.class.getMethod(setterName, type);
                        if (!type.equals(typeOfUpdateProp)){
                            errors.put(prop, type.getSimpleName().toLowerCase());
                        } else {
                            setter.invoke(x, updateData.get(prop));
                        }
                    }
                } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | InvocationTargetException e){
                    throw new CustomException(e.getMessage());
                }
            });
            if (!errors.isEmpty()){
                throw new InvalidRequestTypesException(errors);
            }
            return todoRepo.save(x);
        }).orElseThrow(() -> new NotFoundException("Todo", id));
    }

}
