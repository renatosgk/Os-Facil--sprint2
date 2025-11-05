package com.oracle.OSfacil.dto;


import com.oracle.OSfacil.enums.FormaPagamento;
import com.oracle.OSfacil.model.Cliente;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagamentoDTO {
    private Long id;
    @NotNull(message = "A forma de pagamento não pode ser vazio")
    private FormaPagamento formaPagamento;
    @NotNull(message = "O valor não pode ser nulo")
    @Positive
    private BigDecimal valor;
    @NotNull(message = "O Id do cliente não pode ser vazio")
    private Long clienteId;

}
