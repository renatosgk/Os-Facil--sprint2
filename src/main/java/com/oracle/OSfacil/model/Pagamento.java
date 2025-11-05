package com.oracle.OSfacil.model;


import com.oracle.OSfacil.enums.FormaPagamento;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "tb_pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "pagamento_seq_generator")
    @SequenceGenerator(name = "pagamento_seq_generator",sequenceName = "pagamento_generator",allocationSize = 1)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 50,nullable = false,name = "formaPagamento")
    private FormaPagamento formaPagamento;
    @Column(length = 50,nullable = false)
    private BigDecimal valor;
    @ManyToOne()
    private Cliente cliente;
}
