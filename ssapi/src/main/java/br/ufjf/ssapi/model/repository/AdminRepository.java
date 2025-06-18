package br.ufjf.ssapi.model.repository;

import br.ufjf.ssapi.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    
}
