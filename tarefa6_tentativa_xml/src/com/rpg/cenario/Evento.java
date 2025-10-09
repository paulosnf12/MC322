//src/com/rpg/cenario/Evento.java
package com.rpg.cenario;

import com.rpg.personagens.Heroi;
import jakarta.xml.bind.annotation.XmlSeeAlso; // ADICIONADO PARA JAXB

/**
 * Interface que define um evento no cenário do jogo.
 * Um evento pode ser acionado com base em certos gatilhos
 * e pode executar ações específicas quando acionado.
 */
@XmlSeeAlso({EventoDeBencao.class}) // ADICIONADO PARA JAXB: Informa ao JAXB sobre as implementações concretas da interface.
public interface Evento {
    boolean vericarGatilho(Heroi heroi, TipoCenario tipoCenario);
    void executar(Heroi heroi);
}