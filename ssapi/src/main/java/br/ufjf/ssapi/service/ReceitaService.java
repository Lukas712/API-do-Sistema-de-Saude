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
public class ReceitaService {

    private final ReceitaRepository ReceitaRepository;

    public ReceitaService(ReceitaRepository ReceitaRepository) {
        this.ReceitaRepository = ReceitaRepository;
    }

    public List<Receita> getReceitas() {
        return ReceitaRepository.findAll();
    }

    public Optional<Receita> getReceita(Long id) {
        return ReceitaRepository.findById(id);
    }

    @Transactional
    public Receita salvar(Receita Receita) {
        validar(Receita);
        return ReceitaRepository.save(Receita);
    }

    @Transactional
    public void excluir(Receita Receita) {
        Objects.requireNonNull(Receita.getId());
        ReceitaRepository.delete(Receita);
    }

    public void validar(Receita Receita) throws PasswordException {
        
    }
}