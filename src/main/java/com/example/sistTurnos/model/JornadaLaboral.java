package com.example.sistTurnos.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JornadaLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJornadaLaboral;

    @Column(unique = true)
    private LocalDate fechaJornadaLaboral;

    @ManyToMany
    @JoinTable(
            name = "jornada_franja_horaria",
            joinColumns = @JoinColumn(name = "jornada_id"),
            inverseJoinColumns = @JoinColumn(name = "franja_horaria_id")
    )
    @Builder.Default
    private List<FranjaHoraria> franjasHorarias = new ArrayList<>();

    @OneToMany(mappedBy = "jornadaLaboral", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Turno> turnosJornada = new ArrayList<>();

    public List<TimeRange> calcularHorariosDisponibles() {
        List<TimeRange> horariosDisponibles = new ArrayList<>();

        turnosJornada.sort((t1, t2) -> t1.getFechaHoraInicioTurno().compareTo(t2.getFechaHoraInicioTurno()));

        for (FranjaHoraria franja : franjasHorarias) {
            LocalTime inicioFranja = franja.getHoraAperturaPeriodo();
            LocalTime finFranja = franja.getHoraCierrePeriodo();


            LocalTime ultimoFinOcupado = inicioFranja;

            for (Turno turno : turnosJornada) {
                LocalTime inicioTurno = turno.getFechaHoraInicioTurno().toLocalTime();
                LocalTime finTurno = turno.getFechaHoraFinTurno().toLocalTime();


                if (finTurno.isBefore(inicioFranja)) {
                    continue;
                }

                if (inicioTurno.isAfter(finFranja)) {
                    break;
                }

                //Cuando hay turnos fuera del horario (no deberia pasar)
                LocalTime inicioEfectivo = inicioTurno.isBefore(inicioFranja) ? inicioFranja : inicioTurno;
                LocalTime finEfectivo = finTurno.isAfter(finFranja) ? finFranja : finTurno;

                //Detectar espacio disponible
                if (ChronoUnit.MINUTES.between(ultimoFinOcupado, inicioEfectivo) > 0) {
                    horariosDisponibles.add(TimeRange.builder()
                            .startTime(ultimoFinOcupado)
                            .endTime(inicioEfectivo)
                            .build());
                }

                // Avanzamos el puntero al final de este turno (si es que avanza)
                if (finEfectivo.isAfter(ultimoFinOcupado)) {
                    ultimoFinOcupado = finEfectivo;
                }
            }

            //espacio final
            if (ChronoUnit.MINUTES.between(ultimoFinOcupado, finFranja) > 0) {
                horariosDisponibles.add(TimeRange.builder()
                        .startTime(ultimoFinOcupado)
                        .endTime(finFranja)
                        .build());
            }
        }

        return horariosDisponibles;
    }
}
