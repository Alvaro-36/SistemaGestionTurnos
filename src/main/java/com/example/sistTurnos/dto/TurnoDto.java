package com.example.sistTurnos.dto;

import lombok.Data;

@Data
public class TurnoDto {
    private String fechaHoraInicioTurno;
    private long idCliente;
    private long idTipoTurno;

}
