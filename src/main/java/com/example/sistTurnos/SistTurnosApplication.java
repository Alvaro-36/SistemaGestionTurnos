package com.example.sistTurnos;

import com.example.sistTurnos.model.*;
import com.example.sistTurnos.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@EntityScan(basePackages = "com.example.sistTurnos.model")
@SpringBootApplication
public class SistTurnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistTurnosApplication.class, args);

		System.out.println("Hola Mundo");
	}

	@Bean
	CommandLineRunner initData(ClienteRepository clienteRepository,
							   TipoTurnoRepository tipoTurnoRepository,
							   JornadaLaboralRepository jornadaLaboralRepository,
							   FranjaHorariaRepository franjaHorariaRepository,
							   CaracteristicaTipoTurnoRepository caracteristicaRepository) {
		return args -> {
			// Crear Cliente de prueba (ID 1)
			if (clienteRepository.count() == 0) {
				Cliente cliente = Cliente.builder()
						.nombre("Juan Pérez")
						.nroCliente("C001")
						.nroTelefono("1234567890")
						.email("juan.perez@email.com")
						.direccion("Calle Falsa 123")
						.comentarios("Cliente de prueba")
						.build();
				clienteRepository.save(cliente);
				System.out.println("Cliente creado: " + cliente.getIdCliente());
			}

			// Crear TipoTurno de prueba (ID 1)
			if (tipoTurnoRepository.count() == 0) {
				TipoTurno tipoTurno = TipoTurno.builder()
						.nombreTipoTurno("Consulta General")
						.codTipoTurno("CG01")
						.build();
				tipoTurnoRepository.save(tipoTurno);

				// Crear CaracteristicaTipoTurno para el TipoTurno (duración de 1 hora)
				CaracteristicaTipoTurno caracteristica = CaracteristicaTipoTurno.builder()
						.duracionHoras(LocalTime.of(1, 0))  // 1 hora de duración
						.capacidadSimultanea(1)
						.intervaloReservaMinutos(30)
						.requiereSena(false)
						.tipoTurno(tipoTurno)
						.build();
				caracteristicaRepository.save(caracteristica);
				System.out.println("TipoTurno creado: " + tipoTurno.getIdTipoTurno());
			}

			// Crear JornadaLaboral para 2026-01-05 (fecha del ejemplo Swagger)
			LocalDate fecha = LocalDate.of(2026, 1, 5);
			if (jornadaLaboralRepository.findByFechaJornadaLaboral(fecha) == null) {
				// Crear FranjaHoraria de 08:00 a 18:00 y guardarla primero
				FranjaHorariaNormal franjaHoraria = FranjaHorariaNormal.builder()
						.horaAperturaPeriodo(LocalTime.of(8, 0))
						.horaCierrePeriodo(LocalTime.of(18, 0))
						.diaSemana(DiaSemana.LUNES)
						.build();
				franjaHorariaRepository.save(franjaHoraria);

				JornadaLaboral jornadaLaboral = JornadaLaboral.builder()
						.fechaJornadaLaboral(fecha)
						.franjasHorarias(List.of(franjaHoraria))
						.build();
				jornadaLaboralRepository.save(jornadaLaboral);
				System.out.println("JornadaLaboral creada: " + jornadaLaboral.getFechaJornadaLaboral());
			}

			System.out.println("=== Datos de prueba verificados/cargados ===");
		};
	}

}
