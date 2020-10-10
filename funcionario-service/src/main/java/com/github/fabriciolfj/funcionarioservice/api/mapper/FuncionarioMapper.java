package com.github.fabriciolfj.funcionarioservice.api.mapper;

import com.github.fabriciolfj.funcionarioservice.api.dto.request.FuncionarioRequestDTO;
import com.github.fabriciolfj.funcionarioservice.api.dto.response.FuncionarioResponseDTO;
import com.github.fabriciolfj.funcionarioservice.domain.documents.Funcionario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "departamentoId", target = "departamentoId")
    Funcionario toDocument(final FuncionarioRequestDTO dto);

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "id", target = "id")
    FuncionarioResponseDTO toResponseDTO(final Funcionario funcionario);
}
