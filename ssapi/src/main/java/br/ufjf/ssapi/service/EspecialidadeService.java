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
public class EspecialidadeService {

    private final EspecialidadeRepository EspecialidadeRepository;

    public EspecialidadeService(EspecialidadeRepository EspecialidadeRepository) {
        this.EspecialidadeRepository = EspecialidadeRepository;
    }

    public List<Especialidade> getEspecialidades() {
        return EspecialidadeRepository.findAll();
    }

    public Optional<Especialidade> getEspecialidade(Long id) {
        return EspecialidadeRepository.findById(id);
    }

    @Transactional
    public Especialidade salvar(Especialidade Especialidade) {
        validar(Especialidade);
        return EspecialidadeRepository.save(Especialidade);
    }

    @Transactional
    public void excluir(Especialidade Especialidade) {
        Objects.requireNonNull(Especialidade.getId());
        EspecialidadeRepository.delete(Especialidade);
    }

    public void validar(Especialidade Especialidade) throws PasswordException {
        if (Especialidade.getNome() == null || Especialidade.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
    }
}