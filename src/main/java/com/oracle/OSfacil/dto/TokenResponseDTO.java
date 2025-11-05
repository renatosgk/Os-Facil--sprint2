package com.oracle.OSfacil.dto;

import lombok.Data;

@Data
public class TokenResponseDTO {
    private String tokenAcesso;
    private String nome;
    private String email;
}