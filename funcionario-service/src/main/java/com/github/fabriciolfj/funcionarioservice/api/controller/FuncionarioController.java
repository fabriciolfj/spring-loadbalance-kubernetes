package com.github.fabriciolfj.funcionarioservice.api.controller;

import com.github.fabriciolfj.funcionarioservice.api.dto.request.FuncionarioRequestDTO;
import com.github.fabriciolfj.funcionarioservice.api.dto.response.FuncionarioResponseDTO;
import com.github.fabriciolfj.funcionarioservice.domain.service.FuncionarioService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioResponseDTO create(@NonNull @RequestBody final FuncionarioRequestDTO dto) {
        return funcionarioService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public FuncionarioResponseDTO findById(@NonNull @PathVariable("id") final String id) {
        return funcionarioService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<FuncionarioResponseDTO> findAll() {
        return funcionarioService.findAll();
    }

    @GetMapping("departamento/{departamentoId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<FuncionarioResponseDTO> findByFuncionariosPorDepartamento(@NonNull @PathVariable("departamentoId") final String departamentoId) {
        return funcionarioService.findByFuncionarioPorDepartamento(departamentoId);
    }

    @GetMapping("departamentodelay/{departamentoId}")
    public List<FuncionarioResponseDTO> findByFuncionariosPorDepartamentoDelay(@NonNull @PathVariable("departamentoId") final String departamentoId) throws InterruptedException {
        Thread.sleep(2000);
        return funcionarioService.findByFuncionarioPorDepartamento(departamentoId);
    }
}
