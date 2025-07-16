package br.ufjf.ssapi.model.entity;

import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;





@Entity
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String area;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean validaNome(String nome) {
        if (nome == null || nome.isEmpty())
            return false;

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$";
        return Pattern.matches(regex, nome);
    }

    public boolean validaArea(String area) {
        if (area == null || area.isEmpty())
            return false;

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$";
        return Pattern.matches(regex, area);
    }
}
