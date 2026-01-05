package com.example.sistTurnos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnoDTOResponse {
    private Long idTurno;
    private String fechaHoraInicioTurno;
    private String fechaHoraFinTurno;
    private String nombreCliente;
    private String nombreTipoTurno;
    private String estadoTurno;
}
