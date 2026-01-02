package com.example.sistTurnos.service;

import com.example.sistTurnos.dto.CancelarTurnoDto;
import com.example.sistTurnos.dto.TurnoDTOResponse;
import com.example.sistTurnos.dto.TurnoDto;
import com.example.sistTurnos.exception.HorarioNoDisponibleException;
import com.example.sistTurnos.model.JornadaLaboral;
import com.example.sistTurnos.model.TimeRange;
import com.example.sistTurnos.model.TipoTurno;
import com.example.sistTurnos.model.Turno;
import com.example.sistTurnos.repository.JornadaLaboralRepository;
import com.example.sistTurnos.repository.TipoTurnoRepository;
import com.example.sistTurnos.repository.TurnoRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



//  ----- REQUISITOS QUE FALTAN------///
// LA LISTA DE LOS TURNOS DE UNA JORNADA SE TIENE QUE ORDENAR CADA VEZ QUE SE AGREGUE UN NUEVO TURNO PARA PODER COMPROBAR LA DISPONIBILIDAD

@Service
public class TurnoService {
    private final TurnoRepository turnoRepository;
    private final ModelMapper mapper;
    private final TipoTurnoRepository tipoTurnoRepository;

    private final JornadaLaboralRepository jornadaLaboralRepository;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper mapper, TipoTurnoRepository tipoTurnoRepository, JornadaLaboralRepository jornadaLaboralRepository) {
        this.turnoRepository = turnoRepository;
        this.mapper = mapper;

        this.tipoTurnoRepository = tipoTurnoRepository;
        this.jornadaLaboralRepository = jornadaLaboralRepository;
    }

    //Metodos


    public TurnoDTOResponse asignarTurno(@org.jetbrains.annotations.NotNull TurnoDto turnoDto){
        TipoTurno tipoTurno = tipoTurnoRepository.findById(turnoDto.getIdTipoTurno())
                .orElseThrow(() -> new RuntimeException("Tipo de turno no encontrado"));
        Turno turno = mapper.map(turnoDto, Turno.class);
        JornadaLaboral jornadaLaboral = jornadaLaboralRepository.findByFechaJornadaLaboral(turno.getFechaHoraInicioTurno().toLocalDate());
        List<TimeRange> horariosDisponibles = jornadaLaboral.calcularHorariosDisponibles();
        boolean horarioEstaDisponible = false;
        for (TimeRange horario : horariosDisponibles
             ) {
            if (horario.includesDateTimeRange(TimeRange.builder().startTime(turno.getFechaHoraInicioTurno().toLocalTime()).endTime(turno.getFechaHoraFinTurno().toLocalTime()).build())){
                horarioEstaDisponible = true;
            }
        };
        if (horarioEstaDisponible){
            turno.setTipoTurno(tipoTurno);
            turnoRepository.save(turno);
            return mapper.map(turno, TurnoDTOResponse.class);
        }else{
            throw new HorarioNoDisponibleException("El horario seleccionado no está disponible.");
        }
    }


    public Optional<Turno> obtenerPorId(Long idTurno) {
        return turnoRepository.findById(idTurno);
    }

    public CancelarTurnoDto cancelarTurno(TurnoDto turnoDto){
            if (idTurno !=null && obtenerPorId(idTurno) != null){
        Turno turno = obtenerPorId(idTurno).get();

        }

}
}

