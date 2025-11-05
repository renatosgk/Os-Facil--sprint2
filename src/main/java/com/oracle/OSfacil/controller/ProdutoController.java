package com.oracle.OSfacil.controller;


import com.oracle.OSfacil.dto.ProdutoDTO;
import com.oracle.OSfacil.model.Produto;
import com.oracle.OSfacil.service.ProdutoService;
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
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;


    @PostMapping
    public ResponseEntity<EntityModel<ProdutoDTO>> criar(@RequestBody @Valid ProdutoDTO dto) {
        ProdutoDTO produtoNovo = produtoService.criar(dto);

        EntityModel<ProdutoDTO> resource = EntityModel.of(produtoNovo,
                linkTo(methodOn(ProdutoController.class).buscarPorId(produtoNovo.getId())).withSelfRel(),
                linkTo(methodOn(ProdutoController.class).atualizar(produtoNovo.getId(), dto)).withRel("atualizar"),
                linkTo(methodOn(ProdutoController.class).deletar(produtoNovo.getId())).withRel("deletar"),
                linkTo(methodOn(ProdutoController.class).listar()).withRel("listar_todos")
        );

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProdutoDTO>>> listar() {
        List<EntityModel<ProdutoDTO>> produtos = produtoService.listarTodos().stream()
                .map(produto -> EntityModel.of(produto,
                        linkTo(methodOn(ProdutoController.class).buscarPorId(produto.getId())).withSelfRel(),
                        linkTo(methodOn(ProdutoController.class).atualizar(produto.getId(), produto)).withRel("atualizar"),
                        linkTo(methodOn(ProdutoController.class).deletar(produto.getId())).withRel("deletar")
                ))
                .toList();

        CollectionModel<EntityModel<ProdutoDTO>> collection = CollectionModel.of(produtos,
                linkTo(methodOn(ProdutoController.class).listar()).withSelfRel());

        return ResponseEntity.ok(collection);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProdutoDTO>> buscarPorId(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.buscar(id);

        EntityModel<ProdutoDTO> resource = EntityModel.of(produto,
                linkTo(methodOn(ProdutoController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(ProdutoController.class).atualizar(id, produto)).withRel("atualizar"),
                linkTo(methodOn(ProdutoController.class).deletar(id)).withRel("deletar"),
                linkTo(methodOn(ProdutoController.class).listar()).withRel("listar_todos")
        );

        return ResponseEntity.ok(resource);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProdutoDTO>> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoDTO dto) {
        ProdutoDTO atualizado = produtoService.atualizar(id, dto);

        EntityModel<ProdutoDTO> resource = EntityModel.of(atualizado,
                linkTo(methodOn(ProdutoController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(ProdutoController.class).atualizar(id, dto)).withRel("atualizar"),
                linkTo(methodOn(ProdutoController.class).deletar(id)).withRel("deletar"),
                linkTo(methodOn(ProdutoController.class).listar()).withRel("listar_todos")
        );

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
