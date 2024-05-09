package com.savants.Pokemon.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public ResponseEntity<String> index() {

        // Aquí puedes personalizar el mensaje de respuesta y el código de estado según tus necesidades.
        return ResponseEntity.ok().body("La petición fue exitosa"); // 200 OK
    }
}
