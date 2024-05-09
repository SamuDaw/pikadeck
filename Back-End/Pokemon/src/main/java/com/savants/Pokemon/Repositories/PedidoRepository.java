package com.savants.Pokemon.Repositories;

import com.savants.Pokemon.Models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    List<Pedido> findByUserUsername(String username);

}
