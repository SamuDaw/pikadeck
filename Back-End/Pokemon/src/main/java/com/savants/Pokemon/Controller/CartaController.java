package com.savants.Pokemon.Controller;

import com.savants.Pokemon.DTOs.CartaDTO;
import com.savants.Pokemon.Models.Carta;
import com.savants.Pokemon.Models.Role;
import com.savants.Pokemon.Models.User;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import com.savants.Pokemon.Services.*;
import com.savants.Pokemon.Storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@RestController
@RequestMapping("/api/cartas")
public class CartaController
{
    private final CartaService cartaService;
    private final TipoCartaService tipoCartaService;
    private final TipoEnergiaService tipoEnergiaService;
    private final SobreService sobreService;
    private final PokemonService pokemonService;
    private final StorageService storageService;
    private final UserService userService;

    @Autowired
    public CartaController(CartaService cartaService, TipoCartaService tipoCartaService, TipoEnergiaService tipoEnergiaService, SobreService sobreService, PokemonService pokemonService, StorageService storageService, UserService userService) {
        this.cartaService = cartaService;
        this.tipoCartaService = tipoCartaService;
        this.tipoEnergiaService = tipoEnergiaService;
        this.sobreService = sobreService;
        this.pokemonService = pokemonService;
        this.storageService = storageService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CartaDTO>> obtenerCartas() {
        try {
            List<Carta> cartas = cartaService.getCartas();
            List<CartaDTO> cartasDTO = new ArrayList<>();
            for (Carta carta: cartas) {
                cartasDTO.add(cartaService.convertirCartaADTO(carta));
            }
            if (cartas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(cartasDTO, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> crearCarta(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("img") MultipartFile file,
            @RequestParam("idPokemon") Long idPokemon,
            @RequestParam("idTipoEnergia") Long idTipoEnergia,
            @RequestParam("idTipoCarta") Long idTipoCarta,
            @RequestParam("idSobre") Long idSobre) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo no puede estar vacío");
            }
            String imagenUrl = storageService.store(file);

            Carta carta = new Carta();
            carta.setNombre(nombre);
            carta.setDescripcion(descripcion);
            carta.setImagen(imagenUrl);
            carta.setTipoCarta(tipoCartaService.getTipoCartaPorId(idTipoCarta).get());
            carta.setTipoEnergia(tipoEnergiaService.getTipoEnergiaPorId(idTipoEnergia).get());
            carta.setSobre(sobreService.obtenerSobrePorId(idSobre));
            carta.setPokemon(pokemonService.getPokemonPorId(idPokemon).get());

            // Establecer otros campos de Carta según sea necesario

            cartaService.guardarCarta(carta);
            return ResponseEntity.ok(carta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la carta: " + e.getMessage());
        }
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<CartaDTO> getCartaPorId(@PathVariable Long id) {
        try {
            Carta carta = cartaService.getCartaPorId(id).get();
            CartaDTO cartaDTO = cartaService.convertirCartaADTO(carta);
            return ResponseEntity.ok(cartaDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carta> editarCarta(@PathVariable Long id, @RequestBody Carta cartaEditada) {
        try {
            Carta carta = cartaService.getCartaPorId(id).get();
            if (carta != null) {
                // Comprobar datos para creación de objeto carta cuando ejecutemos
                cartaService.guardarCarta(carta);
                return ResponseEntity.ok(carta);
            }
            return ResponseEntity.notFound().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCarta(@PathVariable Long id) {
        try {
            Carta carta = cartaService.getCartaPorId(id).get();
            if (carta != null) {
                cartaService.eliminarCarta(carta);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

/*    @GetMapping(
            value = "/cartas/imagen/{nombreImagen}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
        InputStream in = getClass()
                .getResourceAsStream("/com/baeldung/produceimage/image.jpg");
        return IOUtils.toByteArray(in);
    }*/

/*    @GetMapping("/imagen/{nombreImagen}")
    public ResponseEntity<?> descargarImagen(@PathVariable String nombreImagen) {
        try {
            Resource file = storageService.loadAsResource(nombreImagen);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (StorageFileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
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

    @GetMapping("/inventarioCartas")
    public ResponseEntity<?> getInventarioCartas()
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

        List<Carta> cartasUser = user.getCartas();

        return ResponseEntity.ok(cartasUser);
    }

    @GetMapping("/borrarCartas")
    public ResponseEntity<?> borrarCartasUser()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        List<User> usuarios = userService.findAll();
        Optional<User> loggedUserEncontrado = usuarios.stream().filter(user -> Objects.equals(user.getUsername(), username)).findFirst();

        User user = loggedUserEncontrado.get();

        user.setCartas(new ArrayList<>());

        userService.save(user);
        return null;
    }
}


