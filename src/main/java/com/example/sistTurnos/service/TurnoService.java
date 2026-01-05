package com.example.sistTurnos.service;

import com.example.sistTurnos.dto.CancelarTurnoDto;
import com.example.sistTurnos.dto.TurnoDTOResponse;
import com.example.sistTurnos.dto.TurnoDto;
import com.example.sistTurnos.exception.HorarioNoDisponibleException;
import com.example.sistTurnos.model.JornadaLaboral;
import com.example.sistTurnos.model.TimeRange;
import com.example.sistTurnos.model.TipoTurno;
import com.example.sistTurnos.model.Turno;
import com.example.sistTurnos.model.*;
import com.example.sistTurnos.repository.ClienteRepository;
import com.example.sistTurnos.repository.JornadaLaboralRepository;
import com.example.sistTurnos.repository.TipoTurnoRepository;
import com.example.sistTurnos.repository.TurnoRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final ClienteRepository clienteRepository;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper mapper, TipoTurnoRepository tipoTurnoRepository, JornadaLaboralRepository jornadaLaboralRepository, ClienteRepository clienteRepository) {
        this.turnoRepository = turnoRepository;
        this.mapper = mapper;
        this.tipoTurnoRepository = tipoTurnoRepository;
        this.jornadaLaboralRepository = jornadaLaboralRepository;
        this.clienteRepository = clienteRepository;
    }

    //Metodos


    public TurnoDTOResponse asignarTurno(@org.jetbrains.annotations.NotNull TurnoDto turnoDto){
        //Busacar tipo de turno en BD
        TipoTurno tipoTurno = tipoTurnoRepository.findById(turnoDto.getIdTipoTurno())
                .orElseThrow(() -> new RuntimeException("Tipo de turno no encontrado"));

        //Buscar Cliente por id
        Cliente clienteTurno = clienteRepository.findById(turnoDto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        
        // Convertir el String de fechaHoraInicioTurno a LocalDateTime
        LocalDateTime fechaHoraInicio = LocalDateTime.parse(turnoDto.getFechaHoraInicioTurno());
        
        // Calcular fechaHoraFinTurno sumando la duración del tipo de turno
        LocalDateTime fechaHoraFinTurnoCalculada = fechaHoraInicio
                .plusHours(tipoTurno.getCaracteristicas().get(0).getDuracionHoras().getHour())
                .plusMinutes(tipoTurno.getCaracteristicas().get(0).getDuracionHoras().getMinute())
                .plusSeconds(tipoTurno.getCaracteristicas().get(0).getDuracionHoras().getSecond());
        
        Turno turno = Turno.builder()
                .fechaHoraInicioTurno(fechaHoraInicio)
                .fechaHoraFinTurno(fechaHoraFinTurnoCalculada)
                .fechaHoraReserva(LocalDate.now().atStartOfDay())
                .build();  
   //     Turno turno = mapper.map(turnoDto, Turno.class);
        turno.setCliente(clienteTurno);
        turno.setTipoTurno(tipoTurno);
        JornadaLaboral jornadaLaboral = jornadaLaboralRepository.findByFechaJornadaLaboral(fechaHoraInicio.toLocalDate());
        
        
        //DEBUG
        System.out.println("Jornada laboral encontrada:");
        System.out.println(jornadaLaboral.toString());
        
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
        Long idTurno = turnoDto.getIdTurno();
        if (idTurno != null && obtenerPorId(idTurno).isPresent()){
            Turno turno = obtenerPorId(idTurno).get();
            // TODO: Implementar lógica de cancelación
        }
        return null;
    }
}

