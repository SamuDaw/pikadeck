package com.savants.Pokemon.Controller;

import com.savants.Pokemon.Models.Sobre;
import com.savants.Pokemon.Services.SobreService;
import com.savants.Pokemon.Services.StorageService;
import com.savants.Pokemon.Storage.StorageFileNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/sobres")
public class SobreController {

    private final SobreService sobreService;

    private final StorageService storageService;

    @Autowired
    public SobreController(SobreService sobreService, StorageService storageService) {
        this.sobreService = sobreService;
        this.storageService = storageService;
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
    public ResponseEntity<Sobre> crearSobre(@Valid @RequestBody Sobre sobre) {
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
}
