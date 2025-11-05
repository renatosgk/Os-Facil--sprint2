package com.oracle.OSfacil.controller;


import com.oracle.OSfacil.dto.ClienteDTO;
import com.oracle.OSfacil.dto.VeiculoDTO;
import com.oracle.OSfacil.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
@RequestMapping("/veiculos")
@AllArgsConstructor
public class VeiculoController {
    private final VeiculoService veiculoService;


    @PostMapping
    public ResponseEntity<EntityModel<VeiculoDTO>> criar(@Valid @RequestBody VeiculoDTO dto) {
        VeiculoDTO novoVeiculo = veiculoService.criar(dto);

        EntityModel<VeiculoDTO> resource = EntityModel.of(novoVeiculo,
                linkTo(methodOn(VeiculoController.class).listarPorId(novoVeiculo.getId())).withSelfRel(),
                linkTo(methodOn(VeiculoController.class).atualizar(novoVeiculo.getId(), dto)).withRel("atualizar"),
                linkTo(methodOn(VeiculoController.class).deletar(novoVeiculo.getId())).withRel("deletar"),
                linkTo(methodOn(VeiculoController.class).listarTodos()).withRel("listar_todos")
        );

        return ResponseEntity.ok(resource);
    }


    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<VeiculoDTO>>> listarTodos() {
        List<EntityModel<VeiculoDTO>> veiculos = veiculoService.listarTodos().stream()
                .map(veiculo -> EntityModel.of(veiculo,
                        linkTo(methodOn(VeiculoController.class).listarPorId(veiculo.getId())).withSelfRel(),
                        linkTo(methodOn(VeiculoController.class).atualizar(veiculo.getId(), veiculo)).withRel("atualizar"),
                        linkTo(methodOn(VeiculoController.class).deletar(veiculo.getId())).withRel("deletar")
                ))
                .toList();

        CollectionModel<EntityModel<VeiculoDTO>> collection = CollectionModel.of(veiculos,
                linkTo(methodOn(VeiculoController.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<VeiculoDTO>> listarPorId(@PathVariable Long id) {
        VeiculoDTO veiculo = veiculoService.buscar(id);

        EntityModel<VeiculoDTO> resource = EntityModel.of(veiculo,
                linkTo(methodOn(VeiculoController.class).listarPorId(id)).withSelfRel(),
                linkTo(methodOn(VeiculoController.class).atualizar(id, veiculo)).withRel("atualizar"),
                linkTo(methodOn(VeiculoController.class).deletar(id)).withRel("deletar"),
                linkTo(methodOn(VeiculoController.class).listarTodos()).withRel("listar_todos")
        );

        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<VeiculoDTO>> atualizar(@PathVariable Long id,
                                                             @Valid @RequestBody VeiculoDTO dto) {
        VeiculoDTO atualizado = veiculoService.atualizar(id, dto);

        EntityModel<VeiculoDTO> resource = EntityModel.of(atualizado,
                linkTo(methodOn(VeiculoController.class).listarPorId(id)).withSelfRel(),
                linkTo(methodOn(VeiculoController.class).atualizar(id, dto)).withRel("atualizar"),
                linkTo(methodOn(VeiculoController.class).deletar(id)).withRel("deletar"),
                linkTo(methodOn(VeiculoController.class).listarTodos()).withRel("listar_todos")
        );

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veiculoService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}