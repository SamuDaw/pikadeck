package com.savants.Pokemon.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.savants.Pokemon.Models.Sobre;

public interface SobreRepository extends JpaRepository<Sobre, Long> {
    List<Sobre> findByNombreContaining(String nombre);
}