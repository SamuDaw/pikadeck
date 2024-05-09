package com.savants.Pokemon.Services;

import com.savants.Pokemon.Models.Pokemon;
import com.savants.Pokemon.Models.TipoCarta;
import com.savants.Pokemon.Repositories.PokemonRepository;
import com.savants.Pokemon.Repositories.TipoCartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PokemonService {

    private PokemonRepository pokemonRepository;
    @Autowired
    public PokemonService(PokemonRepository pokemonRepository){this.pokemonRepository = pokemonRepository;}
    public List<Pokemon> getPokemons() {
        return pokemonRepository.findAll();
    }

    public Optional<Pokemon> getPokemonPorId(Long id) {
        return pokemonRepository.findAll().stream().filter(pokemon1 -> Objects.equals(pokemon1.getIdPokemon(), id)).findFirst();
    }

    public void guardarPokemon(Pokemon pokemon){
        pokemonRepository.save(pokemon);
    }

    public void eliminarPokemon(Pokemon pokemon){
        pokemonRepository.delete(pokemon);
    }
}
