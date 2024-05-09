package com.savants.Pokemon.Models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipo_carta")
public class TipoCarta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_carta")
    private Long idTipoCarta;

    @Column(name = "nombre")
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 3)
    private String nombre;


    @OneToMany(mappedBy = "tipoCarta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Carta> cartas = new ArrayList<>();

    public Long getIdTipoCarta() {
        return idTipoCarta;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setIdTipoCarta(Long idTipoCarta) {
        this.idTipoCarta = idTipoCarta;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
}
