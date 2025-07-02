package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.PasswordException;
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

    public void validar(Enfermeiro enfermeiro) throws PasswordException {
        if (enfermeiro.getNome() == null || enfermeiro.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
        if (!enfermeiro.validaEmail(enfermeiro.getEmail())) {
            throw new PasswordException("O email está no formato incorreto.");
        }
        if (!enfermeiro.validaCPF(enfermeiro.getCpf())) {
            throw new PasswordException("O cpf está no formato incorreto.");
        }
        if (!enfermeiro.validaDataNascimento(enfermeiro.getDataNascimento())) {
            throw new PasswordException("A data está no formato incorreto.");
        }
        if (!enfermeiro.validaTelefone(enfermeiro.getTelefone())) {
            throw new PasswordException("O telefone está no formato incorreto.");
        }
    }
}