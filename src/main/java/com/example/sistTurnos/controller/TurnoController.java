package com.example.sistTurnos.controller;

import com.example.sistTurnos.dto.TurnoDTOResponse;
import com.example.sistTurnos.service.TurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.sistTurnos.dto.TurnoDto;
import com.example.sistTurnos.exception.*;
import com.example.sistTurnos.model.TipoTurno;


@RestController
@RequestMapping("/Turno")
@Tag(name = "Turnos", description = "API para gestión de turnos")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @Operation(
        summary = "Agendar un nuevo turno",
        description = "Crea un nuevo turno verificando disponibilidad horaria en la jornada laboral correspondiente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Turno agendado exitosamente",
            content = @Content(schema = @Schema(implementation = TurnoDTOResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos del turno inválidos"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tipo de turno no encontrado"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Horario no disponible"
        )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos del turno a agendar",
        required = true,
        content = @Content(
            schema = @Schema(implementation = TurnoDto.class),
            examples = @ExampleObject(
                name = "Ejemplo de turno",
                summary = "Turno de ejemplo para las 10:00",
                value = """
                    {
                      "fechaHoraInicioTurno": "2026-01-05T10:00:00",
                      "idCliente": 1,
                      "idTipoTurno": 1
                    }
                    """
            )
        )
    )
    @PostMapping("/agendar")
    public ResponseEntity<TurnoDTOResponse> agendarTurno(@Valid @RequestBody TurnoDto nuevoTurno) {
        TurnoDTOResponse turnoAgendado = turnoService.asignarTurno(nuevoTurno);
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoAgendado);
    }

    @ExceptionHandler(HorarioNoDisponibleException.class)
    public ResponseEntity<com.example.sistTurnos.dto.ErrorResponse> handleHorarioNoDisponible(HorarioNoDisponibleException ex) {
        // Devuelve un estado HTTP 409 (Conflict) y el mensaje de la excepción en JSON
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new com.example.sistTurnos.dto.ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ClienteNoExistenteException.class)
    public ResponseEntity<com.example.sistTurnos.dto.ErrorResponse> handleClienteNoExistente(ClienteNoExistenteException ex) {
        // Devuelve un estado HTTP 409 (Conflict) y el mensaje de la excepción en JSON
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new com.example.sistTurnos.dto.ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(TipoTurnoNoExistenteException.class)
    public ResponseEntity<com.example.sistTurnos.dto.ErrorResponse> handleTipoTurnoNoExistente(TipoTurnoNoExistenteException ex) {
        // Devuelve un estado HTTP 409 (Conflict) y el mensaje de la excepción en JSON
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new com.example.sistTurnos.dto.ErrorResponse(ex.getMessage()));
    }
}
