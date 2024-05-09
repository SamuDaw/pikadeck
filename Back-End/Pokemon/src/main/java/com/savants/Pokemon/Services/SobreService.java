package com.savants.Pokemon.Services;

import com.savants.Pokemon.Models.Sobre;
import com.savants.Pokemon.Repositories.SobreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SobreService {

    private final SobreRepository sobreRepository;

    @Autowired
    public SobreService(SobreRepository sobreRepository) {
        this.sobreRepository = sobreRepository;
    }

    public List<Sobre> obtenerTodosSobres() {
        return sobreRepository.findAll();
    }

    public Sobre obtenerSobrePorId(Long id) {
        Optional<Sobre> sobreOptional = sobreRepository.findById(id);
        return sobreOptional.orElse(null);
    }

    public List<Sobre> buscarSobresPorNombre(String nombre) {
        return sobreRepository.findByNombreContaining(nombre);
    }

    public Sobre crearSobre(Sobre sobre) {
        return sobreRepository.save(sobre);
    }

    public Sobre actualizarSobre(Long id, Sobre sobre) {
        Optional<Sobre> existingSobreOptional = sobreRepository.findById(id);
        if (existingSobreOptional.isPresent()) {
            Sobre existingSobre = existingSobreOptional.get();

            existingSobre.setNombre(sobre.getNombre());
            existingSobre.setPrecio(sobre.getPrecio());
            existingSobre.setnCarta(sobre.getnCarta());
            existingSobre.setStock(sobre.getStock());
            existingSobre.setImagen(sobre.getImagen());

            return sobreRepository.save(existingSobre);
        } else {
            return null;
        }
    }

    public boolean eliminarSobre(Long id) {
        Optional<Sobre> existingSobreOptional = sobreRepository.findById(id);
        if (existingSobreOptional.isPresent()) {
            sobreRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean existeSobre(Long id) {
        return sobreRepository.existsById(id);
    }
}
