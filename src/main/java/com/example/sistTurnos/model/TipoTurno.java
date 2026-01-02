package com.example.sistTurnos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoTurno;

    private String nombreTipoTurno;

    private String codTipoTurno;

    private LocalDateTime fechaHoraBajaTT;

    @OneToMany(mappedBy = "tipoTurno", cascade = CascadeType.ALL)
    private List<CaracteristicaTipoTurno> caracteristicas;

    @OneToMany(mappedBy = "tipoTurno")
    private List<Turno> turnos;
}
