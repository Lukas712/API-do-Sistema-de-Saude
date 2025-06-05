package br.ufjf.ssapi.model.entity;

@Entity

public class Medico extends Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String crm;

    @ManyToOne
    private Hospital hospital;

    @OneToOne
    private Especialidade especialidade;
}
