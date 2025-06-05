package br.ufjf.ssapi.model.entity;

@Entity

public class Paciente extends Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
