package uy.edu.ort.battleshipdemo.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class PrepareBattleDto {
    private String place;
    private String tactic;
    private Integer width;
    private Integer height;
    private Map<String, Integer> quantities = new HashMap<>();
}
