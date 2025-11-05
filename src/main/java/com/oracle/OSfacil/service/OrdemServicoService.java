package com.oracle.OSfacil.service;


import com.oracle.OSfacil.dto.OrdemServicoDTO;
import com.oracle.OSfacil.model.Cliente;
import com.oracle.OSfacil.model.OrdemServico;
import com.oracle.OSfacil.repository.ClienteRepository;
import com.oracle.OSfacil.repository.OrdemServicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdemServicoService {
    private final OrdemServicoRepository ordemServicoRepository;
    private final ClienteRepository clienteRepository;

     private OrdemServicoDTO toDTO(OrdemServico ordemServico) {
         OrdemServicoDTO ordemServicoDTO = new OrdemServicoDTO();
         ordemServicoDTO.setId(ordemServico.getId());
         ordemServicoDTO.setDescricao(ordemServico.getDescricao());
         ordemServicoDTO.setValor(ordemServico.getValor());
         ordemServicoDTO.setStatus(ordemServico.getStatus());
         ordemServicoDTO.setStatusPagamento(ordemServico.getStatusPagamento());
         ordemServicoDTO.setClienteId(ordemServico.getCliente() !=null?ordemServico.getCliente().getId():null);
         return ordemServicoDTO;
     }
     private OrdemServico toEntity(OrdemServicoDTO ordemServicoDTO) {
         OrdemServico ordemServico = new OrdemServico();
         ordemServico.setId(ordemServicoDTO.getId());
         ordemServico.setDescricao(ordemServicoDTO.getDescricao());
         ordemServico.setValor(ordemServicoDTO.getValor());
         ordemServico.setStatus(ordemServicoDTO.getStatus());
         ordemServico.setStatusPagamento(ordemServicoDTO.getStatusPagamento());
         if(ordemServicoDTO.getClienteId()!=null){
             Cliente cliente = clienteRepository.findById(ordemServicoDTO.getClienteId())
                     .orElseThrow(()->new RuntimeException("cliente nao encontrado"));
             ordemServico.setCliente(cliente);
         }else{
             ordemServico.setCliente(null);
         }
         return ordemServico;
     }

     public OrdemServicoDTO ciar(OrdemServicoDTO ordemServicoDTO) {
        OrdemServico ordemServico = toEntity(ordemServicoDTO);
        OrdemServico salvo = ordemServicoRepository.save(ordemServico);
        return toDTO(salvo);
     }

     public void deletar(Long id) {
         OrdemServico ordemServico = ordemServicoRepository.findById(id)
                 .orElseThrow(()->new RuntimeException("ordemServico nao encontrado"));
         ordemServicoRepository.delete(ordemServico);
     }

     public OrdemServicoDTO buscar(Long id) {
         OrdemServico ordemServico = ordemServicoRepository.findById(id)
                 .orElseThrow(()->new RuntimeException("ordemServico nao encontrado"));
         return toDTO(ordemServico);
     }
     public List<OrdemServicoDTO> listarTodos() {
         return ordemServicoRepository.findAll()
                 .stream()
                 .map(this::toDTO)
                 .collect(Collectors.toList());
     }

     public OrdemServicoDTO atualizar(OrdemServicoDTO ordemServicoDTO,Long id) {
         OrdemServico ordemServico = ordemServicoRepository.findById(id)
                 .orElseThrow(()->new RuntimeException("ordemServico nao encontrado"));
         ordemServico.setValor(ordemServicoDTO.getValor());
         ordemServico.setStatusPagamento(ordemServicoDTO.getStatusPagamento());
         ordemServico.setStatus(ordemServicoDTO.getStatus());
         ordemServico.setDescricao(ordemServicoDTO.getDescricao());
         Cliente cliente = clienteRepository.findById(ordemServicoDTO.getClienteId())
                 .orElseThrow(()->new RuntimeException("cliente nao encontrado"));
         ordemServico.setCliente(cliente);

         OrdemServico atualizado = ordemServicoRepository.save(ordemServico);
         return toDTO(atualizado);

     }


}
