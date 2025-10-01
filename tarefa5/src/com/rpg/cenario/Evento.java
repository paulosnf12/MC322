//Evento.java
package com.rpg.cenario;
import com.rpg.personagens.Heroi;

public interface Evento {
    boolean vericarGatilho(Heroi heroi, String ambiente);
    void executar(Heroi heroi);
}