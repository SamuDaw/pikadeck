package com.savants.Pokemon.Services;

import com.savants.Pokemon.Models.Carrito;
import com.savants.Pokemon.Models.Sobre;
import com.savants.Pokemon.Repositories.CarritoRepository;
import com.savants.Pokemon.Repositories.SobreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarritoService {

    private CarritoRepository carritoRepository;

    @Autowired
    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    public Carrito getCarrito(String username) {
        Optional<Carrito> carritoEncontrado = carritoRepository.findAll().stream().filter(carrito -> Objects.equals(carrito.getUsuario().getUsername(), username)).findFirst();
        Carrito carrito = carritoEncontrado.get();
        return carrito;
    }

    public void guardarCarrito(Carrito carrito){
        carritoRepository.save(carrito);
    }


/*    public void addMuebleCarrito(Producto producto, Long carrito_id) {
        Optional<Carrito> carritoEncontrado = carritoRepository.findAll().stream().filter(carrito -> Objects.equals(carrito.getCarrito_id(), carrito_id)).findFirst();
        List<Producto> carrito = carritoEncontrado.get().getProductos();
        carrito.add(producto);
        carritoRepository.save(carritoRepository.findAll().stream().filter(carrito -> Objects.equals(carrito.getCarrito_id(), carrito_id)).findFirst());
    }

    public void eliminarMuebleCarrito(Producto producto) {
        carrito.getProductos().remove(producto);
    }*/
}