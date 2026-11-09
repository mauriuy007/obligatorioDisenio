package uy.edu.ort.battleshipdemo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uy.edu.ort.battleshipdemo.model.Facade;

@Configuration
public class BattleShipConfiguration {

    @Bean
    public Facade facade() {
        return Facade.getInstance();
    }
}
