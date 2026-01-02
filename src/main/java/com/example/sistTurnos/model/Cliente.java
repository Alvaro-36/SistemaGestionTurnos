package com.example.sistTurnos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String nroCliente;

    private String nombre;

    private String nroTelefono;

    private String email;

    private String direccion;

    private String comentarios;

    @OneToMany(mappedBy = "cliente")
    private List<Turno> turnos;
}
