package com.savants.Pokemon.DTOs;

import com.savants.Pokemon.Models.Carta;

public class CartaDTO {
    private Long idCarta;
    private String nombre;
    private String descripcion;
    private String imagen;
    private Long idPokemon;
    private Long idTipoCarta;
    private Long idTipoEnergia;
    private Long idSobre;
    // Getters y setters

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

    public Long getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(Long idPokemon) {
        this.idPokemon = idPokemon;
    }

    public Long getIdTipoCarta() {
        return idTipoCarta;
    }

    public void setIdTipoCarta(Long idTipoCarta) {
        this.idTipoCarta = idTipoCarta;
    }

    public Long getIdTipoEnergia() {
        return idTipoEnergia;
    }

    public void setIdTipoEnergia(Long idTipoEnergia) {
        this.idTipoEnergia = idTipoEnergia;
    }

    public Long getIdSobre() {
        return idSobre;
    }

    public void setIdSobre(Long idSobre) {
        this.idSobre = idSobre;
    }

}