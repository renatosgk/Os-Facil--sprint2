package com.oracle.OSfacil.controller;


import com.oracle.OSfacil.dto.PagamentoDTO;
import com.oracle.OSfacil.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
@RequestMapping("/pagamentos")
@AllArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;


    @PostMapping
    public ResponseEntity<EntityModel<PagamentoDTO>>criar(@Valid @RequestBody PagamentoDTO dto){
        PagamentoDTO pagamentoNovo = pagamentoService.criar(dto);

        EntityModel<PagamentoDTO> resource = EntityModel.of(pagamentoNovo,
                linkTo(methodOn(PagamentoController.class).buscar(pagamentoNovo.getId())).withSelfRel(),
                linkTo(methodOn(PagamentoController.class).atualizar(pagamentoNovo.getId(), dto)).withRel("atualizar"),
                linkTo(methodOn(PagamentoController.class).deletar(pagamentoNovo.getId())).withRel("deletar")
        );

        return ResponseEntity.ok(resource);
    }


    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PagamentoDTO>>> listarTodos() {
        List<EntityModel<PagamentoDTO>> pagamentos = pagamentoService.listarTodos().stream()
                .map(p -> EntityModel.of(p,
                        linkTo(methodOn(PagamentoController.class).buscar(p.getId())).withSelfRel(),
                        linkTo(methodOn(PagamentoController.class).atualizar(p.getId(), p)).withRel("atualizar"),
                        linkTo(methodOn(PagamentoController.class).deletar(p.getId())).withRel("deletar")
                ))
                .toList();

        CollectionModel<EntityModel<PagamentoDTO>> collection = CollectionModel.of(pagamentos,
                linkTo(methodOn(PagamentoController.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PagamentoDTO>> buscar(@PathVariable Long id) {
        PagamentoDTO pagamento = pagamentoService.buscar(id);

        EntityModel<PagamentoDTO> resource = EntityModel.of(pagamento,
                linkTo(methodOn(PagamentoController.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(PagamentoController.class).atualizar(id, pagamento)).withRel("atualizar"),
                linkTo(methodOn(PagamentoController.class).deletar(id)).withRel("deletar")
        );

        return ResponseEntity.ok(resource);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PagamentoDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody PagamentoDTO dto) {
        PagamentoDTO atualizado = pagamentoService.atualizar(id, dto);

        EntityModel<PagamentoDTO> resource = EntityModel.of(atualizado,
                linkTo(methodOn(PagamentoController.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(PagamentoController.class).atualizar(id, dto)).withRel("atualizar"),
                linkTo(methodOn(PagamentoController.class).deletar(id)).withRel("deletar")
        );

        return ResponseEntity.ok(resource);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pagamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}