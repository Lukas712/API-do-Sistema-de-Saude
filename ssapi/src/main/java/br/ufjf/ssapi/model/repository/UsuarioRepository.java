package br.ufjf.ssapi.model.repository;

import br.ufjf.ssapi.model.entity.UsuarioLogin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioLogin, Long> {

    Optional<UsuarioLogin> findByLogin(String login);
}

