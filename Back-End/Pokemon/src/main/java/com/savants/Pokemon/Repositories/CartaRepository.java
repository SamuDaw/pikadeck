package com.savants.Pokemon.Repositories;

import com.savants.Pokemon.Models.Carta;
import com.savants.Pokemon.Models.Sobre;
import com.savants.Pokemon.Models.TipoCarta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartaRepository extends JpaRepository<Carta, Long> {
    // Metodo para encontrar todas las cartas del tipo especificado
    List<Carta> findAllByTipoCartaAndSobre(TipoCarta TipoCarta, Sobre sobre);
}