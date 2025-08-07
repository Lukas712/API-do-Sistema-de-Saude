package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

import org.springframework.stereotype.Service;
import br.ufjf.ssapi.model.repository.*;
import br.ufjf.ssapi.model.entity.*;
import br.ufjf.ssapi.exception.DefaultException;
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

    public void validar(Especialidade especialidade) throws DefaultException {
        if (!especialidade.validaNome()){
            throw new DefaultException("O nome da especialidade não pode ser vazio.");
        }
        if (!especialidade.validaArea()) {
            throw new DefaultException("A área da especialidade não pode ser vazia.");
        }
        
    }
}