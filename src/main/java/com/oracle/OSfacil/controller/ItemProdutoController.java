package com.oracle.OSfacil.controller;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.oracle.OSfacil.dto.ItemProdutoDTO;
import com.oracle.OSfacil.service.ItemProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/item-produtos")
@AllArgsConstructor
public class ItemProdutoController {

    private final ItemProdutoService itemProdutoService;


    @PostMapping
    public ResponseEntity<EntityModel<ItemProdutoDTO>> criar(@RequestBody @Valid ItemProdutoDTO dto){
        ItemProdutoDTO itemNovo = itemProdutoService.criar(dto);

        EntityModel<ItemProdutoDTO> resource = EntityModel.of(itemNovo,
                linkTo(methodOn(ItemProdutoController.class).buscar(itemNovo.getId())).withSelfRel(),
                linkTo(methodOn(ItemProdutoController.class).atualizar(itemNovo.getId(), dto)).withRel("atualizar"),
                linkTo(methodOn(ItemProdutoController.class).deletar(itemNovo.getId())).withRel("deletar")
        );

        return ResponseEntity.ok(resource);
    }


    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ItemProdutoDTO>>> listarTodos(){
        List<EntityModel<ItemProdutoDTO>> produtos = itemProdutoService.listarTodos().stream()
                .map(item -> EntityModel.of(item,
                        linkTo(methodOn(ItemProdutoController.class).buscar(item.getId())).withSelfRel(),
                        linkTo(methodOn(ItemProdutoController.class).atualizar(item.getId(), item)).withRel("atualizar"),
                        linkTo(methodOn(ItemProdutoController.class).deletar(item.getId())).withRel("deletar")
                ))
                .toList();

        CollectionModel<EntityModel<ItemProdutoDTO>> collection = CollectionModel.of(produtos,
                linkTo(methodOn(ItemProdutoController.class).listarTodos()).withSelfRel());

        return ResponseEntity.ok(collection);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ItemProdutoDTO>> buscar(@PathVariable Long id){
        ItemProdutoDTO item = itemProdutoService.buscar(id);

        EntityModel<ItemProdutoDTO> resource = EntityModel.of(item,
                linkTo(methodOn(ItemProdutoController.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ItemProdutoController.class).atualizar(id, item)).withRel("atualizar"),
                linkTo(methodOn(ItemProdutoController.class).deletar(id)).withRel("deletar")
        );

        return ResponseEntity.ok(resource);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        itemProdutoService.deletar(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ItemProdutoDTO>> atualizar(@PathVariable Long id, @RequestBody @Valid ItemProdutoDTO dto){
        ItemProdutoDTO atualizado = itemProdutoService.atualizar(id, dto);

        EntityModel<ItemProdutoDTO> resource = EntityModel.of(atualizado,
                linkTo(methodOn(ItemProdutoController.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(ItemProdutoController.class).atualizar(id, dto)).withRel("atualizar"),
                linkTo(methodOn(ItemProdutoController.class).deletar(id)).withRel("deletar")
        );

        return ResponseEntity.ok(resource);
    }
}
