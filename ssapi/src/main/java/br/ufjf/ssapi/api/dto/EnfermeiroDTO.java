package br.ufjf.ssapi.api.dto;

import java.util.Date;

import org.modelmapper.ModelMapper;

import br.ufjf.ssapi.model.entity.Enfermeiro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EnfermeiroDTO {
    private Long id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private String telefone;
    private String genero;
    
    private Long idHospital;
    private String nomeHospital;

    public static EnfermeiroDTO create(Enfermeiro enfermeiro){
        ModelMapper modelMapper = new ModelMapper();
        EnfermeiroDTO dto = modelMapper.map(enfermeiro, EnfermeiroDTO.class);
         dto.nomeHospital = enfermeiro.getHospital().getNome();
        return dto;
    }
}
