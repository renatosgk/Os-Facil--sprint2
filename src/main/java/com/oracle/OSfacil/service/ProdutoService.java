package com.oracle.OSfacil.service;


import com.oracle.OSfacil.dto.ProdutoDTO;
import com.oracle.OSfacil.model.Produto;
import com.oracle.OSfacil.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProdutoService {
    private ProdutoRepository produtoRepository;

    private ProdutoDTO toDTO(Produto produto){
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        dto.setQuantidade(produto.getQuantidade());
        return dto;
    }
    private Produto toEntity(ProdutoDTO dto){
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        return produto;
    }

    public ProdutoDTO criar(ProdutoDTO dto){
        Produto produto = toEntity(dto);
        produtoRepository.save(produto);
        return toDTO(produto);
    }
    public void deletar(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("produto nao encontrado"));
        produtoRepository.delete(produto);
    }
    public ProdutoDTO atualizar(Long id, ProdutoDTO dto){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("produto nao encontrado"));

        produto.setNome(produto.getNome());
        produto.setPreco(produto.getPreco());
        produto.setQuantidade(produto.getQuantidade());
        Produto salvo = produtoRepository.save(produto);
        return toDTO(salvo);

    }
    public List<ProdutoDTO> listarTodos(){
        return produtoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public ProdutoDTO buscar(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("produto nao encontrado"));
        return toDTO(produto);
    }

}
