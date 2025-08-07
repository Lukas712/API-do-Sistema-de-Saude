package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

import org.springframework.stereotype.Service;
import br.ufjf.ssapi.model.repository.*;
import br.ufjf.ssapi.model.entity.*;
import br.ufjf.ssapi.exception.DefaultException;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentoRepository.findAll();
    }

    public Optional<Medicamento> getMedicamento(Long id) {
        return medicamentoRepository.findById(id);
    }

    @Transactional
    public Medicamento salvar(Medicamento medicamento) {
        validar(medicamento);
        return medicamentoRepository.save(medicamento);
    }

    @Transactional
    public void excluir(Medicamento medicamento) {
        Objects.requireNonNull(medicamento.getId());
        medicamentoRepository.delete(medicamento);
    }

    public void validar(Medicamento medicamento) throws DefaultException {
        if(!medicamento.validaNome()){
            throw new DefaultException("Nome inválido");
        }
        if(!medicamento.validaReceita()){
            throw new DefaultException("Receita inválida");
        }
    }
}