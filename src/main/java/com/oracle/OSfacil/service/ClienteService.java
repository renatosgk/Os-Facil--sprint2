package com.oracle.OSfacil.service;


import com.oracle.OSfacil.dto.ClienteDTO;
import com.oracle.OSfacil.model.Cliente;
import com.oracle.OSfacil.model.Veiculo;
import com.oracle.OSfacil.repository.ClienteRepository;
import com.oracle.OSfacil.repository.VeiculoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteService implements UserDetailsService {

    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;

    private ClienteDTO toDTO(Cliente cliente){
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setTelefone(cliente.getTelefone());
        dto.setCpf(cliente.getCpf());
        dto.setSenha(cliente.getSenha());
        dto.setEndereco(cliente.getEndereco());
        if (cliente.getVeiculos() != null && !cliente.getVeiculos().isEmpty()) {
            Set<Long> veiculoIds = cliente.getVeiculos().stream()
                    .map(Veiculo::getId)
                    .collect(Collectors.toSet());
            dto.setVeiculoIds(veiculoIds);
        }
        return dto;
    }
    private Cliente toEntity(ClienteDTO dto){
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCpf(dto.getCpf());
        cliente.setSenha( dto.getSenha());
        cliente.setEndereco(dto.getEndereco());
        if (dto.getVeiculoIds() != null && !dto.getVeiculoIds().isEmpty()) {
            Set<Veiculo> veiculos = dto.getVeiculoIds().stream()
                    .map(id -> veiculoRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Veículo não encontrado: " + id)))
                    .collect(Collectors.toSet());
            veiculos.forEach(v -> v.setCliente(cliente));

            cliente.getVeiculos().addAll(veiculos);
        }
        return cliente;
    }

    public ClienteDTO criar (ClienteDTO dto){
        if (clienteRepository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("CPF já cadastrado para outro cliente!");
        }
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado para outro cliente!");
        }
        Cliente cliente = toEntity(dto);
        Cliente salvo = clienteRepository.save(cliente);
        return toDTO(salvo);
    }
    public List<ClienteDTO> listarTodos(){
       return clienteRepository.findAllWithVeiculos()
               .stream()
               .map(this::toDTO)
               .collect(Collectors.toList());
    }
    public ClienteDTO listarPorId(Long id){
        Cliente cliente = clienteRepository.findByIdWithVeiculos(id)
                .orElseThrow(()->new RuntimeException("Cliente nao encontrado"));
        return toDTO(cliente);
    }

    public ClienteDTO atualizar(Long id, ClienteDTO dto){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Cliente nao encontrado"));
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setSenha(dto.getSenha());
        cliente.setCpf(dto.getCpf());
        cliente.setEndereco(dto.getEndereco());
        if (dto.getVeiculoIds() != null && !dto.getVeiculoIds().isEmpty()) {
            Set<Veiculo> veiculos = dto.getVeiculoIds().stream()
                    .map(vid -> veiculoRepository.findById(vid)
                            .orElseThrow(() -> new RuntimeException("Veículo não encontrado: " + vid)))
                    .collect(Collectors.toSet());
            veiculos.forEach(v -> v.setCliente(cliente));
            cliente.getVeiculos().addAll(veiculos);
        };

        Cliente atualizado = clienteRepository.save(cliente);
        return toDTO(atualizado);
    }
    public void deletar(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Cliente nao encontrado"));
        clienteRepository.delete(cliente);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clienteRepository.findByEmailIgnoreCase(username)
                .orElseThrow(()->new UsernameNotFoundException("cliente não encontrado"));
    }
}
