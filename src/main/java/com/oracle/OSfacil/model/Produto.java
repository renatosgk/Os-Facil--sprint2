package com.oracle.OSfacil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Data
@Table(name = "tb_produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "produto_seq_generator")
    @SequenceGenerator(name = "produto_seq_generator",sequenceName = "produto_generator",allocationSize = 1 )
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private BigDecimal preco;
    @Column(nullable = false)
    private Integer quantidade;
}
