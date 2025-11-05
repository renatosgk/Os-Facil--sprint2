package com.oracle.OSfacil.dto;


import com.oracle.OSfacil.enums.Status;
import com.oracle.OSfacil.enums.StatusPagamento;
import com.oracle.OSfacil.model.Cliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrdemServicoDTO {
    private Long id;
    @NotNull(message = "O Id do cliente não pode ser vazio")
    private Long clienteId;
    @NotNull(message = "O status do serviço não pode ser vazio")
    private Status status;
    @NotBlank(message = "A descrição não pode ser vazio")
    private String descricao;
    @NotNull(message = "O status do pagamento não pode ser vazio ")
    private StatusPagamento statusPagamento;
    @NotNull(message = "O valor não pode ser nulo")
    @Positive
    private BigDecimal valor;

}
