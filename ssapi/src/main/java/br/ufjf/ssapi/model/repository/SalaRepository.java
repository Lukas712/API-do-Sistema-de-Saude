package br.ufjf.ssapi.model.repository;

import br.ufjf.ssapi.model.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    // Aqui você pode adicionar métodos personalizados, se necessário.
    // Por exemplo, para encontrar salas por nome ou capacidade:
    // List<Sala> findByNome(String nome);
    // List<Sala> findByCapacidadeGreaterThanEqual(int capacidade);

}
