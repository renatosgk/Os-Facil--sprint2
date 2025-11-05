package com.oracle.OSfacil.service;


import com.oracle.OSfacil.dto.VeiculoDTO;
import com.oracle.OSfacil.model.Cliente;
import com.oracle.OSfacil.model.Veiculo;
import com.oracle.OSfacil.repository.ClienteRepository;
import com.oracle.OSfacil.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class VeiculoService {

    private VeiculoRepository veiculoRepository;
    private ClienteRepository clienteRepository;

    private VeiculoDTO toDTO(Veiculo veiculo){
        VeiculoDTO dto = new VeiculoDTO();
        dto.setId(veiculo.getId());
        dto.setPlaca(veiculo.getPlaca());
        dto.setAno(veiculo.getAno());
        dto.setMarca(veiculo.getMarca());
        dto.setModelo(veiculo.getModelo());
        dto.setCor(veiculo.getCor());
        dto.setClienteId(veiculo.getCliente()!=null?veiculo.getCliente().getId():null);
        return dto;
    }
    private Veiculo toEntity(VeiculoDTO dto){
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setAno(dto.getAno());
        veiculo.setMarca(dto.getMarca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setCor(dto.getCor());
        if(dto.getClienteId() != null){
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(()->new RuntimeException("cliente nao encontrado"));
            veiculo.setCliente(cliente);
        }else{
            veiculo.setCliente(null);
        }
        return veiculo;
    }
    public VeiculoDTO criar (VeiculoDTO dto){
        if (veiculoRepository.existsByPlaca(dto.getPlaca())) {
            throw new RuntimeException("Já existe um veículo cadastrado com a placa " + dto.getPlaca());
        }
        Veiculo veiculo = toEntity(dto);
        Veiculo salvo = veiculoRepository.save(veiculo);
        return toDTO(salvo);

    }
    public void deletar(Long id){
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("veiculo nao encontrado"));
        Cliente cliente = veiculo.getCliente();
        if(cliente != null){
            cliente.getVeiculos().remove(veiculo);
        }
        veiculoRepository.delete(veiculo);
    }
    public VeiculoDTO buscar(Long id){
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("veiculo nao encontrado"));
        return toDTO(veiculo);
    }
    public List<VeiculoDTO> listarTodos(){
        return veiculoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }

    public VeiculoDTO atualizar(Long id,VeiculoDTO dto){
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("veiculo nao encontrado"));
        veiculo.setPlaca(dto.getPlaca());
        veiculo.setAno(dto.getAno());
        veiculo.setMarca(dto.getMarca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setCor(dto.getCor());
        Cliente clienteNovo = null;
        if(dto.getClienteId() != null){
            clienteNovo = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        }
        Cliente clienteAtual = veiculo.getCliente();
        if(clienteAtual != null && clienteAtual != clienteNovo){
            clienteAtual.getVeiculos().remove(veiculo);
        }
        veiculo.setCliente(clienteNovo);
        if(clienteNovo != null){
            clienteNovo.getVeiculos().add(veiculo);
        }
        Veiculo salvo = veiculoRepository.save(veiculo);
        return toDTO(salvo);

    }
}
