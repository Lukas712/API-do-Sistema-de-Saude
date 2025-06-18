package br.ufjf.ssapi.model.repository;

import br.ufjf.ssapi.model.entity.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    // Aqui você pode adicionar métodos personalizados de consulta, se necessário.
    // Por exemplo:
    // List<Receita> findByPacienteId(Long pacienteId);
    
}
