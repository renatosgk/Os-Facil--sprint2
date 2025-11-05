package com.oracle.OSfacil.service;


import com.oracle.OSfacil.dto.FuncionarioDTO;
import com.oracle.OSfacil.model.Funcionario;
import com.oracle.OSfacil.repository.FuncionarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioDTO criar (FuncionarioDTO dto){
        Funcionario funcionario = toEntity(dto);
        Funcionario salvo = funcionarioRepository.save(funcionario);
        return toDTO(salvo);
    }
    public FuncionarioDTO atualizar (FuncionarioDTO dto,Long id){
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Funcionario nao encontrado"));
        funcionario.setNome(dto.getNome());
        funcionario.setEmail(dto.getEmail());
        funcionario.setCpf(dto.getCpf());
        funcionario.setSalario(dto.getSalario());
        funcionario.setLogin(dto.getLogin());
        funcionario.setSenha(dto.getSenha());
        funcionario.setCargo(dto.getCargo());

        Funcionario atualizado = funcionarioRepository.save(funcionario);
        return toDTO(atualizado);
    }

    public List<FuncionarioDTO> listarTodos(){
        return funcionarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }
    public FuncionarioDTO listarPorId(Long id){
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario nao encontrado"));
        return toDTO(funcionario);
    }
    public void deletarPorId(Long id){
        Funcionario funcionario  = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario nao encontrado"));
        funcionarioRepository.delete(funcionario);
    }

    private FuncionarioDTO toDTO(Funcionario salvo) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(salvo.getId());
        dto.setNome(salvo.getNome());
        dto.setCpf(salvo.getCpf());
        dto.setEmail(salvo.getEmail());
        dto.setCargo(salvo.getCargo());
        dto.setLogin(salvo.getLogin());
        dto.setSenha(salvo.getSenha());
        dto.setSalario(salvo.getSalario());
        return dto;
    }

    private Funcionario toEntity(FuncionarioDTO dto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setEmail(dto.getEmail());
        funcionario.setCargo(dto.getCargo());
        funcionario.setLogin(dto.getLogin());
        funcionario.setSenha(dto.getSenha());
        funcionario.setSalario(dto.getSalario());
        return funcionario;
    }

}
