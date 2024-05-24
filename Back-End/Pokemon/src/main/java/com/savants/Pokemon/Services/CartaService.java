package com.savants.Pokemon.Services;

import com.savants.Pokemon.DTOs.CartaDTO;
import com.savants.Pokemon.Models.*;
import com.savants.Pokemon.Repositories.CartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartaService {

    private CartaRepository cartaRepository;

    @Autowired
    public CartaService(CartaRepository cartaRepository){this.cartaRepository = cartaRepository;}
    public List<Carta> getCartas() {
        return cartaRepository.findAll();
    }

    public void guardarCarta(Carta carta){
        cartaRepository.save(carta);
    }

    public void eliminarCarta(Carta carta){
        cartaRepository.delete(carta);
    }

    public Optional<Carta> getCartaPorId(Long id) {
        Optional<Carta> cartaEncontrada = getCartas().stream().filter(carta1 -> Objects.equals(carta1.getIdCarta(), id)).findFirst();
        return cartaEncontrada;

    }

    public List<Carta> getCartasPorTipoEnergia(TipoEnergia tipoEnergia){
        List<Carta> cartasPorTipoEnergia = getCartas().stream().filter(carta -> Objects.equals(carta.getTipoEnergia().getIdTipoEnergia(), tipoEnergia.getIdTipoEnergia())).toList();
        return cartasPorTipoEnergia;
    }

    public List<Carta> getCartasPorTipoCarta(TipoCarta tipoCarta){
        List<Carta> cartasPorTipoCarta = getCartas().stream().filter(carta -> Objects.equals(carta.getTipoCarta().getIdTipoCarta(), tipoCarta.getIdTipoCarta())).toList();
        return cartasPorTipoCarta;
    }

    public List<Carta> getCartasPorPokemon(Pokemon pokemon){
        List<Carta> cartasPorPokemon = getCartas().stream().filter(carta -> Objects.equals(carta.getPokemon().getIdPokemon(), pokemon.getIdPokemon())).toList();
        return cartasPorPokemon;
    }

    public List<Carta> getCartasPorGeneracion(Generacion generacion){
        List<Carta> cartasPorGeneracion = getCartas().stream().filter(carta -> carta.getPokemon().getGeneracionId() == generacion.getIdGeneracion()).toList();
        return cartasPorGeneracion;
    }

    public List<Carta> getCartasPorSobre(Sobre sobre){
        List<Carta> cartasPorSobre = getCartas().stream().filter(carta -> carta.getSobre() == sobre).toList();
        return cartasPorSobre;
    }

    public CartaDTO convertirCartaADTO(Carta carta){
        CartaDTO nuevaCartaDTO = new CartaDTO();
        nuevaCartaDTO.setIdCarta(carta.getIdCarta());
        nuevaCartaDTO.setNombre(carta.getNombre());
        nuevaCartaDTO.setDescripcion(carta.getDescripcion());
        nuevaCartaDTO.setImagen(carta.getImagen());
        if (carta.getPokemon() != null) {
            nuevaCartaDTO.setIdPokemon(carta.getPokemon().getIdPokemon());
        }
        if (carta.getTipoCarta() != null) {
            nuevaCartaDTO.setIdTipoCarta(carta.getTipoCarta().getIdTipoCarta());
        }
        if (carta.getTipoEnergia() != null) {
            nuevaCartaDTO.setIdTipoEnergia(carta.getTipoEnergia().getIdTipoEnergia());
        }
        if (carta.getSobre() != null) {
            nuevaCartaDTO.setIdSobre(carta.getSobre().getSobreId());
        }

        return nuevaCartaDTO;
    }

    // Metodo para conectar con el repositorio de cartas y encontrar todas las cartas segun tipo de carta especificado y sobre
    public List<Carta> getCartasPorTipoCartaYSobre(TipoCarta TipoCarta, Sobre sobre)
    {
        return cartaRepository.findAllByTipoCartaAndSobre(TipoCarta, sobre);
    }
}
