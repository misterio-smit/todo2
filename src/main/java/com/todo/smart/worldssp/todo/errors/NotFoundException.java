package com.todo.smart.worldssp.todo.errors;

import java.util.UUID;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String entityName, UUID id){
        super(entityName + "id not found: " + id.toString());
    }
}
