package com.oracle.OSfacil.model;


import com.oracle.OSfacil.enums.Cargo;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tb_funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "funcionario_seq_generator")
    @SequenceGenerator(name = "funcionario_seq_generator",sequenceName = "funcionario_generator",allocationSize=1)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, length = 50)
    private String cpf;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private BigDecimal salario;
    @Column(nullable = false,unique = true)
    private String login;
    @Column(nullable = false)
    private String senha;
    @Enumerated(EnumType.STRING)
    private Cargo cargo;

}
