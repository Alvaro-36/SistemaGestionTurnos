package com.example.sistTurnos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoTurno;

    private String nombreEstadoTurno;

    @OneToMany(mappedBy = "estadoTurno")
    private List<Turno> turnos;
}
