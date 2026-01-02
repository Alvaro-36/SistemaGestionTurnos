package com.example.sistTurnos.repository;

import com.example.sistTurnos.model.JornadaLaboral;
import com.example.sistTurnos.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JornadaLaboralRepository extends JpaRepository<JornadaLaboral, Long> {
    public JornadaLaboral findByFechaJornadaLaboral(LocalDate fecha);
}
