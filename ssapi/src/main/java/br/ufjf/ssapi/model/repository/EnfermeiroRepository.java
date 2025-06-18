package br.ufjf.ssapi.model.repository;

import br.ufjf.ssapi.model.entity.Enfermeiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnfermeiroRepository extends JpaRepository<Enfermeiro, Long> {

    // Métodos adicionais de consulta podem ser definidos aqui, se necessário.
    
}
