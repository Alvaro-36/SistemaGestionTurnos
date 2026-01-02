package com.example.sistTurnos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaracteristicaTipoTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCaracteristicaTipoTurno;

    private Boolean requiereSena;

    private LocalTime duracionHoras;

    private Integer capacidadSimultanea;

    private Integer intervaloReservaMinutos;

    @ManyToOne
    @JoinColumn(name = "tipo_turno_id")
    private TipoTurno tipoTurno;
}
