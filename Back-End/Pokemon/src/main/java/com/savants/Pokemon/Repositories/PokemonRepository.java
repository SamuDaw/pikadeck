package com.savants.Pokemon.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.savants.Pokemon.Models.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

}