package com.example.sistTurnos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Turno implements Comparable<Turno> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurno;

    private LocalDateTime fechaHoraInicioTurno;

    private LocalDateTime fechaHoraFinTurno;

    private LocalDateTime fechaHoraReserva;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tipo_turno_id")
    private TipoTurno tipoTurno;

    @ManyToOne
    @JoinColumn(name = "estado_turno_id")
    private EstadoTurno estadoTurno;

    @ManyToOne
    @JoinColumn(name = "jornada_id")
    private JornadaLaboral jornadaLaboral;

    @Override
    public int compareTo(Turno o) {
        return this.getFechaHoraInicioTurno().compareTo(o.getFechaHoraInicioTurno());
    }
}
