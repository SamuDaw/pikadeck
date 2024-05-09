package com.savants.Pokemon.Controller;

import com.savants.Pokemon.Models.Carrito;
import com.savants.Pokemon.Models.Role;
import com.savants.Pokemon.Models.Sobre;
import com.savants.Pokemon.Services.CarritoService;
import com.savants.Pokemon.Services.SobreService;
import com.savants.Pokemon.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    private SobreService sobreService;
    private CarritoService carritoService;

    private UserService userService;

    @Autowired
    public CarritoController(CarritoService carritoService, SobreService sobreService, UserService userService) {
        this.sobreService = sobreService;
        this.carritoService = carritoService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<?> verCarrito() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String nombreUsuario = auth.getName();
            List<Role> roles = userService.getRolesByUsername(nombreUsuario);

            boolean hasRoleBasic = roles.stream().anyMatch(role -> role.getRole().equals("ROLE_BASIC") || role.getRole().equals("ROLE_PREMIUM"));
            if (!hasRoleBasic) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permisos para acceder a este recurso");
            }

            Carrito carrito = carritoService.getCarrito(nombreUsuario);

            if (carrito != null) {
                return ResponseEntity.ok(carrito.getSobres()); // Retorna los sobres del carrito
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay objetos en el carrito"); // No hay objetos en el carrito
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la pila de llamadas de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor."); // Error interno del servidor
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> comprarSobre(@PathVariable Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String nombreUsuario = auth.getName();
            List<Role> roles = userService.getRolesByUsername(nombreUsuario);

            boolean hasRoleBasic = roles.stream().anyMatch(role -> role.getRole().equals("ROLE_BASIC") || role.getRole().equals("ROLE_PREMIUM"));
            if (!hasRoleBasic) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permisos para acceder a este recurso");
            }

            Carrito carritoUsuario = carritoService.getCarrito(nombreUsuario);

            Sobre sobre = sobreService.obtenerSobrePorId(id);
            if (sobre == null) {
                return ResponseEntity.notFound().build(); // No se encontró el sobre con el ID proporcionado
            }

            carritoUsuario.getSobres().add(sobre);
            carritoService.guardarCarrito(carritoUsuario);
            return ResponseEntity.ok("Sobre añadido al carrito con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor."); // Error interno del servidor
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSobre(@PathVariable Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String nombreUsuario = auth.getName();
            List<Role> roles = userService.getRolesByUsername(nombreUsuario);

            boolean hasRoleBasic = roles.stream().anyMatch(role -> role.getRole().equals("ROLE_BASIC") || role.getRole().equals("ROLE_PREMIUM"));
            if (!hasRoleBasic) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permisos para acceder a este recurso");
            }

            Carrito carritoUsuario = carritoService.getCarrito(nombreUsuario);

            Sobre sobre = carritoUsuario.getSobres().stream().filter(s -> s.getSobreId().equals(id)).findFirst().orElse(null);
            if (sobre == null) {
                return ResponseEntity.notFound().build(); // No se encontró el sobre en el carrito
            }

            carritoUsuario.getSobres().remove(sobre);
            carritoService.guardarCarrito(carritoUsuario);
            return ResponseEntity.ok("Sobre eliminado del carrito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor."); // Error interno del servidor
        }
    }
}
