package com.example.sistTurnos.repository;

import com.example.sistTurnos.model.JornadaLaboral;
import com.example.sistTurnos.model.TipoTurno;
import com.example.sistTurnos.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TipoTurnoRepository extends JpaRepository<TipoTurno, Long> {

}
