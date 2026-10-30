package uy.edu.ort.battleshipdemo.presenter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/battle")
public class BattlePresenter {
    
    @PostMapping("/connectedView")
    public Commands connectedView() {
        
    }
}
