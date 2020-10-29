package com.todo.smart.worldssp.todo.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Представляет класс ошибки, связанной с передачей неверных типов данных
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestTypesException extends RuntimeException {
    @Getter
    private List<String> errors;

    /**
     * Конструктор ошибки
     * @param errors - сообщения о несоответствии типов
     */
    public InvalidRequestTypesException(HashMap<String, String> errors) {
        super("Fields must match the following in 'errors' types:");
        this.errors = errors.entrySet().stream().map(Object::toString).collect(Collectors.toList());
    }
}
