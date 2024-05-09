package com.savants.Pokemon.Models;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pokemon", schema = "pokemon")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pok_id", nullable = false)
    private Long idPokemon;
    @Basic
    @Column(name = "pok_name", nullable = false, length = 79)
    @NotBlank(message = "El nombre de pokemon no puede estar vacio")
    @NotNull(message = "El nombre del pokemon no puede ser null")
    @Size(min = 3, max = 79)
    private String pokemonName;

    @ManyToOne
    @JoinColumn(name = "generacion_id", referencedColumnName = "generacion_id")
    private Generacion generacion;

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Carta> cartas = new ArrayList<>();

    public Long getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(Long idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokName) {
        this.pokemonName = pokName;
    }

    public Integer getGeneracionId() {
        return generacion.getIdGeneracion();
    }

    public void setGeneracionId(Integer idGeneracion) {
        this.generacion.setIdGeneracion(idGeneracion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pokemon that = (Pokemon) o;

        if (idPokemon != that.idPokemon) return false;
        if (pokemonName != null ? !pokemonName.equals(that.pokemonName) : that.pokemonName != null) return false;
        if (generacion != null ? !generacion.equals(that.generacion) : that.generacion != null) return false;

        return true;
    }

    public Generacion getGeneracion() {
        return generacion;
    }

    public void setGeneracion(Generacion generacion) {
        this.generacion = generacion;
    }
}
