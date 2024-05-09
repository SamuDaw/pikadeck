package com.savants.Pokemon.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collection;

@Entity
@Table(name = "generacion", schema = "pokemon", catalog = "")
public class Generacion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "generacion_id", nullable = false)
    private int idGeneracion;
    @Basic
    @Column(name = "nombre", nullable = true, length = 50)
    @NotBlank(message = "El nombre de la generación no puede estar vacio")
    @NotNull(message = "El nombre de la generación no puede ser null")
    @Size(min=3, max = 50)
    private String nombre;
    @OneToMany(mappedBy = "generacion")
    private Collection<Pokemon> pokemons;

    public int getIdGeneracion() {
        return idGeneracion;
    }

    public void setIdGeneracion(int generacionId) {
        this.idGeneracion = generacionId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Generacion that = (Generacion) o;

        if (idGeneracion != that.idGeneracion) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idGeneracion;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    public Collection<Pokemon> getPokemonsByGeneracionId() {
        return pokemons;
    }

    public void setPokemonsByGeneracionId(Collection<Pokemon> pokemonsByGeneracionId) {
        this.pokemons = pokemonsByGeneracionId;
    }
}
