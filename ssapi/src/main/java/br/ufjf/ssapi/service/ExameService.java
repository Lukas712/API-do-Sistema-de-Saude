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
public class ExameService {

    private final ExameRepository ExameRepository;

    public ExameService(ExameRepository ExameRepository) {
        this.ExameRepository = ExameRepository;
    }

    public List<Exame> getExames() {
        return ExameRepository.findAll();
    }

    public Optional<Exame> getExame(Long id) {
        return ExameRepository.findById(id);
    }

    @Transactional
    public Exame salvar(Exame Exame) {
        validar(Exame);
        return ExameRepository.save(Exame);
    }

    @Transactional
    public void excluir(Exame Exame) {
        Objects.requireNonNull(Exame.getId());
        ExameRepository.delete(Exame);
    }

    public void validar(Exame Exame) throws PasswordException {
        if (Exame.validaValidade(Exame.getValidade())) {
            throw new PasswordException("Validade esta no formato incorreto");
        }
        if (Exame.getDescricao() == null || Exame.getDescricao().isEmpty()) {
            throw new PasswordException("A descricao não pode ser vazia.");
        }
        if (Exame.getLaudo() == null || Exame.getLaudo().isEmpty()) {
            throw new PasswordException("O laudo não pode ser vazio.");
        }
    }
}