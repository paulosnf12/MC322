// src/com/rpg/exceptions/RecursoInsuficienteException.java
package com.rpg.exceptions; // Pacote correto para exceções customizadas

/**
 * Exceção lançada quando um personagem não possui recursos suficientes para realizar certa ação,
 * como usar uma habilidade ou consumir um item.
 * Por exemplo, se um herói tenta usar uma habilidade que custa mais mana do que ele possui,
 * esta exceção deve ser lançada.
 */

public class RecursoInsuficienteException extends Exception {
    public RecursoInsuficienteException(String message) {
        super(message);
    }
}