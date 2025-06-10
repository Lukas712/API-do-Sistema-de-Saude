package br.ufjf.ssapi.api.dto;

import org.modelmapper.ModelMapper;
import br.ufjf.ssapi.model.entity.Hospital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HospitalDTO {

    private Long id;
    private String nome;
    private String cnpj;
    private String local;

    public static HospitalDTO create(Hospital hospital){
        ModelMapper modelMapper = new ModelMapper();
        HospitalDTO dto = modelMapper.map(hospital, HospitalDTO.class);
        return dto;
    }
}
