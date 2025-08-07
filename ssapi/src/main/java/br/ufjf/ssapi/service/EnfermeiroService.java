package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Enfermeiro;
import br.ufjf.ssapi.model.repository.EnfermeiroRepository;


@Service
public class EnfermeiroService {

    private final EnfermeiroRepository enfermeiroRepository;

    public EnfermeiroService(EnfermeiroRepository enfermeiroRepository) {
        this.enfermeiroRepository = enfermeiroRepository;
    }

    public List<Enfermeiro> getEnfermeiros() {
        return enfermeiroRepository.findAll();
    }

    public Optional<Enfermeiro> getEnfermeiro(Long id) {
        return enfermeiroRepository.findById(id);
    }

    @Transactional
    public Enfermeiro salvar(Enfermeiro enfermeiro) {
        validar(enfermeiro);
        return enfermeiroRepository.save(enfermeiro);
    }

    @Transactional
    public void excluir(Enfermeiro enfermeiro) {
        Objects.requireNonNull(enfermeiro.getId());
        enfermeiroRepository.delete(enfermeiro);
    }

    public void validar(Enfermeiro enfermeiro) throws DefaultException {
        if (!enfermeiro.validaNome()) {
            throw new DefaultException("O nome não pode ser vazio.");
        }
        if (!enfermeiro.validaEmail()) {
            throw new DefaultException("O email está no formato incorreto.");
        }
        if (!enfermeiro.validaCPF()) {
            throw new DefaultException("O cpf está no formato incorreto.");
        }
        if (!enfermeiro.validaDataNascimento()) {
            throw new DefaultException("A data está no formato incorreto.");
        }
        if (!enfermeiro.validaTelefone()) {
            throw new DefaultException("O telefone está no formato incorreto.");
        }
        if(!enfermeiro.validaHospital()){
            throw new DefaultException("Hospital não encontrado");
        }
    }
}