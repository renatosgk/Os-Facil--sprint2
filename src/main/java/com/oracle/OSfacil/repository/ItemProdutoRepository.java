package com.oracle.OSfacil.repository;

import com.oracle.OSfacil.model.ItemProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemProdutoRepository extends JpaRepository<ItemProduto, Long> {

}
