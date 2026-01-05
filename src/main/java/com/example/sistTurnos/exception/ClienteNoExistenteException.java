package com.example.sistTurnos.exception;

public class ClienteNoExistenteException extends RuntimeException {
    public ClienteNoExistenteException(String mensaje) {
        super(mensaje);
    }
}