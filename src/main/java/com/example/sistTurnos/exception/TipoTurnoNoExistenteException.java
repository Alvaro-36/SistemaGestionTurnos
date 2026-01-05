package com.example.sistTurnos.exception;

public class TipoTurnoNoExistenteException extends RuntimeException {
    public TipoTurnoNoExistenteException(String mensaje) {
        super(mensaje);
    }
}