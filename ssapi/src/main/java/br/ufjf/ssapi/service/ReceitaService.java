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

    private final ReceitaRepository receitaRepository;

    public ReceitaService(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    public List<Receita> getReceitas() {
        return receitaRepository.findAll();
    }

    public Optional<Receita> getReceita(Long id) {
        return receitaRepository.findById(id);
    }

    @Transactional
    public Receita salvar(Receita receita) {
        validar(receita);
        return receitaRepository.save(receita);
    }

    @Transactional
    public void excluir(Receita receita) {
        Objects.requireNonNull(receita.getId());
        receitaRepository.delete(receita);
    }

    public void validar(Receita receita) throws PasswordException {
        if (receita.getDescricao() == null || receita.getDescricao().isEmpty()) {
            throw new PasswordException("A descricao não pode ser vazia.");
        }
    }
}