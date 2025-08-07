package br.ufjf.ssapi.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;




@Entity
public class Medico extends Usuario{
    private String crm;

    @ManyToOne
    private Hospital hospital;

    @OneToOne
    private Especialidade especialidade;

    

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public boolean validaCrm() {
        return crm != null && !crm.isEmpty();
    }

    public boolean validaEspecialidade() {
        return especialidade != null;
    }

    public boolean validaHospital() {
        return hospital != null;
    }
}
