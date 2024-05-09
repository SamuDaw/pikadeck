package com.savants.Pokemon.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="pedido", schema = "pokemon", catalog = "")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "precio")
    @NotNull(message = "El precio no puede ser nulo")
    @NotBlank(message = "El precio no puede estar vacio")
    private Double precio;
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;
    @JoinTable(
            name = "sobre_pedido",
            joinColumns = @JoinColumn(name = "fk_pedido", nullable = false),
            inverseJoinColumns = @JoinColumn(name="fk_sobre", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Sobre> sobres;

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Sobre> getSobres() {
        return sobres;
    }

    public void setSobres(List<Sobre> sobres) {
        this.sobres = sobres;
    }
}
