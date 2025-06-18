package br.ufjf.ssapi.model.repository;

import br.ufjf.ssapi.model.entity.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    
}
