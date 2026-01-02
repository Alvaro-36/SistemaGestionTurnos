package com.example.sistTurnos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class FranjaHorariaExcepcional extends FranjaHoraria {

    private LocalDate fechaDiaExcepcion;
}
