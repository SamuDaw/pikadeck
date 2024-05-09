package com.savants.Pokemon.Services;

import com.savants.Pokemon.Models.TipoCarta;
import com.savants.Pokemon.Models.TipoEnergia;
import com.savants.Pokemon.Repositories.TipoCartaRepository;
import com.savants.Pokemon.Repositories.TipoEnergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoEnergiaService {

    private TipoEnergiaRepository tipoEnergiaRepository;

    @Autowired
    public TipoEnergiaService(TipoEnergiaRepository tipoEnergiaRepository){this.tipoEnergiaRepository = tipoEnergiaRepository;}
    public List<TipoEnergia> getTipoEnergias() {
        return tipoEnergiaRepository.findAll();
    }

    public Optional<TipoEnergia> getTipoEnergiaPorId(Long id) {
        return tipoEnergiaRepository.findAll().stream().filter(tipoEnergia -> tipoEnergia.getIdTipoEnergia() == id).findFirst();
    }

    public void guardarTipoEnergia(TipoEnergia tipoEnergia){
        tipoEnergiaRepository.save(tipoEnergia);
    }

    public void eliminarTipoEnergia(TipoEnergia tipoEnergia){
        tipoEnergiaRepository.delete(tipoEnergia);
    }
}
