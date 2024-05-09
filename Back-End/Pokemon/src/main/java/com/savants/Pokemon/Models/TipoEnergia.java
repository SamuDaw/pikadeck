package com.savants.Pokemon.Models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipo_energia")
public class TipoEnergia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_energia")
    private Long idTipoEnergia;

    @Column(name = "nombre")
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 3)
    private String nombre;

    @OneToMany(mappedBy = "tipoEnergia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Carta> cartas = new ArrayList<>();

    public Long getIdTipoEnergia() {
        return idTipoEnergia;
    }

    public void setIdTipoEnergia(Long idTipoEnergia) {
        this.idTipoEnergia = idTipoEnergia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
}
