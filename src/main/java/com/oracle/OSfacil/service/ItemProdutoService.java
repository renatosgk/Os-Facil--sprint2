package com.oracle.OSfacil.service;


import com.oracle.OSfacil.dto.ItemProdutoDTO;
import com.oracle.OSfacil.model.ItemProduto;
import com.oracle.OSfacil.model.OrdemServico;
import com.oracle.OSfacil.model.Produto;
import com.oracle.OSfacil.repository.ItemProdutoRepository;
import com.oracle.OSfacil.repository.OrdemServicoRepository;
import com.oracle.OSfacil.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemProdutoService {
    private ItemProdutoRepository itemProdutoRepository;
    private OrdemServicoRepository ordemServicoRepository;
    private ProdutoRepository produtoRepository;

    private ItemProdutoDTO toDto(ItemProduto itemProduto) {
        ItemProdutoDTO itemProdutoDTO = new ItemProdutoDTO();
        itemProdutoDTO.setId(itemProduto.getId());
        itemProdutoDTO.setSubtotal(itemProduto.getSubtotal());
        itemProdutoDTO.setQuantidade(itemProduto.getQuantidade());
        itemProdutoDTO.setValorUnitario(itemProduto.getValorUnitario());
        if(itemProdutoDTO.getProdutoId()!=null){
            Produto produto = produtoRepository.findById(itemProdutoDTO.getProdutoId())
                    .orElseThrow(()->new RuntimeException("produto nao encontrado"));
            itemProdutoDTO.setProdutoId(produto.getId());
        }else {
            itemProdutoDTO.setProdutoId(null);
        }

        if(itemProdutoDTO.getOrdemServicoId()!=null){
            OrdemServico ordemServico = ordemServicoRepository.findById(itemProdutoDTO.getOrdemServicoId())
                    .orElseThrow(()->new RuntimeException("ordemServico nao encontrado"));
            itemProdutoDTO.setOrdemServicoId(ordemServico.getId());
        }else {
            itemProdutoDTO.setOrdemServicoId(null);
        }
        return itemProdutoDTO;
    }
    private ItemProduto toEntity(ItemProdutoDTO itemProdutoDTO) {
        ItemProduto itemProduto = new ItemProduto();
        itemProduto.setId(itemProdutoDTO.getId());
        itemProduto.setSubtotal(itemProdutoDTO.getSubtotal());
        itemProduto.setQuantidade(itemProdutoDTO.getQuantidade());
        itemProduto.setValorUnitario(itemProdutoDTO.getValorUnitario());
        if(itemProdutoDTO.getProdutoId()!=null){
            Produto produto = produtoRepository.findById(itemProdutoDTO.getProdutoId())
                    .orElseThrow(()->new RuntimeException("produto nao encontrado"));
            itemProduto.setProduto(produto);
        }else{
            itemProduto.setProduto(null);
        }
        if(itemProdutoDTO.getOrdemServicoId()!=null){
            OrdemServico ordemServico = ordemServicoRepository.findById(itemProdutoDTO.getOrdemServicoId())
                    .orElseThrow(()->new RuntimeException("ordemServico nao encontrado"));
            itemProduto.setOrdemServico(ordemServico);
        }else{
            itemProduto.setOrdemServico(null);
        }
        return itemProduto;
    }

    public ItemProdutoDTO criar(ItemProdutoDTO itemProdutoDTO) {
        ItemProduto itemProduto = toEntity(itemProdutoDTO);
        ItemProduto salvo = itemProdutoRepository.save(itemProduto);
        return toDto(salvo);
    }
    public void deletar(Long id) {
        ItemProduto itemProduto = itemProdutoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("item produto nao encontrado"));
        itemProdutoRepository.delete(itemProduto);
    }
    public List<ItemProdutoDTO> listarTodos(){
        return itemProdutoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    public ItemProdutoDTO buscar(Long id) {
        ItemProduto itemProduto = itemProdutoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("item produto nao encontrado"));
        return toDto(itemProduto);
    }
    public ItemProdutoDTO atualizar(Long id, ItemProdutoDTO itemProdutoDTO) {
        ItemProduto itemProduto = itemProdutoRepository.findById(id)
                        .orElseThrow(()->new RuntimeException("item produto nao encontrado"));
        itemProduto.setSubtotal(itemProdutoDTO.getSubtotal());
        itemProduto.setQuantidade(itemProdutoDTO.getQuantidade());
        itemProduto.setValorUnitario(itemProdutoDTO.getValorUnitario());
        if (itemProdutoDTO.getProdutoId() != null) {
            Produto produto = produtoRepository.findById(itemProdutoDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("produto nao encontrado"));
            itemProduto.setProduto(produto);
        } else {
            itemProduto.setProduto(null);
        }
        if (itemProdutoDTO.getOrdemServicoId() != null) {
            OrdemServico ordemServico = ordemServicoRepository.findById(itemProdutoDTO.getOrdemServicoId())
                    .orElseThrow(() -> new RuntimeException("ordemServico nao encontrado"));
            itemProduto.setOrdemServico(ordemServico);
        } else {
            itemProduto.setOrdemServico(null);
        }
        ItemProduto atualizado = itemProdutoRepository.save(itemProduto);
        return toDto(atualizado);

    }
}

