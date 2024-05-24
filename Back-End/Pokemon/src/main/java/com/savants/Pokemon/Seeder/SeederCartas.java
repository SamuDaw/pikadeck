package com.savants.Pokemon.Seeder;

import com.savants.Pokemon.Models.*;
import com.savants.Pokemon.Services.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SeederCartas {

    private CartaService cartaService;
    private TipoEnergiaService tipoEnergiaService;
    private TipoCartaService tipoCartaService;
    private PokemonService pokemonService;
    private SobreService sobreService;

    @Autowired
    public SeederCartas(CartaService cartaService, TipoCartaService tipoCartaService, TipoEnergiaService tipoEnergiaService, PokemonService pokemonService , SobreService sobreService)
    {
        this.cartaService = cartaService;
        this.tipoCartaService = tipoCartaService;
        this.tipoEnergiaService = tipoEnergiaService;
        this.pokemonService = pokemonService;
        this.sobreService = sobreService;

    }

    @PostConstruct
    public void seed() {
        seederTipoEnergia();
        seederTipoCarta();
        seederCartas();
    }

    public void seederTipoEnergia(){
        TipoEnergia planta = new TipoEnergia();
        planta.setNombre("Energía Planta");
        if (tipoEnergiaService.getTipoEnergias().stream().filter(tipoEnergia -> Objects.equals(tipoEnergia.getNombre(), planta.getNombre())).findFirst().isEmpty())
        {
            tipoEnergiaService.guardarTipoEnergia(planta);
        }


        TipoEnergia fuego = new TipoEnergia();
        fuego.setNombre("Energía Fuego");
        if (tipoEnergiaService.getTipoEnergias().stream().filter(tipoEnergia -> Objects.equals(tipoEnergia.getNombre(), fuego.getNombre())).findFirst().isEmpty())
        {
            tipoEnergiaService.guardarTipoEnergia(fuego);
        }


        TipoEnergia agua = new TipoEnergia();
        agua.setNombre("Energía Agua");
        if (tipoEnergiaService.getTipoEnergias().stream().filter(tipoEnergia -> Objects.equals(tipoEnergia.getNombre(), agua.getNombre())).findFirst().isEmpty())
        {
            tipoEnergiaService.guardarTipoEnergia(agua);
        }

        TipoEnergia rayo = new TipoEnergia();
        rayo.setNombre("Energía Rayo");
        if (tipoEnergiaService.getTipoEnergias().stream().filter(tipoEnergia -> Objects.equals(tipoEnergia.getNombre(), rayo.getNombre())).findFirst().isEmpty())
        {
            tipoEnergiaService.guardarTipoEnergia(rayo);
        }

        TipoEnergia psiquica = new TipoEnergia();
        psiquica.setNombre("Energía Psíquica");
        if (tipoEnergiaService.getTipoEnergias().stream().filter(tipoEnergia -> Objects.equals(tipoEnergia.getNombre(), psiquica.getNombre())).findFirst().isEmpty())
        {
            tipoEnergiaService.guardarTipoEnergia(psiquica);
        }

        TipoEnergia lucha = new TipoEnergia();
        lucha.setNombre("Energía Lucha");
        if (tipoEnergiaService.getTipoEnergias().stream().filter(tipoEnergia -> Objects.equals(tipoEnergia.getNombre(), lucha.getNombre())).findFirst().isEmpty())
        {
            tipoEnergiaService.guardarTipoEnergia(lucha);
        }

        TipoEnergia oscura = new TipoEnergia();
        oscura.setNombre("Energía Oscura");
        if (tipoEnergiaService.getTipoEnergias().stream().filter(tipoEnergia -> Objects.equals(tipoEnergia.getNombre(), oscura.getNombre())).findFirst().isEmpty())
        {
            tipoEnergiaService.guardarTipoEnergia(oscura);
        }

        TipoEnergia metalica = new TipoEnergia();
        metalica.setNombre("Energía Metálica");
        if (tipoEnergiaService.getTipoEnergias().stream().filter(tipoEnergia -> Objects.equals(tipoEnergia.getNombre(), metalica.getNombre())).findFirst().isEmpty())
        {
            tipoEnergiaService.guardarTipoEnergia(metalica);
        }
    }

    public void seederTipoCarta()
    {
        TipoCarta comun = new TipoCarta();
        comun.setNombre("Común");
        if (tipoCartaService.getTipoCartas().stream().filter(tipoCarta -> Objects.equals(tipoCarta.getNombre(), comun.getNombre())).findFirst().isEmpty()){
            tipoCartaService.guardarTipoCarta(comun);
        }


        TipoCarta infrecuente = new TipoCarta();
        infrecuente.setNombre("Infrecuente");
        if (tipoCartaService.getTipoCartas().stream().filter(tipoCarta -> Objects.equals(tipoCarta.getNombre(), infrecuente.getNombre())).findFirst().isEmpty()){
            tipoCartaService.guardarTipoCarta(infrecuente);
        }

        TipoCarta rara = new TipoCarta();
        rara.setNombre("Rara");
        if (tipoCartaService.getTipoCartas().stream().filter(tipoCarta -> Objects.equals(tipoCarta.getNombre(), rara.getNombre())).findFirst().isEmpty()){
            tipoCartaService.guardarTipoCarta(rara);
        }

        TipoCarta holografica = new TipoCarta();
        holografica.setNombre("Holográfica");
        if (tipoCartaService.getTipoCartas().stream().filter(tipoCarta -> Objects.equals(tipoCarta.getNombre(), holografica.getNombre())).findFirst().isEmpty()){
            tipoCartaService.guardarTipoCarta(holografica);
        }

    }

    public void seederCartas(){
        Pokemon pokemon = pokemonService.getPokemonPorId(1L).get();
        TipoEnergia tipoEnergia = tipoEnergiaService.getTipoEnergiaPorId(1L).get();
        TipoCarta tipoCarta = tipoCartaService.getTipoCartaPorId(1L).get();
        Sobre sobre = sobreService.obtenerSobrePorId(1L);


        Carta carta1 = new Carta();
        carta1.setNombre(pokemon.getPokemonName());
        carta1.setDescripcion("LOREM IPSUM");
        carta1.setImagen("bulbasur.png");
        carta1.setPokemon(pokemon);
        carta1.setTipoEnergia(tipoEnergia);
        carta1.setTipoCarta(tipoCarta);
        carta1.setSobre(sobre);

        if (cartaService.getCartaPorId(1L).isEmpty()){
            cartaService.guardarCarta(carta1);
        }

        Pokemon pokemon2 = pokemonService.getPokemonPorId(2L).get();
        TipoEnergia tipoEnergia2 = tipoEnergiaService.getTipoEnergiaPorId(2L).get();
        TipoCarta tipoCarta2 = tipoCartaService.getTipoCartaPorId(2L).get();
        Sobre sobre2 = sobreService.obtenerSobrePorId(2L);

        Carta carta2 = new Carta();
        carta2.setNombre(pokemon2.getPokemonName());
        carta2.setDescripcion("LOREM IPSUM2");
        carta2.setImagen("ivysaur.png");
        carta2.setPokemon(pokemon2);
        carta2.setTipoEnergia(tipoEnergia2);
        carta2.setTipoCarta(tipoCarta2);
        carta2.setSobre(sobre2);

        if (cartaService.getCartaPorId(2L).isEmpty()){
            cartaService.guardarCarta(carta2);
        }

        Pokemon pokemon3 = pokemonService.getPokemonPorId(3L).get();
        TipoEnergia tipoEnergia3 = tipoEnergiaService.getTipoEnergiaPorId(2L).get();
        TipoCarta tipoCarta3 = tipoCartaService.getTipoCartaPorId(1L).get();
        Sobre sobre1 = sobreService.obtenerSobrePorId(1L);

        Carta carta3 = new Carta();
        carta3.setNombre(pokemon3.getPokemonName());
        carta3.setDescripcion("Venasaur");
        carta3.setImagen("venasaur.png");
        carta3.setPokemon(pokemon3);
        carta3.setTipoEnergia(tipoEnergia3);
        carta3.setTipoCarta(tipoCarta3);
        carta3.setSobre(sobre1);

        if (cartaService.getCartaPorId(3L).isEmpty()){
            cartaService.guardarCarta(carta3);
        }

        Pokemon pokemon4 = pokemonService.getPokemonPorId(3L).get();
        TipoEnergia tipoEnergia4 = tipoEnergiaService.getTipoEnergiaPorId(2L).get();
        TipoCarta tipoCarta4 = tipoCartaService.getTipoCartaPorId(1L).get();


        Carta carta4 = new Carta();
        carta4.setNombre(pokemon4.getPokemonName());
        carta4.setDescripcion("charmander");
        carta4.setImagen("charmander.png");
        carta4.setPokemon(pokemon4);
        carta4.setTipoEnergia(tipoEnergia4);
        carta4.setTipoCarta(tipoCarta4);
        carta4.setSobre(sobre1);

        if (cartaService.getCartaPorId(3L).isEmpty()){
            cartaService.guardarCarta(carta4);
        }

        Pokemon pokemon5 = pokemonService.getPokemonPorId(3L).get();
        TipoEnergia tipoEnergia5 = tipoEnergiaService.getTipoEnergiaPorId(2L).get();
        TipoCarta tipoCarta5 = tipoCartaService.getTipoCartaPorId(1L).get();


        Carta carta5 = new Carta();
        carta5.setNombre(pokemon5.getPokemonName());
        carta5.setDescripcion("charmeleon");
        carta5.setImagen("charmeleon.png");
        carta5.setPokemon(pokemon4);
        carta5.setTipoEnergia(tipoEnergia4);
        carta5.setTipoCarta(tipoCarta4);
        carta5.setSobre(sobre1);

        if (cartaService.getCartaPorId(3L).isEmpty()){
            cartaService.guardarCarta(carta5);
        }
    }

}
