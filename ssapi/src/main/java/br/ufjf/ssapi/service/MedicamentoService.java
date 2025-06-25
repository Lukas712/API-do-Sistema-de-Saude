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
public class MedicamentoService {

    private final MedicamentoRepository MedicamentoRepository;

    public MedicamentoService(MedicamentoRepository MedicamentoRepository) {
        this.MedicamentoRepository = MedicamentoRepository;
    }

    public List<Medicamento> getMedicamentos() {
        return MedicamentoRepository.findAll();
    }

    public Optional<Medicamento> getMedicamento(Long id) {
        return MedicamentoRepository.findById(id);
    }

    @Transactional
    public Medicamento salvar(Medicamento Medicamento) {
        validar(Medicamento);
        return MedicamentoRepository.save(Medicamento);
    }

    @Transactional
    public void excluir(Medicamento Medicamento) {
        Objects.requireNonNull(Medicamento.getId());
        MedicamentoRepository.delete(Medicamento);
    }

    public void validar(Medicamento Medicamento) throws PasswordException {
        if (Medicamento.getNome() == null || Medicamento.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
    }
}