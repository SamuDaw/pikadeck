package com.savants.Pokemon.Services;

import com.savants.Pokemon.Models.Carta;
import com.savants.Pokemon.Models.TipoCarta;
import com.savants.Pokemon.Repositories.CartaRepository;
import com.savants.Pokemon.Repositories.TipoCartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoCartaService {
    private TipoCartaRepository tipoCartaRepository;

    @Autowired
    public TipoCartaService(TipoCartaRepository tipoCartaRepository){this.tipoCartaRepository = tipoCartaRepository;}
    public List<TipoCarta> getTipoCartas() {
        return tipoCartaRepository.findAll();
    }

    public Optional<TipoCarta> getTipoCartaPorId(Long id){
        return tipoCartaRepository.findAll().stream().filter(tipoCarta -> Objects.equals(tipoCarta.getIdTipoCarta(), id)).findFirst();
    }

    public void guardarTipoCarta(TipoCarta tipoCarta){
        tipoCartaRepository.save(tipoCarta);
    }

    public void eliminarTipoCarta(TipoCarta tipoCarta){
        tipoCartaRepository.delete(tipoCarta);
    }
}
