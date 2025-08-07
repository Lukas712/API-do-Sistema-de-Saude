package br.ufjf.ssapi.model.entity;

import java.util.Date;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;




@Entity
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;
    private String local;

    public boolean validaNome(){
        if (nome == null || nome.isEmpty())
            return false;

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$";
        return Pattern.matches(regex, nome);
    }

    public boolean validaLocal() {
        if (local == null || local.isEmpty())
            return false;

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9\\s,.-]+$";
        return Pattern.matches(regex, local);
    }

    public boolean validaCNPJ() {
        if (cnpj == null || cnpj.isEmpty())
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
