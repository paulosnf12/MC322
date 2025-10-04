//AcaoDeCombate.java
package com.rpg.combate;

/**
 * Representa uma ação que pode ser executada durante um combate, como um ataque
 * ou uma habilidade especial.
 */

public interface AcaoDeCombate {
    /**
     * Executa a ação de combate.
     * @param usuario O combatente que está realizando a ação.
     * @param alvo O combatente que é o alvo da ação.
     */
    void executar(Combatente usuario, Combatente alvo);
}
