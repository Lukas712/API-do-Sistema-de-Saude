package br.ufjf.ssapi.model.repository;

import br.ufjf.ssapi.model.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // Aqui você pode adicionar métodos personalizados, se necessário.
    // Por exemplo, para buscar médicos por especialidade ou nome.
    // List<Medico> findByEspecialidade(String especialidade);
    // List<Medico> findByNomeContaining(String nome);
    
}
