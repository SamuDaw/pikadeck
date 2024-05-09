package com.savants.Pokemon.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "carrito")
public class Carrito {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_carrito", nullable = false)
    private Long idCarrito;


    @OneToOne(mappedBy = "carrito")
    @JsonIgnoreProperties("carrito")
    private User usuario;

    @ManyToMany
    @JoinTable(
            name = "carrito_sobre",
            joinColumns = @JoinColumn(name = "id_carrito"),
            inverseJoinColumns = @JoinColumn(name = "id_sobre")
    )
    private List<Sobre> sobres;

    // Constructor que llama a los setters
    public Carrito(User usuario, List<Sobre> sobres) {
        setUsuario(usuario);
        setSobres(sobres);
    }
    public Carrito(){}

    // Getters y Setters
    public Long getIdCarrito() {
        return idCarrito;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        if (usuario != null) {
            this.usuario = usuario;
        } else {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
    }

    public List<Sobre> getSobres() {
        return sobres;
    }

    public void setSobres(List<Sobre> sobres) {
        if (sobres != null) {
            this.sobres = sobres;
        } else {
            throw new IllegalArgumentException("La lista de sobres no puede ser nula");
        }
    }

    // Método equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrito that = (Carrito) o;
        return idCarrito.equals(that.idCarrito);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCarrito);
    }
}
