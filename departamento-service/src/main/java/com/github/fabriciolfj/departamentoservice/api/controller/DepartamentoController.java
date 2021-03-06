package com.github.fabriciolfj.departamentoservice.api.controller;

import com.github.fabriciolfj.departamentoservice.api.request.DepartamentoRequestDTO;
import com.github.fabriciolfj.departamentoservice.api.response.DepartamentoResponseDTO;
import com.github.fabriciolfj.departamentoservice.domain.service.DepartamentoService;
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
public class DepartamentoController {

    private final DepartamentoService service;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<DepartamentoResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DepartamentoResponseDTO findById(@PathVariable("id") final String id) {
        return service.findById(id);
    }

    @GetMapping("/rest/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DepartamentoResponseDTO findByIdRest(@PathVariable("id") final String id) {
        return service.findByIdRest(id);
    }

    @GetMapping("/delay/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DepartamentoResponseDTO findByIdDelay(@PathVariable("id") final String id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartamentoResponseDTO create(@RequestBody final DepartamentoRequestDTO dto) {
        return service.create(dto);
    }
}
