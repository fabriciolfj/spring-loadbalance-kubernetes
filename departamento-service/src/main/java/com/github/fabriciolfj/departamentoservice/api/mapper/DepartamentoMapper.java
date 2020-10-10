package com.github.fabriciolfj.departamentoservice.api.mapper;

import com.github.fabriciolfj.departamentoservice.api.mapper.impl.DepartamentoMapperDecorated;
import com.github.fabriciolfj.departamentoservice.api.request.DepartamentoRequestDTO;
import com.github.fabriciolfj.departamentoservice.api.response.DepartamentoResponseDTO;
import com.github.fabriciolfj.departamentoservice.api.response.FuncionarioResponseDTO;
import com.github.fabriciolfj.departamentoservice.domain.documents.Departamento;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
@DecoratedWith(DepartamentoMapperDecorated.class)
public interface DepartamentoMapper {

    @Mapping(source = "nome", target = "nome")
    Departamento toDocument(final DepartamentoRequestDTO dto);

    @Mapping(source = "departamento.nome", target = "nome")
    @Mapping(source = "departamento.id", target = "id")
    DepartamentoResponseDTO toResponse(final Departamento departamento, final List<FuncionarioResponseDTO> funcionarios);
}
