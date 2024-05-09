package com.savants.Pokemon.Seeder;

import com.savants.Pokemon.Models.Sobre;
import com.savants.Pokemon.Repositories.SobreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SobreSeeder {

    private final SobreRepository sobreRepository;

    @Autowired
    public SobreSeeder(SobreRepository sobreRepository) {
        this.sobreRepository = sobreRepository;
    }

    @PostConstruct
    public void seedSobres() {
        if (sobreRepository.count() == 0) {
            seedInitialSobres();
        }
    }

    private void seedInitialSobres() {
        Sobre sobre1 = new Sobre("Primera Generacion", "3", 10.0, 100, "1GeneracionSobre.png");
        Sobre sobre2 = new Sobre("Segunda Generacion", "4", 25.0, 150, "2GeneracionSobre.png");
        Sobre sobre3 = new Sobre("Tercera Generacion", "2", 45.0, 90, "3GeneracionSobre.png");
        Sobre sobre4 = new Sobre("Cuarta Generacion", "3", 45.0, 90, "4GeneracionSobre.png");
        Sobre sobre5 = new Sobre("Quinta Generacion", "5", 65.0, 20, "5GeneracionSobre.png");
        Sobre sobre6 = new Sobre("Sexta Generacion", "6", 5.0, 7, "6GeneracionSobre.png");
        Sobre sobre7 = new Sobre("Septima Generacion", "1", 0.10, 10, "7GeneracionSobre.png");



        sobreRepository.save(sobre1);
        sobreRepository.save(sobre2);
        sobreRepository.save(sobre3);
        sobreRepository.save(sobre4);
        sobreRepository.save(sobre5);
        sobreRepository.save(sobre6);
        sobreRepository.save(sobre7);


    }
}
