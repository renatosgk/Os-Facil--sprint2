package com.oracle.OSfacil.controller;


import com.oracle.OSfacil.dto.OrdemServicoDTO;
import com.oracle.OSfacil.service.OrdemServicoService;
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
@RequestMapping("/ordem-servicos")
@AllArgsConstructor
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    @PostMapping
    public ResponseEntity<EntityModel<OrdemServicoDTO>> criar(@RequestBody @Valid OrdemServicoDTO dto){
        OrdemServicoDTO ordemNova = ordemServicoService.ciar(dto);

        EntityModel<OrdemServicoDTO> resource = EntityModel.of(ordemNova,
                linkTo(methodOn(OrdemServicoController.class).buscar(ordemNova.getId())).withSelfRel(),
                linkTo(methodOn(OrdemServicoController.class).atualizar(ordemNova.getId(), dto)).withRel("atualizar"),
                linkTo(methodOn(OrdemServicoController.class).deletar(ordemNova.getId())).withRel("deletar")
        );

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<OrdemServicoDTO>>> listarTodos(){
        List<EntityModel<OrdemServicoDTO>> ordens = ordemServicoService.listarTodos().stream()
                .map(ordem -> EntityModel.of(ordem,
                        linkTo(methodOn(OrdemServicoController.class).buscar(ordem.getId())).withSelfRel(),
                        linkTo(methodOn(OrdemServicoController.class).atualizar(ordem.getId(), ordem)).withRel("atualizar"),
                        linkTo(methodOn(OrdemServicoController.class).deletar(ordem.getId())).withRel("deletar")
                ))
                .toList();

        CollectionModel<EntityModel<OrdemServicoDTO>> collection = CollectionModel.of(ordens,
                linkTo(methodOn(OrdemServicoController.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrdemServicoDTO>> buscar(@PathVariable Long id){
        OrdemServicoDTO ordem = ordemServicoService.buscar(id);

        EntityModel<OrdemServicoDTO> resource = EntityModel.of(ordem,
                linkTo(methodOn(OrdemServicoController.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(OrdemServicoController.class).atualizar(id, ordem)).withRel("atualizar"),
                linkTo(methodOn(OrdemServicoController.class).deletar(id)).withRel("deletar")
        );

        return ResponseEntity.ok(resource);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<OrdemServicoDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody OrdemServicoDTO dto){
        OrdemServicoDTO atualizado = ordemServicoService.atualizar(dto, id);

        EntityModel<OrdemServicoDTO> resource = EntityModel.of(atualizado,
                linkTo(methodOn(OrdemServicoController.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(OrdemServicoController.class).atualizar(id, dto)).withRel("atualizar"),
                linkTo(methodOn(OrdemServicoController.class).deletar(id)).withRel("deletar")
        );

        return ResponseEntity.ok(resource);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar( @PathVariable Long id){
        ordemServicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}