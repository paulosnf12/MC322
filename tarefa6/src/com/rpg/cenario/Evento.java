//Evento.java
package com.rpg.cenario;
import com.rpg.personagens.Heroi;

/**
 * Interface que define um evento no cenário do jogo.
 * Um evento pode ser acionado com base em certos gatilhos
 * e pode executar ações específicas quando acionado.
 */
public interface Evento {
    boolean vericarGatilho(Heroi heroi, TipoCenario tipoCenario);
    void executar(Heroi heroi);
}