//AcaoDeCombate.java
package com.rpg.combate;

import com.rpg.exceptions.RecursoInsuficienteException;

/**
 * Representa uma ação que pode ser executada durante um combate, como um ataque
 * ou uma habilidade especial.
 */

public interface AcaoDeCombate {
    /**
        * Executa a ação de combate.
        * @param usuario O combatente que está executando a ação.
        * @param alvo O combatente que é o alvo da ação.
        * @throws RecursoInsuficienteException Se o combatente não tiver recursos
        * suficientes para executar a ação (como mana ou stamina).
     */
    void executar(Combatente usuario, Combatente alvo) throws RecursoInsuficienteException;
}
