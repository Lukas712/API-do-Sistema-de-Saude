package br.ufjf.ssapi.model.repository;

import br.ufjf.ssapi.model.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    // Aqui você pode adicionar métodos personalizados, se necessário
    // Por exemplo:
    // List<Hospital> findByNome(String nome);
    
}
