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
public class MedicoService {

    private final MedicoRepository MedicoRepository;

    public MedicoService(MedicoRepository MedicoRepository) {
        this.MedicoRepository = MedicoRepository;
    }

    public List<Medico> getMedicos() {
        return MedicoRepository.findAll();
    }

    public Optional<Medico> getMedico(Long id) {
        return MedicoRepository.findById(id);
    }

    @Transactional
    public Medico salvar(Medico Medico) {
        validar(Medico);
        return MedicoRepository.save(Medico);
    }

    @Transactional
    public void excluir(Medico Medico) {
        Objects.requireNonNull(Medico.getId());
        MedicoRepository.delete(Medico);
    }

    public void validar(Medico Medico) throws PasswordException {
        if (Medico.getNome() == null || Medico.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
    }
}