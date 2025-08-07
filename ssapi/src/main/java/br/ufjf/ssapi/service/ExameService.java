package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Exame;
import br.ufjf.ssapi.model.repository.ExameRepository;


@Service
public class ExameService {

    private final ExameRepository exameRepository;

    public ExameService(ExameRepository exameRepository) {
        this.exameRepository = exameRepository;
    }

    public List<Exame> getExames() {
        return exameRepository.findAll();
    }

    public Optional<Exame> getExame(Long id) {
        return exameRepository.findById(id);
    }

    @Transactional
    public Exame salvar(Exame exame) {
        validar(exame);
        return exameRepository.save(exame);
    }

    @Transactional
    public void excluir(Exame exame) {
        Objects.requireNonNull(exame.getId());
        exameRepository.delete(exame);
    }

    public void validar(Exame exame) throws DefaultException {
        if (!exame.validaValidade()) {
            throw new DefaultException("Validade esta no formato incorreto");
        }
        if(!exame.validaDescricao()) {
            throw new DefaultException("Descrição não pode ser vazia");
        }
        if(!exame.validaLaudo()) {
            throw new DefaultException("Paciente não encontrado");
        }
        if(!exame.validaPaciente()) {
            throw new DefaultException("Paciente não encontrado");
        }
        if(!exame.validaEnfermeiro()) {
            throw new DefaultException("Enfermeiro não encontrado");
        }
    }
}