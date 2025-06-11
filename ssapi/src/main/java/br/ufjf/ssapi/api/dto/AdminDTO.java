package br.ufjf.ssapi.api.dto;

import java.util.Date;

import org.modelmapper.ModelMapper;

import br.ufjf.ssapi.model.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AdminDTO {

    private Long id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private String telefone;
    private String genero;
    private Long idHospital;
    private String nomeHospital;
    
    public static AdminDTO create(Admin admin){
        ModelMapper modelMapper = new ModelMapper();
        AdminDTO dto = modelMapper.map(admin, AdminDTO.class);
        dto.nomeHospital = admin.getHospital().getNome();
        return dto;
    }
}
