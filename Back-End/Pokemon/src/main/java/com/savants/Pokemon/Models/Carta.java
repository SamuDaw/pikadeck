package com.savants.Pokemon.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "carta")
public class Carta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carta")
    private Long idCarta;

    @Column(name = "Nombre")
    @NotNull(message = "El nombre de la carta no puede ser null")
    @NotBlank(message = "El nombre de la carta no puede ser vacio")
    private String nombre;

    @Column(name = "descripcion")
    @NotNull(message = "La descripción no puede ser nula")
    @NotBlank(message = "La descripción no puede ser vacia")
    @Size(min = 3, max = 150)
    private String descripcion;

    @Column(name = "imagen")
    private String imagen;


    @ManyToOne
    @JoinColumn(name = "id_pokemon")
    @JsonBackReference
    private Pokemon pokemon;

    @ManyToOne
    @JoinColumn(name = "id_tipo_energia")
    @JsonBackReference
    private TipoEnergia tipoEnergia;

    @ManyToOne
    @JoinColumn(name = "id_tipo_carta")
    @JsonBackReference
    private TipoCarta tipoCarta;

    @ManyToOne
    @JoinColumn(name = "id_sobre")
    @JsonBackReference
    private Sobre sobre;

    @ManyToMany(mappedBy = "cartas")
    private List<User> users;

    public Long getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(Long idCarta) {
        this.idCarta = idCarta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public TipoEnergia getTipoEnergia() {
        return tipoEnergia;
    }

    public void setTipoEnergia(TipoEnergia tipoEnergia) {
        this.tipoEnergia = tipoEnergia;
    }

    public TipoCarta getTipoCarta() {
        return tipoCarta;
    }

    public void setTipoCarta(TipoCarta tipoCarta) {
        this.tipoCarta = tipoCarta;
    }

    public Sobre getSobre() {
        return sobre;
    }

    public void setSobre(Sobre sobre) {
        this.sobre = sobre;
    }

    public List<User> getUsers() { return users; }

    public void setUsers(List<User> users) { this.users = users; }
}
