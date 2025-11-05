package com.oracle.OSfacil.dto;


import com.oracle.OSfacil.model.OrdemServico;
import com.oracle.OSfacil.model.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemProdutoDTO {
    private Long id;
    @NotNull(message = "O Id da ordem do serviço não pode ser vazio")
    private Long ordemServicoId;
    @NotNull(message = "O Id do produto não pode ser vazio")
    private Long produtoId;
    @NotNull(message = "A quantidade não pode ser nula")
    @Positive
    private Integer quantidade;
    @NotNull(message = "O valor unitário não pode ser nulo")
    @Positive
    private BigDecimal valorUnitario;
    @NotNull(message = "O subtotal não pode ser nulo")
    @Positive
    private BigDecimal subtotal;

}
