package com.savants.Pokemon.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user", schema = "pokemon", catalog = "")
@JsonIgnoreProperties({"roles"})
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id_user")
    private Long idUser;
    @Column(name = "password", nullable = false)
    @NotBlank(message = "La contraseña no puede ser vacia")
    @NotNull(message = "La contraseña no puede ser nula")
    private String password;
    @Column(name = "user_mail", nullable = false)
    @NotBlank(message = "El email no puede ser vacio")
    @NotNull(message = "El email no puede ser nulo")
    private String userMail;
    @Column(name = "username",nullable = false)
    @NotBlank(message = "El username no puede ser vacio")
    @NotNull(message = "El username no puede ser nulo")
    private String username;
    @Column(name = "nombre_entrenador", nullable = false)
    @NotBlank(message = "El nombre del entrenador no puede ser vacio")
    @NotNull(message = "El nombre del entrenador no puede ser nulo")
    private String nombreEntrenador;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "fk_user", nullable = false),
            inverseJoinColumns = @JoinColumn(name="fk_role", nullable = false)
    )
    private List<Role> roles = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "fk_carrito")
    @JsonIgnore
    private Carrito carrito;

    @ManyToMany
    @JoinTable(
            name = "User_Carta",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_carta"))
    private List<Carta> cartas = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "user_sobre",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_sobre"))
    private List<Sobre> sobres = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private List<Pedido> pedidos;
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombreEntrenador() {
        return nombreEntrenador;
    }

    public void setNombreEntrenador(String nombreEntrenador) {
        this.nombreEntrenador = nombreEntrenador;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public void setPedidos(List<Pedido> pedidos) {this.pedidos = pedidos;}

    public List<Pedido> getPedidos() {return pedidos;}

    public User(String userMail, String username, String nombreEntrenador, String password)
    {
        setUserMail(userMail);
        setUsername(username);
        setNombreEntrenador(nombreEntrenador);
        setPassword(password);
    }
    public User(){}

    public List<Carta> getCartas() {
        return cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

    public List<Sobre> getSobres() {
        return sobres;
    }

    public void setSobres(List<Sobre> sobres) {
        this.sobres = sobres;
    }
}
