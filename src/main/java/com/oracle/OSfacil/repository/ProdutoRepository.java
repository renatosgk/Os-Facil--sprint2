package com.oracle.OSfacil.repository;

import com.oracle.OSfacil.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProdutoRepository extends JpaRepository<Produto,Long> {

}
