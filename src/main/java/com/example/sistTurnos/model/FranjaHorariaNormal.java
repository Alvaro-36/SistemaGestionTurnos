package com.example.sistTurnos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class FranjaHorariaNormal extends FranjaHoraria {

    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @ManyToOne
    @JoinColumn(name = "configuracion_horario_id")
    private ConfiguracionHorarioAtencion configuracionHorarioAtencion;
}
