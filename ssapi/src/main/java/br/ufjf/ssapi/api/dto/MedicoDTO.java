package br.ufjf.ssapi.api.dto;

import java.util.Date;

import org.modelmapper.ModelMapper;

import br.ufjf.ssapi.model.entity.Medico;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MedicoDTO {
    private Long id;
    private String crm;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private String telefone;
    private String genero;
    
    private Long idHospital;
    private String nomeHospital;
    private Long idEspecialidade;
    private String nomeEspecialidade;

    public static MedicoDTO create(Medico medico){
        ModelMapper modelMapper = new ModelMapper();
        MedicoDTO dto = modelMapper.map(medico, MedicoDTO.class);
        dto.nomeHospital = medico.getHospital().getNome();
        dto.nomeEspecialidade = medico.getEspecialidade().getNome();
        return dto;
    }
}
