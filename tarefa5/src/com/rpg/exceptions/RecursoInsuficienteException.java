// RecursoInsuficienteException.java
package com.rpg.exceptions; // Pacote correto para exceções customizadas

/**
 * Exceção lançada quando um personagem não possui recursos suficientes para realizar certa ação,
 * como usar uma habilidade ou consumir um item.
 */

public class RecursoInsuficienteException extends Exception {
    public RecursoInsuficienteException(String message) {
        super(message);
    }
}