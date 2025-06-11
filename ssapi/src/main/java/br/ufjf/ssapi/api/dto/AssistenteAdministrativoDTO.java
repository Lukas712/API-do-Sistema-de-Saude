package br.ufjf.ssapi.api.dto;

import java.util.Date;

import org.modelmapper.ModelMapper;

import br.ufjf.ssapi.model.entity.AssistenteAdministrativo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AssistenteAdministrativoDTO {
    
    private Long id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private String telefone;
    private String genero;
    private Long idHospital;
    private String nomeHospital;

    public static AssistenteAdministrativoDTO create(AssistenteAdministrativo assistente){
        ModelMapper modelMapper = new ModelMapper();
        AssistenteAdministrativoDTO dto = modelMapper.map(assistente, AssistenteAdministrativoDTO.class);
        dto.nomeHospital = assistente.getHospital().getNome();
        return dto;
    }
}
