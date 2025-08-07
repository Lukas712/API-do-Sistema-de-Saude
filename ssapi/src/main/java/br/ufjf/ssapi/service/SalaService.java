package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Sala;
import br.ufjf.ssapi.model.repository.SalaRepository;


@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<Sala> getSalas() {
        return salaRepository.findAll();
    }

    public Optional<Sala> getSala(Long id) {
        return salaRepository.findById(id);
    }

    @Transactional
    public Sala salvar(Sala sala) {
        validar(sala);
        return salaRepository.save(sala);
    }

    @Transactional
    public void excluir(Sala sala) {
        Objects.requireNonNull(sala.getId());
        salaRepository.delete(sala);
    }

    public void validar(Sala sala) throws DefaultException {
        if(!sala.validaEquipamento()) {
            throw new DefaultException("Equipamento inválido");
        }
        if(!sala.validaExame()){
            throw new DefaultException("Exame inválido");
        }
        if(!sala.validaHospital()){
            throw new DefaultException("Hospital inválido");
        }
    }
}