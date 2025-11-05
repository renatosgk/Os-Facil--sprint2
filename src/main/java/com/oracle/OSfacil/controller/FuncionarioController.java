package com.oracle.OSfacil.controller;


import com.oracle.OSfacil.dto.FuncionarioDTO;
import com.oracle.OSfacil.service.FuncionarioService;
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
@RequestMapping("/funcionarios")
@AllArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;



    @PostMapping
    public ResponseEntity<EntityModel<FuncionarioDTO>>criar(@Valid @RequestBody FuncionarioDTO dto){
        FuncionarioDTO funcionarioNovo = funcionarioService.criar(dto);

        EntityModel<FuncionarioDTO> resource = EntityModel.of(funcionarioNovo,
                linkTo(methodOn(FuncionarioController.class).listarPorId(funcionarioNovo.getId())).withSelfRel(),
                linkTo(methodOn(FuncionarioController.class).atualizar(funcionarioNovo.getId(), dto)).withRel("atualizar"),
                linkTo(methodOn(FuncionarioController.class).deletarPorId(funcionarioNovo.getId())).withRel("deletar")
        );

        return ResponseEntity.ok(resource);

    }


    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<FuncionarioDTO>>>listarTodos() {
        List<EntityModel<FuncionarioDTO>> funcionarios = funcionarioService.listarTodos().stream()
                .map(func -> EntityModel.of(func,
                        linkTo(methodOn(FuncionarioController.class).listarPorId(func.getId())).withSelfRel(),
                        linkTo(methodOn(FuncionarioController.class).atualizar(func.getId(), func)).withRel("atualizar"),
                        linkTo(methodOn(FuncionarioController.class).deletarPorId(func.getId())).withRel("deletar")
                ))
                .toList();

        CollectionModel<EntityModel<FuncionarioDTO>> collection = CollectionModel.of(funcionarios,
                linkTo(methodOn(FuncionarioController.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<FuncionarioDTO>> listarPorId(@PathVariable Long id){
        FuncionarioDTO func = funcionarioService.listarPorId(id);

        EntityModel<FuncionarioDTO> resource = EntityModel.of(func,
                linkTo(methodOn(FuncionarioController.class).listarPorId(id)).withSelfRel(),
                linkTo(methodOn(FuncionarioController.class).atualizar(id, func)).withRel("atualizar"),
                linkTo(methodOn(FuncionarioController.class).deletarPorId(id)).withRel("deletar")
        );

        return ResponseEntity.ok(resource);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id){
        funcionarioService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FuncionarioDTO>>atualizar(@PathVariable Long id, @Valid @RequestBody FuncionarioDTO dto){
        FuncionarioDTO atualizado = funcionarioService.atualizar(dto,id);

        EntityModel<FuncionarioDTO> resource = EntityModel.of(atualizado,
                linkTo(methodOn(FuncionarioController.class).listarPorId(id)).withSelfRel(),
                linkTo(methodOn(FuncionarioController.class).atualizar(id, dto)).withRel("atualizar"),
                linkTo(methodOn(FuncionarioController.class).deletarPorId(id)).withRel("deletar")
        );

        return ResponseEntity.ok(resource);
    }
}