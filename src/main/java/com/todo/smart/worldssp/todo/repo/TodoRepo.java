package com.todo.smart.worldssp.todo.repo;

import com.todo.smart.worldssp.todo.entity.EntityTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import java.util.UUID;


public interface TodoRepo extends JpaRepository<EntityTodo, UUID> {

}
