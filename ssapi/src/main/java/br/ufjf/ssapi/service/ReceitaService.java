package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.PasswordException;
import br.ufjf.ssapi.model.entity.Receita;
import br.ufjf.ssapi.model.repository.ReceitaRepository;


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
        if (Receita.getDescricao() == null || Receita.getDescricao().isEmpty()) {
            throw new PasswordException("A descricao não pode ser vazia.");
        }
    }
}