// src/com/rpg/exceptions/NivelInsuficienteException.java
package com.rpg.exceptions; // Pacote correto para exceções customizadas

/**
 * Exceção lançada quando um personagem tenta equipar uma arma
 * para a qual não possui nível suficiente.
 */
public class NivelInsuficienteException extends Exception {
    public NivelInsuficienteException(String message) {
        super(message);
    }
}