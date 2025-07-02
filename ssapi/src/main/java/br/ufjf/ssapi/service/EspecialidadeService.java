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

    private final EspecialidadeRepository especialidadeRepository;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository) {
        this.especialidadeRepository = especialidadeRepository;
    }

    public List<Especialidade> getEspecialidades() {
        return especialidadeRepository.findAll();
    }

    public Optional<Especialidade> getEspecialidade(Long id) {
        return especialidadeRepository.findById(id);
    }

    @Transactional
    public Especialidade salvar(Especialidade especialidade) {
        validar(especialidade);
        return especialidadeRepository.save(especialidade);
    }

    @Transactional
    public void excluir(Especialidade especialidade) {
        Objects.requireNonNull(especialidade.getId());
        especialidadeRepository.delete(especialidade);
    }

    public void validar(Especialidade especialidade) throws PasswordException {
        if (especialidade.getNome() == null || especialidade.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
        if (especialidade.getArea() == null || especialidade.getArea().isEmpty()) {
            throw new PasswordException("A área não pode ser vazio.");
        }
    }
}