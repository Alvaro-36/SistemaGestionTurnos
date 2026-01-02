package com.example.sistTurnos.exception;

public class HorarioNoDisponibleException extends RuntimeException {
    public HorarioNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}