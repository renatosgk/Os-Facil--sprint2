package com.oracle.OSfacil.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "tb_veiculo")
@EqualsAndHashCode(of = "id")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "carro_seq_generator")
    @SequenceGenerator(name = "carro_seq_generator",sequenceName = "carro_generator",allocationSize=1)
    private Long id;
    @Column(length = 100,nullable = false)
    private String modelo;
    @Column(length = 30,nullable = false)
    private String cor;
    @Column(length = 100,nullable = false)
    private String marca;
    @Column(nullable = false)
    @Min(value = 1886, message = "Ano inválido (primeiro carro foi em 1886)")
    @Max(value = 2050, message = "Ano inválido, não exite um carro deste ano ainda")
    private Integer ano;
    @ManyToOne()
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    @Column(length = 10,nullable = false,unique = true)
    @Pattern(regexp = "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}$", message = "Placa inválida, use o padrão Mercosul")
    private String placa;

}
