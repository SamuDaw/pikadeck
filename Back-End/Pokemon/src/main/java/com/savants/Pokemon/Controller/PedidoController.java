package com.savants.Pokemon.Controller;
import com.savants.Pokemon.Models.*;
import com.savants.Pokemon.Services.CarritoService;
import com.savants.Pokemon.Services.PedidoService;
import com.savants.Pokemon.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final UserService userService;
    private final CarritoService carritoService;

    @Autowired
    public PedidoController(PedidoService pedidoService, UserService userService, CarritoService carritoService) {
        this.pedidoService = pedidoService;
        this.userService = userService;
        this.carritoService = carritoService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getPedidos() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            // Verificar si el usuario tiene el rol "ROLE_BASIC"
            String nombreUsuario = auth.getName();
            List<Role> roles = userService.getRolesByUsername(nombreUsuario);

            boolean hasRoleBasic = roles.stream().anyMatch(role -> role.getRole().equals("ROLE_BASIC") || role.getRole().equals("ROLE_PREMIUM"));
            if (!hasRoleBasic) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permisos para acceder a este recurso");
            }

            List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(nombreUsuario);

            if (!pedidos.isEmpty()) {
                return ResponseEntity.ok(pedidos); // Retorna los pedidos del usuario
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No hay pedidos para este usuario"); // No hay pedidos para el usuario
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la pila de llamadas de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor."); // Error interno del servidor
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPedidoPorId(@PathVariable Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String nombreUsuario = auth.getName();
            List<Role> roles = userService.getRolesByUsername(nombreUsuario);

            boolean hasRoleBasic = roles.stream().anyMatch(role -> role.getRole().equals("ROLE_BASIC") || role.getRole().equals("ROLE_PREMIUM"));
            if (!hasRoleBasic) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permisos para acceder a este recurso");
            }

            Pedido pedido = pedidoService.obtenerPedidoPorId(id);
            if (pedido == null || !pedido.getUser().getUsername().equals(nombreUsuario)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permisos para acceder a este recurso");
            }

            return ResponseEntity.ok(pedido);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminarPedido/{id}")
    public ResponseEntity<?> eliminarPedido(@PathVariable Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String nombreUsuario = auth.getName();
            List<Role> roles = userService.getRolesByUsername(nombreUsuario);

            boolean hasRoleBasic = roles.stream().anyMatch(role -> role.getRole().equals("ROLE_BASIC") || role.getRole().equals("ROLE_PREMIUM"));
            if (!hasRoleBasic) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permisos para acceder a este recurso");
            }

            Pedido pedido = pedidoService.obtenerPedidoPorId(id);
            if (pedido != null) {
                pedidoService.eliminarPedido(pedido);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(@Valid @ModelAttribute("pedido") Pedido pedido, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Error en los datos del pedido", HttpStatus.BAD_REQUEST);
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            List<Role> roles = userService.getRolesByUsername(username);

            boolean hasRoleBasic = roles.stream().anyMatch(role -> role.getRole().equals("ROLE_BASIC") || role.getRole().equals("ROLE_PREMIUM"));
            if (!hasRoleBasic) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permisos para acceder a este recurso");
            }

            List<User> usuarios = userService.findAll();
            Optional<User> loggedUserEncontrado = usuarios.stream().filter(user -> Objects.equals(user.getUsername(), username)).findFirst();
            if (loggedUserEncontrado.isEmpty()) {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }

            User usuario = loggedUserEncontrado.get();

            // Asignamos el usuario al pedido
            pedido.setUser(usuario);
            // Asignamos pedido al usuario
            usuario.getPedidos().add(pedido);

            // Asignar la fecha actual al pedido
            pedido.setFecha(new Date());

            Carrito carritoUsuario = carritoService.getCarrito(username);

            // Calcular el precio total de los sobres en el carrito
            double precioTotal = carritoUsuario.getSobres().stream().mapToDouble(Sobre::getPrecio).sum();
            pedido.setPrecio(precioTotal);

            //Asignar los sobres al pedido
            // Para no hacer referencia al mismo espacio de memoria y por ende hayan errores hacemos una nueva lista
            List<Sobre> sobresCarrito = new ArrayList<Sobre>(carritoUsuario.getSobres());
            pedido.setSobres(sobresCarrito);

            // Añadir sobres al user
            usuario.getSobres().addAll(sobresCarrito);


            pedidoService.guardarPedido(pedido);

            carritoUsuario.getSobres().clear();
            carritoService.guardarCarrito(carritoUsuario);
            return new ResponseEntity<>("Pedido creado y carrito vaciado", HttpStatus.CREATED);
        }
    }
}