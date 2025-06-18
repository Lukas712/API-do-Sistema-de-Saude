package br.ufjf.ssapi.model.repository;

import br.ufjf.ssapi.model.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // Métodos adicionais de consulta podem ser definidos aqui, se necessário.
    // Por exemplo, você pode adicionar métodos para buscar pacientes por nome, CPF, etc.
    
}
