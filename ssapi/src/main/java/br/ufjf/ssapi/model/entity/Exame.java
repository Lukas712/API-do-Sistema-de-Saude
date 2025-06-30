package br.ufjf.ssapi.model.entity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public boolean validaValidade(Date validade) {
        if (validade == null)
            return false;

        LocalDate dataValidade = validade.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate hoje = LocalDate.now();

        return !dataValidade.isBefore(hoje);
    }
}
