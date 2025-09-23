//Fase.java
package com.rpg.cenario;
import java.util.ArrayList;
import java.util.List;
import com.rpg.personagens.Heroi;


public interface Fase {
    void iniciar(Heroi heroi);
    boolean isConcluida();
    String getTipoDeCenario();
}
