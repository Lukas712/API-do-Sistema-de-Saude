package br.ufjf.ssapi.model.repository;

import java.util.List;

import br.ufjf.ssapi.model.entity.Medicamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    List<Medicamento> findByReceitaId(Long receitaId);
    
}
