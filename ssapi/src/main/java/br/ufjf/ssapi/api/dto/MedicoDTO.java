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

    public static MedicoDTO create(Medico medico){
        ModelMapper modelMapper = new ModelMapper();
        MedicoDTO dto = modelMapper.map(medico, MedicoDTO.class);
        return dto;
    }
}
