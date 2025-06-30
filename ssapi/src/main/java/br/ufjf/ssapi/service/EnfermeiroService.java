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
        if (!Enfermeiro.validaEmail(Enfermeiro.getEmail())) {
            throw new PasswordException("O email está no formato incorreto.");
        }
        if (!Enfermeiro.validaCPF(Enfermeiro.getCpf())) {
            throw new PasswordException("O cpf está no formato incorreto.");
        }
        if (!Enfermeiro.validaDataNascimento(Enfermeiro.getDataNascimento())) {
            throw new PasswordException("A data está no formato incorreto.");
        }
        if (!Enfermeiro.validaTelefone(Enfermeiro.getTelefone())) {
            throw new PasswordException("O telefone está no formato incorreto.");
        }
    }
}