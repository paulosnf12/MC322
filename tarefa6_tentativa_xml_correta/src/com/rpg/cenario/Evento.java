// src/com/rpg/cenario/Evento.java
package com.rpg.cenario;

import com.rpg.personagens.Heroi;
import jakarta.xml.bind.annotation.XmlSeeAlso; // JAXB: Para reconhecer subclasses

/**
 * Interface que define um evento no cenário do jogo.
 * Um evento pode ser acionado com base em certos gatilhos
 * e pode executar ações específicas quando acionado.
 */
// JAXB: Indica que Evento pode ter a subclasse EventoDeBencao
@XmlSeeAlso({EventoDeBencao.class})
public interface Evento {
    boolean vericarGatilho(Heroi heroi, TipoCenario tipoCenario);
    void executar(Heroi heroi);
}