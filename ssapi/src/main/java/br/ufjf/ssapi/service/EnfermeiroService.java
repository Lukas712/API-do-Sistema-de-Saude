package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

import org.springframework.stereotype.Service;
import br.ufjf.ssapi.model.repository.*;
import br.ufjf.ssapi.model.entity.*;
import br.ufjf.ssapi.exception.PasswordException;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EnfermeiroService {

    private final EnfermeiroRepository EnfermeiroRepository;

    public EnfermeiroService(EnfermeiroRepository EnfermeiroRepository) {
        this.EnfermeiroRepository = EnfermeiroRepository;
    }

    public List<Enfermeiro> getEnfermeiros() {
        return EnfermeiroRepository.findAll();
    }

    public Optional<Enfermeiro> getEnfermeiro(Long id) {
        return EnfermeiroRepository.findById(id);
    }

    @Transactional
    public Enfermeiro salvar(Enfermeiro Enfermeiro) {
        validar(Enfermeiro);
        return EnfermeiroRepository.save(Enfermeiro);
    }

    @Transactional
    public void excluir(Enfermeiro Enfermeiro) {
        Objects.requireNonNull(Enfermeiro.getId());
        EnfermeiroRepository.delete(Enfermeiro);
    }

    public void validar(Enfermeiro Enfermeiro) throws PasswordException {
        if (Enfermeiro.getNome() == null || Enfermeiro.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
    }
}