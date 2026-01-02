package com.example.sistTurnos.repository;

import com.example.sistTurnos.model.JornadaLaboral;
import com.example.sistTurnos.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    public List<Turno> findByJornadaLaboral(JornadaLaboral jornadaLaboral);
}
