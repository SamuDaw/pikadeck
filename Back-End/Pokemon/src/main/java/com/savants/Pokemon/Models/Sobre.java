package com.savants.Pokemon.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sobre")
public class Sobre {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_sobre", nullable = false)
    private Long idSobre;

    @Basic
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false, length = 512)
    @Size(min = 3, max = 512)
    private String nombre;

    @Basic
    @NotBlank(message = "El numero de la carta es obligatorio")
    @Column(name = "numero_carta", nullable = false, length = 512)
    @Size(max = 512)
    private String numeroCarta;

    @Basic
    @Min(value = 0, message = "El precio no puede ser negativo")
    @Column(name = "precio", nullable = false)
    @NotNull(message = "El precio no puede ser nulo")
    @NotBlank(message = "El precio no puede estar vacio")
    private double precio;

    @Basic
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock", nullable = false)
    @NotNull(message = "El stock no puede ser null")
    @NotBlank(message = "El stock no puede estar vacio")
    private int stock;

    @Basic
    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Column(name = "imagen", nullable = false, length = 512)
    private String imagen;

    @ManyToMany(mappedBy = "sobres")
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "sobre", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Carta> cartas = new ArrayList<>();

    @ManyToMany(mappedBy = "sobres")
    private List<User> user;
    // Constructor vacío
    public Sobre() {
    }

    // Constructor usando setters
    public Sobre(String nombre, String nCarta, double precio, int stock, String imagen) {

        setNombre(nombre);
        setnCarta(nCarta);
        setPrecio(precio);
        setStock(stock);
        setImagen(imagen);
    }

    // Getters y Setters
    public Long getSobreId() {
        return idSobre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
    }

    public String getnCarta() {
        return numeroCarta;
    }

    public void setnCarta(String nCarta) {
        if (nCarta != null && !nCarta.trim().isEmpty()) {
            this.numeroCarta = nCarta;
        } else {
            throw new IllegalArgumentException("El nombre de la carta no puede estar vacío");
        }
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        } else {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        } else {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        if (imagen != null && !imagen.trim().isEmpty()) {
            this.imagen = imagen;
        } else {
            throw new IllegalArgumentException("La URL de la imagen no puede estar vacía");
        }
    }

    // Método equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sobre that = (Sobre) o;
        return idSobre == that.idSobre && Double.compare(that.precio, precio) == 0 && stock == that.stock && Objects.equals(nombre, that.nombre) && Objects.equals(numeroCarta, that.numeroCarta) && Objects.equals(imagen, that.imagen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSobre, nombre, numeroCarta, precio, stock, imagen);
    }
}
