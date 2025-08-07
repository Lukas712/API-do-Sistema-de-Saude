package br.ufjf.ssapi.model.entity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;




@Entity
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String laudo;
    private Date validade;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Enfermeiro enfermeiro;

    public boolean validaValidade() {
        if (validade == null)
            return false;

        LocalDate dataValidade = validade.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate hoje = LocalDate.now();

        return !dataValidade.isBefore(hoje);
    }

    public boolean validaLaudo() {
        if (laudo == null || laudo.isEmpty())
            return false;

        String regex = "^[\\w\\s.,;:!?-]+$";
        return laudo.matches(regex);
    }

    public boolean validaDescricao() {
        if (descricao == null || descricao.isEmpty())
            return false;

        String regex = "^[\\w\\s.,;:!?-]+$";
        return descricao.matches(regex);
    }

    public boolean validaPaciente() {
        return paciente != null;
    }

    public boolean validaEnfermeiro() {
        return enfermeiro != null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLaudo() {
        return laudo;
    }

    public void setLaudo(String laudo) {
        this.laudo = laudo;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Enfermeiro getEnfermeiro() {
        return enfermeiro;
    }

    public void setEnfermeiro(Enfermeiro enfermeiro) {
        this.enfermeiro = enfermeiro;
    }
}
