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

    private final ConsultaRepository ConsultaRepository;

    public ConsultaService(ConsultaRepository ConsultaRepository) {
        this.ConsultaRepository = ConsultaRepository;
    }

    public List<Consulta> getConsultas() {
        return ConsultaRepository.findAll();
    }

    public Optional<Consulta> getConsulta(Long id) {
        return ConsultaRepository.findById(id);
    }

    @Transactional
    public Consulta salvar(Consulta Consulta) {
        validar(Consulta);
        return ConsultaRepository.save(Consulta);
    }

    @Transactional
    public void excluir(Consulta Consulta) {
        Objects.requireNonNull(Consulta.getId());
        ConsultaRepository.delete(Consulta);
    }

    public void validar(Consulta Consulta) throws PasswordException {
        if (Consulta.getDescricao() == null || Consulta.getDescricao().isEmpty()) {
            throw new PasswordException("A consulta não pode ser vazio.");
        }
    }
}