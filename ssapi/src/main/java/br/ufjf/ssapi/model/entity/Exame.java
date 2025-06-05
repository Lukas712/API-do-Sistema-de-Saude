package br.ufjf.ssapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exame{

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
}
