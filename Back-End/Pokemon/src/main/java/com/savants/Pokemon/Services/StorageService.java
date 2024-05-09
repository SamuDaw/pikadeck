package com.savants.Pokemon.Services;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public abstract class StorageService {

    private final Path rutaImgDir = Paths.get("Pokemon/Pokemon/src/main/resources/static/img");

    public abstract Stream<Path> loadAll();

    public abstract Path load(String filename);

    public abstract Resource loadAsResource(String filename);

    public abstract void deleteAll();

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rutaImgDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Fallo al intentar guardar un fichero vacío " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new RuntimeException(
                        "No se puede almacenar una imagen fuera del directorio actual "
                                + filename);
            }
            Files.copy(file.getInputStream(), this.rutaImgDir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Fallo al guardar la imagen: " + filename, e);
        }
    }
}