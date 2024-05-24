package com.savants.Pokemon.Controller;

import com.savants.Pokemon.Models.*;
import com.savants.Pokemon.Services.*;
import com.savants.Pokemon.Storage.StorageFileNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/sobres")
public class SobreController {

    private final SobreService sobreService;

    private final StorageService storageService;

    private final UserService userService;

    private final CartaService cartaService;

    private final TipoCartaService tipoCartaService;

    @Autowired
    public SobreController(SobreService sobreService, StorageService storageService, UserService userService, CartaService cartaService, TipoCartaService tipoCartaService) {
        this.sobreService = sobreService;
        this.storageService = storageService;
        this.userService = userService;
        this.cartaService = cartaService;
        this.tipoCartaService = tipoCartaService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Sobre>> obtenerTodosSobres() {
        try {
            List<Sobre> sobres = sobreService.obtenerTodosSobres();
            if (sobres.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No hay contenido para mostrar
            } else {
                return new ResponseEntity<>(sobres, HttpStatus.OK); // Devuelve la lista de sobres
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error interno del servidor
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Sobre> obtenerSobrePorId(@PathVariable("id") Long id) {
        try {
            Sobre sobre = sobreService.obtenerSobrePorId(id);
            if (sobre != null) {

                return new ResponseEntity<>(sobre, HttpStatus.OK); // Devuelve el sobre encontrado
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // No se encontró el sobre con el ID especificado
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error interno del servidor
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Sobre>> buscarSobresPorNombre(@RequestParam(defaultValue = "") String nombre) {
        try {
            List<Sobre> sobres = sobreService.buscarSobresPorNombre(nombre);
            if (!sobres.isEmpty()) {
                return new ResponseEntity<>(sobres, HttpStatus.OK); // Se encontraron sobres
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No se encontró ningún sobre
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Sobre> crearSobre(
        @RequestParam("nombre") String nombre,
        @RequestParam("numeroCarta") String numeroCarta,
        @RequestParam("precio") Double precio,
        @RequestParam("stock") Integer stock,
        @RequestParam("imagen") MultipartFile imagen
    ) {
        String imagenUrl = storageService.store(imagen);
        Sobre sobre = new Sobre(nombre, numeroCarta, precio, stock, imagenUrl);
        try {
            if (sobre.getPrecio() <= 0 || sobre.getStock() < 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Precio o stock inválidos
            }

            Sobre createdSobre = sobreService.crearSobre(sobre);
            return new ResponseEntity<>(createdSobre, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error interno del servidor
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sobre> actualizarSobre(@PathVariable("id") Long id, @Valid @RequestBody Sobre sobre) {
        try {

            Sobre updatedSobre = sobreService.actualizarSobre(id, sobre);
            if (updatedSobre != null) {
                return new ResponseEntity<>(updatedSobre, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error interno del servidor
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSobre(@PathVariable("id") Long id) {
        try {
            // Verificar si el sobre con el ID especificado existe antes de intentar eliminarlo
            boolean exists = sobreService.existeSobre(id);
            if (!exists) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // El sobre no existe
            }

            boolean deleted = sobreService.eliminarSobre(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Sobre eliminado exitosamente
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error al eliminar el sobre
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error interno del servidor
        }
    }

    @GetMapping("/imagen/{nombreImagen}")
    public ResponseEntity<?> descargarImagen(@PathVariable String nombreImagen) {
        try {
            Path filePath = storageService.load(nombreImagen);
            byte[] fileContent = Files.readAllBytes(filePath);
            String mimeType = Files.probeContentType(filePath);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName().toString() + "\"")
                    .body(new ByteArrayResource(fileContent));
        } catch (StorageFileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al descargar la imagen: " + e.getMessage());
        }
    }

    @GetMapping("/inventarioSobres")
    public ResponseEntity<?> getInventarioSobres()
    {
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

        User user = loggedUserEncontrado.get();

        List<Sobre> sobresUser = user.getSobres();

        return ResponseEntity.ok(sobresUser);
    }

    // Endpoint para abrir un sobre especificado por el usuario y enviar cartas a su inventario
    @GetMapping("/abrir/{id}")
    public ResponseEntity<?> abrirSobre(@PathVariable("id") Long id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Role> roles = userService.getRolesByUsername(username);
        boolean hasRoleBasic = roles.stream().anyMatch(role -> role.getRole().equals("ROLE_BASIC") || role.getRole().equals("ROLE_PREMIUM"));
        if (!hasRoleBasic) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permisos para acceder a este recurso");
        }
        // Cargamos el usuario
        List<User> usuarios = userService.findAll();
        Optional<User> loggedUserEncontrado = usuarios.stream().filter(user -> Objects.equals(user.getUsername(), username)).findFirst();
        User user = loggedUserEncontrado.get();

        // Buscamos si en el inventario del usuario está el sobre
        Optional<Sobre> sobreEncontrado = user.getSobres().stream().filter(sobreUser -> Objects.equals(sobreUser.getSobreId(), id)).findFirst();
        if (sobreEncontrado.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no tiene ese sobre");

        // Si existe el sobre que nos pide hacer lo siguiente
        Sobre sobre = sobreEncontrado.get();

        // Inicializamos la lista de cartas que vamos a devolver al usuario
        List<Carta> cartas = new ArrayList<>();

        // Un sobre tiene que tener 4 cartas comunes, 3 infrecuentes y 3 holográficas y al menos 1 rara
        // Id: 1 es comun, Id: 2 es infrecuente, Id: 3 es rara y Id: 4 es holografica
        Optional<TipoCarta> tipoCartaComunEncontrado = tipoCartaService.getTipoCartaPorId(1L);
        TipoCarta tipoCartaComun = tipoCartaComunEncontrado.get();

        Optional<TipoCarta> tipoCartaInfrecuenteEncontrado = tipoCartaService.getTipoCartaPorId(2L);
        TipoCarta tipoCartaInfrecuente = tipoCartaInfrecuenteEncontrado.get();

        Optional<TipoCarta> tipoCartaHoloEncontrado = tipoCartaService.getTipoCartaPorId(3L);
        TipoCarta tipoCartaHolo = tipoCartaHoloEncontrado.get();

        Optional<TipoCarta> tipoCartaRaraEncontrado = tipoCartaService.getTipoCartaPorId(4L);
        TipoCarta tipoCartaRara = tipoCartaRaraEncontrado.get();

        List<Carta> cartasComunes = cartaService.getCartasPorTipoCartaYSobre(tipoCartaComun, sobre);

        List<Carta> cartasInfrecuentes = cartaService.getCartasPorTipoCartaYSobre(tipoCartaInfrecuente, sobre);

        List<Carta> cartasHolo = cartaService.getCartasPorTipoCartaYSobre(tipoCartaHolo, sobre);

        List<Carta> cartasRaras = cartaService.getCartasPorTipoCartaYSobre(tipoCartaRara, sobre);

        Random random = new Random();

        // Este tipo de entero es con el que se puede aumentar su valor dentro de una funcion lambda
        AtomicInteger contadorComunes = new AtomicInteger();
        do {
            cartasComunes.forEach(cartaComun -> {
                if (random.nextDouble() < 0.3 && contadorComunes.get() < 4)
                {
                    //cartaComun.getUsers().add(user);
                    cartas.add(cartaComun);
                    contadorComunes.addAndGet(1);
                }
            });
        }while(contadorComunes.get() < 4);

        AtomicInteger contadorInfrecuentes = new AtomicInteger();
        do{
            cartasInfrecuentes.forEach(cartaInfrecuente -> {
                if (random.nextDouble() < 0.3 && contadorInfrecuentes.get() < 3)
                {
                    //cartaInfrecuente.getUsers().add(user);
                    cartas.add(cartaInfrecuente);
                    contadorInfrecuentes.addAndGet(1);
                }
            });
        } while (contadorInfrecuentes.get() < 3);

        AtomicInteger contadorHolo = new AtomicInteger();
        do {
            cartasHolo.forEach(cartaHolo -> {
                if (random.nextDouble() < 0.3 && contadorHolo.get() < 2)
                {
                    //cartaHolo.getUsers().add(user);
                    cartas.add(cartaHolo);
                    contadorHolo.addAndGet(1);
                }
            });
        } while (contadorHolo.get() < 2);

        AtomicInteger contadorRara = new AtomicInteger();
        do {
            cartasRaras.forEach(cartaRara -> {
                if (random.nextDouble() < 0.3 && contadorRara.get() < 1)
                {
                    //cartaRara.getUsers().add(user);
                    cartas.add(cartaRara);
                    contadorRara.addAndGet(1);
                }
            });
        } while (contadorRara.get() < 1);
        user.getSobres().remove(sobre);
        user.getCartas().addAll(cartas);
        userService.save(user);

        return ResponseEntity.ok(cartas);
    }
}
