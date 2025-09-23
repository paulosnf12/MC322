//Fase.java
package com.rpg.cenario;
import com.rpg.personagens.Heroi;


public interface Fase {
    void iniciar(Heroi heroi);
    boolean isConcluida();
    String getTipoDeCenario();
}
