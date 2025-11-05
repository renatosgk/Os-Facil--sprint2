package com.oracle.OSfacil.controller;


import com.oracle.OSfacil.dto.ClienteDTO;
import com.oracle.OSfacil.model.Cliente;
import com.oracle.OSfacil.service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;


    @PostMapping
    public ResponseEntity<EntityModel<ClienteDTO>> criar(@Valid @RequestBody ClienteDTO dto) {
        ClienteDTO novoCliente = clienteService.criar(dto);

        EntityModel<ClienteDTO> resource = EntityModel.of(novoCliente,
                linkTo(methodOn(ClienteController.class).listarPorId(novoCliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).listarTodos()).withRel("clientes")
        );
        return ResponseEntity.ok(resource);
    }


    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ClienteDTO>>> listarTodos() {
        List<EntityModel<ClienteDTO>> clientes = clienteService.listarTodos().stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(ClienteController.class).listarPorId(dto.getId())).withSelfRel(),
                        linkTo(methodOn(ClienteController.class).atualizar(dto.getId(), dto)).withRel("atualizar"),
                        linkTo(methodOn(ClienteController.class).deletar(dto.getId())).withRel("deletar")
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ClienteDTO>> collection = CollectionModel.of(clientes,
                linkTo(methodOn(ClienteController.class).listarTodos()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteDTO>> listarPorId(@PathVariable Long id) {
        ClienteDTO dto = clienteService.listarPorId(id);

        EntityModel<ClienteDTO> resource = EntityModel.of(dto,
                linkTo(methodOn(ClienteController.class).listarPorId(id)).withSelfRel(),
                linkTo(methodOn(ClienteController.class).atualizar(id, dto)).withRel("atualizar"),
                linkTo(methodOn(ClienteController.class).deletar(id)).withRel("deletar"),
                linkTo(methodOn(ClienteController.class).listarTodos()).withRel("clientes")
        );

        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ClienteDTO>> atualizar(@PathVariable Long id,
                                                             @Valid @RequestBody ClienteDTO dto) {
        ClienteDTO atualizado = clienteService.atualizar(id, dto);
        EntityModel<ClienteDTO> resource = EntityModel.of(atualizado,
                linkTo(methodOn(ClienteController.class).listarPorId(id)).withSelfRel(),
                linkTo(methodOn(ClienteController.class).listarTodos()).withRel("clientes")
        );

        return ResponseEntity.ok(resource);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}


