package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.PasswordException;
import br.ufjf.ssapi.model.entity.Sala;
import br.ufjf.ssapi.model.repository.SalaRepository;


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
        if (Sala.getEquipamento() == null || Sala.getEquipamento().isEmpty()) {
            throw new PasswordException("O equipamento não pode ser vazio.");
        }
    }
}