package com.example.sistTurnos.exception;

public class ClienteNoExistenteException extends RuntimeException {
    public ClienteNoExistenteException(Long id) {
        super("No se encuentró el cliente "+ id + " en la base de datos");
    }
}