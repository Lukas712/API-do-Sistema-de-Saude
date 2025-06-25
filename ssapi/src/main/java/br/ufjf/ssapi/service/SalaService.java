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
public class SalaService {

    private final SalaRepository SalaRepository;

    public SalaService(SalaRepository SalaRepository) {
        this.SalaRepository = SalaRepository;
    }

    public List<Sala> getSalas() {
        return SalaRepository.findAll();
    }

    public Optional<Sala> getSala(Long id) {
        return SalaRepository.findById(id);
    }

    @Transactional
    public Sala salvar(Sala Sala) {
        validar(Sala);
        return SalaRepository.save(Sala);
    }

    @Transactional
    public void excluir(Sala Sala) {
        Objects.requireNonNull(Sala.getId());
        SalaRepository.delete(Sala);
    }

    public void validar(Sala Sala) throws PasswordException {
        
    }
}