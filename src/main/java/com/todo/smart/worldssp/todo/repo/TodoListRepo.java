package com.todo.smart.worldssp.todo.repo;

import com.todo.smart.worldssp.todo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import java.util.*;


public interface TodoListRepo extends JpaRepository<ListEntityTodo, UUID> {



    Optional<ListEntityTodo> findById(UUID listId);
}
