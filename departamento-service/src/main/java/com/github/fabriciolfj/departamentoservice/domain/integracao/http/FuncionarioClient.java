package com.github.fabriciolfj.departamentoservice.domain.integracao.http;

import com.github.fabriciolfj.departamentoservice.api.response.FuncionarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "funcionario")
public interface FuncionarioClient {

    @GetMapping("/departamento/{id}")
    List<FuncionarioResponseDTO> findByDepartamento(@PathVariable("id") final String id);
}
