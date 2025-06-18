package br.ufjf.ssapi.model.repository;

import br.ufjf.ssapi.model.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    
}
