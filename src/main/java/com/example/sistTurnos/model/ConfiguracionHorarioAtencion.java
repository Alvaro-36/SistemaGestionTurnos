package com.example.sistTurnos.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracionHorarioAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConfiguracionHorarioAtencion;

    private LocalDateTime fechaHoraAlta;

    private LocalDateTime fechaHoraBaja;

    @OneToMany(mappedBy = "configuracionHorarioAtencion", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FranjaHorariaNormal> franjasHorarias = new ArrayList<>();
}
