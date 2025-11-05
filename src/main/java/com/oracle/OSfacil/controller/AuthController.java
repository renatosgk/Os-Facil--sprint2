package com.oracle.OSfacil.controller;


import com.oracle.OSfacil.dto.ClienteDTO;
import com.oracle.OSfacil.dto.RegisterDTO;
import com.oracle.OSfacil.dto.TokenResponseDTO;
import com.oracle.OSfacil.model.Cliente;
import com.oracle.OSfacil.model.autenticacao.DadosLogin;
import com.oracle.OSfacil.repository.ClienteRepository;
import com.oracle.OSfacil.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ClienteRepository clienteRepository;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, ClienteRepository clienteRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> efetuarLogin(@Valid @RequestBody DadosLogin dados){
        var autenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.password());
        var authentication = authenticationManager.authenticate(autenticationToken);

        Cliente cliente = (Cliente) authentication.getPrincipal();
        String tokenAcesso = tokenService.gerarToken((Cliente) authentication.getPrincipal());

        TokenResponseDTO response = new TokenResponseDTO();
        response.setTokenAcesso(tokenAcesso);
        response.setNome(cliente.getNome());
        response.setEmail(cliente.getEmail());
        return ResponseEntity.ok( response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        if (clienteRepository.findByEmailIgnoreCase(registerDTO.email()).isPresent()){
            return ResponseEntity.badRequest().body("Já possui um cliente cadastrado com esse e-mail");
        }
        String senhaCriptografada = new BCryptPasswordEncoder().encode(registerDTO.password());
        Cliente cliente = new Cliente();
        cliente.setNome(registerDTO.nome());
        cliente.setEmail(registerDTO.email());
        cliente.setSenha(senhaCriptografada);
        cliente.setTelefone(registerDTO.telefone());
        cliente.setEndereco(registerDTO.endereco());
        cliente.setCpf(registerDTO.cpf());

        clienteRepository.save(cliente);

        return ResponseEntity.ok("Cliente cadastrado com sucesso!");
    }

    public UserDetails saveCliente(String email, String senhaCriptografada) {
        Cliente cliente = new Cliente();
        cliente.setEmail(email);
        cliente.setSenha(senhaCriptografada);
        return clienteRepository.save(cliente);
    }

}
