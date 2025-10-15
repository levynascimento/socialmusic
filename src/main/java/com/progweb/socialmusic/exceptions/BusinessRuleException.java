package com.progweb.socialmusic.exceptions;

public class BusinessRuleException extends RuntimeException {

    /**
     * Construtor que aceita uma mensagem.
     * Esta mensagem será retornada para o cliente pelo APIExceptionHandler.
     * * @param message A mensagem de erro da regra de negócio (Ex: "Username já existe!").
     */
    public BusinessRuleException(String message) {
        super(message);
    }
}
