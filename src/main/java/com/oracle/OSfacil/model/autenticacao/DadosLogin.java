package com.oracle.OSfacil.model.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record  DadosLogin(@NotBlank String email,@NotBlank String password){}

