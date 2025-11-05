package com.oracle.OSfacil.service;


import com.oracle.OSfacil.dto.PagamentoDTO;
import com.oracle.OSfacil.model.Cliente;
import com.oracle.OSfacil.model.Pagamento;
import com.oracle.OSfacil.repository.ClienteRepository;
import com.oracle.OSfacil.repository.PagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.image.TileObserver;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ClienteRepository clienteRepository;

    private PagamentoDTO toDTO(Pagamento pagamento) {
        PagamentoDTO dto = new PagamentoDTO();
        dto.setId(pagamento.getId());
        dto.setFormaPagamento(pagamento.getFormaPagamento());
        dto.setValor(pagamento.getValor());
        dto.setClienteId(pagamento.getCliente() !=null ? pagamento.getCliente().getId() :null);
        return dto;
    }
    private Pagamento toEntity(PagamentoDTO dto) {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(dto.getId());
        pagamento.setFormaPagamento(dto.getFormaPagamento());
        pagamento.setValor(dto.getValor());
        if (dto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(()->new RuntimeException("cliente nao encontrado"));
            pagamento.setCliente(cliente);
        }else{
            pagamento.setCliente(null);
        }
        return pagamento;

    }
    public PagamentoDTO criar(PagamentoDTO dto) {
        Pagamento pagamento = toEntity(dto);
        Pagamento salvo =  pagamentoRepository.save(pagamento);
        return toDTO(salvo);
    }
    public void deletar(Long id){
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("pagamento nao encontrado"));
        pagamentoRepository.delete(pagamento);

    }
    public PagamentoDTO atualizar (Long id, PagamentoDTO dto) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("pagamento nao encontrado"));
        pagamento.setFormaPagamento(dto.getFormaPagamento());
        pagamento.setValor(dto.getValor());
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(()->new RuntimeException("cliente nao encontrado"));
        pagamento.setCliente(cliente);

        Pagamento atualizado = pagamentoRepository.save(pagamento);
        return toDTO(atualizado);
    }

    public List<PagamentoDTO> listarTodos(){
        return pagamentoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public PagamentoDTO buscar(Long id){
        Pagamento pagamento =  pagamentoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Pagamento nao encontrado"));
        return toDTO(pagamento);

    }
}
