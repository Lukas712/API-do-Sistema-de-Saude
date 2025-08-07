package br.ufjf.ssapi.model.entity;

import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;




@Entity
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    private Receita receita;

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

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public boolean validaNome(){
        if (nome == null || nome.isEmpty())
            return false;

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$";
        return Pattern.matches(regex, nome);
    }

    public boolean validaReceita() {
        return receita != null;
    }




}
