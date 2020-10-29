package com.todo.smart.worldssp.todo.errors;

/**
 * Представляет класс для ошибок, вызванных ошибками программы
 */
public class CustomException extends RuntimeException {
    /**
     * Конструктор ошибки
     * @param message сообщение, описывающее ошибку
     */
    public CustomException(String message){
        super(message);
    }
}
