package br.ufjf.ssapi.model.repository;

import br.ufjf.ssapi.model.entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    // Aqui você pode adicionar métodos personalizados, se necessário.
    // Por exemplo, para buscar medicamentos por nome:
    // List<Medicamento> findByNomeContainingIgnoreCase(String nome);
    
}
