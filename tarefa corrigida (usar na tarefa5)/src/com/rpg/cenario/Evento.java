//Evento.java
package com.rpg.cenario;
import com.rpg.personagens.Heroi;

public interface Evento {
    boolean vericarGatilho(Heroi heroi, TipoCenario tipoCenario);
    void executar(Heroi heroi);
}