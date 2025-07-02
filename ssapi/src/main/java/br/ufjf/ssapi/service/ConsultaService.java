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
public class ConsultaService {

    private final ConsultaRepository consultaRepository;

    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public List<Consulta> getConsultas() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> getConsulta(Long id) {
        return consultaRepository.findById(id);
    }

    @Transactional
    public Consulta salvar(Consulta consulta) {
        validar(consulta);
        return consultaRepository.save(consulta);
    }

    @Transactional
    public void excluir(Consulta consulta) {
        Objects.requireNonNull(consulta.getId());
        consultaRepository.delete(consulta);
    }

    public void validar(Consulta consulta) throws PasswordException {
        if (consulta.getDescricao() == null || consulta.getDescricao().isEmpty()) {
            throw new PasswordException("A consulta não pode ser vazio.");
        }
    }
}