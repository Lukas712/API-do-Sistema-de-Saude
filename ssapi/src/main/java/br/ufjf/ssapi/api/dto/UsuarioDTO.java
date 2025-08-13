package br.ufjf.ssapi.api.dto;

import br.ufjf.ssapi.model.entity.UsuarioLogin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String login;
    private String senha;
    private String senhaRepeticao;
    private String cpf;
    private boolean admin;

    public static UsuarioDTO create(UsuarioLogin usuario) {
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        return dto;
    }
}
